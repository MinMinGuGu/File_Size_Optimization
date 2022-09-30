package com.gugu.service;

import com.gugu.config.ApplicationConfig;
import com.gugu.config.Config;
import com.gugu.model.FileSizeUnit;
import com.gugu.model.SizeUnit;
import com.gugu.utils.ApplicationConfigUtil;
import com.gugu.utils.ConfigUtil;
import com.gugu.utils.ConsoleUtil;
import com.gugu.utils.DateUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The type Abstract file service.
 *
 * @author minmin
 * @date 2022 /09/26
 */
public abstract class AbstractFileService {

    private static final Pattern PROCESSED_FILE_PATTERN = Pattern.compile(".+_Optimization_End_.+");

    protected static final String DEFAULT_PROCESS_FLAG = "_Optimization_End_";

    /**
     * The Config.
     */
    protected final Config config;

    /**
     * The Application config.
     */
    protected final ApplicationConfig applicationConfig;

    /**
     * Instantiates a new Abstract file service.
     *
     * @param commandMap the command map
     * @throws IOException               the io exception
     * @throws NoSuchMethodException     the no such method exception
     * @throws InstantiationException    the instantiation exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     */
    public AbstractFileService(Map<String, String> commandMap) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        applicationConfig = ApplicationConfigUtil.loadApplicationConfig(commandMap);
        config = ConfigUtil.loadConfig(applicationConfig);
        applicationConfig.debug(config);
    }

    /**
     * Start.
     *
     * @throws Exception the exception
     */
    public void start() throws Exception {
        List<Path> pathList = getProcessFileList();
        preProcessFileList(pathList);
        processFileList(pathList);
        postProcessFileList(pathList);
    }

    private List<Path> getProcessFileList() throws IOException {
        List<Path> fileList = new LinkedList<>();
        Path scanPath = config.getScanPath();
        FileSizeUnit fileSizeUnit = config.getFileSizeUnit();
        long fileSizeByteCount = Objects.isNull(fileSizeUnit) ? 0 : fileSizeUnit.getFileSizeByteCount();
        Files.walkFileTree(scanPath, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                File file = path.toFile();
                String fileName = file.getName();
                if (PROCESSED_FILE_PATTERN.matcher(fileName).find()){
                    if (applicationConfig.isDeleteProcessedFile()){
                        if (file.delete()) {
                            ConsoleUtil.printMsg("删除处理过的文件 " + path);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
                boolean fileNameFlag = config.getScanFileNamePattern().matcher(fileName).find();
                boolean fileSizeFlag = file.length() >= fileSizeByteCount;
                if (fileNameFlag && fileSizeFlag){
                    fileList.add(path);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return fileList;
    }

    /**
     * Post process file list.
     *
     * @param fileList the file list
     * @throws Exception the exception
     */
    protected abstract void postProcessFileList(List<Path> fileList) throws Exception;

    /**
     * Process file list.
     *
     * @param fileList the file list
     * @throws Exception the exception
     */
    protected abstract void processFileList(List<Path> fileList) throws Exception;

    /**
     * Pre process file list.
     *
     * @param pathList the path list
     */
    protected void preProcessFileList(List<Path> pathList){
        ConsoleUtil.printSplit();
        for (Path path : pathList) {
            File file = path.toFile();
            System.out.printf("%s (%s)%n",file, formatFileSize(file.length()));
        }
        System.out.printf("共计 %s 个文件待处理%n", pathList.size());
        ConsoleUtil.printSplit();
    }

    /**
     * Format file size string.
     *
     * @param fileLength the file length
     * @return the string
     */
    protected String formatFileSize(long fileLength){
        long gbCountBytes = SizeUnit.GB.countBytes(1);
        if (fileLength > gbCountBytes) {
            return SizeUnit.GB.format(fileLength);
        } else {
            return SizeUnit.MB.format(fileLength);
        }
    }

    /**
     * Get processed file name string.
     *
     * @param fileName the file name
     * @return the string
     */
    protected String getProcessedFileName(String fileName){
        return DateUtil.getDefaultFormat() + DEFAULT_PROCESS_FLAG + fileName;
    }
}
