package com.gugu.service;

import com.gugu.model.ReportInfo;
import com.gugu.utils.ConsoleUtil;
import com.gugu.utils.TryUtil;
import com.gugu.utils.ValidUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The type Default file service.
 *
 * @author minmin
 * @date 2022 /09/30
 */
public class DefaultFileService extends AbstractFileService {

    private static final String DEFAULT_PASS = ".pass";

    private static final String DEFAULT_YES = "y";

    private static final Integer DEFAULT_BYTES_LENGTH = 4 * 1024;

    private static final String DEFAULT_SAVE_FILE_NAME = "report.log";

    private final List<ReportInfo> reportInfoList = new LinkedList<>();

    /**
     * Instantiates a new Default file service.
     *
     * @param commandMap the command map
     * @throws IOException               the io exception
     * @throws NoSuchMethodException     the no such method exception
     * @throws InvocationTargetException the invocation target exception
     * @throws InstantiationException    the instantiation exception
     * @throws IllegalAccessException    the illegal access exception
     */
    public DefaultFileService(Map<String, String> commandMap) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        super(commandMap);
    }

    @Override
    protected void postProcessFileList(List<Path> pathList) throws IOException {
        if (ValidUtil.listIsEmpty(reportInfoList)) {
            return;
        }
        String input = ConsoleUtil.getInput("要 打印/存储/忽略 报告列表吗(p/s/d):");
        switch (input) {
            case "p": {
                for (ReportInfo reportInfo : reportInfoList) {
                    System.out.println(reportInfo);
                }
                return;
            }
            case "s": {
                Path reportPath = Paths.get(System.getProperty("user.dir"), DEFAULT_SAVE_FILE_NAME);
                StringBuilder stringBuilder = new StringBuilder();
                for (ReportInfo reportInfo : reportInfoList) {
                    stringBuilder.append(reportInfo.toString()).append(System.lineSeparator());
                }
                try (FileOutputStream fileOutputStream = new FileOutputStream(reportPath.toFile())) {
                    fileOutputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.flush();
                }
                ConsoleUtil.printMsg(String.format("文件已经保存在 %s", reportPath));
                return;
            }
            default: {
                break;
            }
        }
    }

    @Override
    protected void processFileList(List<Path> pathList) throws IOException {
        if (ValidUtil.listIsEmpty(pathList)) {
            return;
        }
        ConsoleUtil.printMsg("开始优化");
        int count = 0;
        for (Path path : pathList) {
            ConsoleUtil.outputControlBar(count++, pathList.size());
            File sourceFile = path.toFile();
            Path newFilePath = Paths.get(sourceFile.getParent(), sourceFile.getName() + DEFAULT_PROCESS_FLAG + DEFAULT_PASS);
            File newFile = newFilePath.toFile();
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(sourceFile, "r"); FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
                skipByte(randomAccessFile, getSkipByteCount(randomAccessFile.length()));
                byte[] bytes = new byte[DEFAULT_BYTES_LENGTH];
                int pass;
                while ((pass = randomAccessFile.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, pass);
                }
                fileOutputStream.flush();
            }
            long sourceFileLength = sourceFile.length();
            long newFileLength = newFile.length();
            boolean tryDeleteFile = TryUtil.tryDeleteFile(sourceFile);
            boolean tryRenameFile;
            if (applicationConfig.isReplaceSourceFile()) {
                tryRenameFile = TryUtil.tryRenameFile(newFile, sourceFile);
            } else {
                // 可以采用写入空串而不是删除文件
                File renameFile = Paths.get(newFile.getParent(), getProcessedFileName(sourceFile.getName())).toFile();
                tryRenameFile = TryUtil.tryRenameFile(newFile, renameFile);
            }
            if (tryDeleteFile && tryRenameFile) {
                reportInfoList.add(new ReportInfo(path.toString(), formatFileSize(sourceFileLength), formatFileSize(newFileLength)));
                ConsoleUtil.outputControlBar(count, pathList.size());
            } else {
                // 意料之外的错误 如果replaceSourceFile为真则可能文件再被占用写入
                ConsoleUtil.printMsg(String.format("%n删除原文件 %s 或重命名pass文件失败%n", sourceFile));
            }
        }
        System.out.println();
        ConsoleUtil.printMsg("优化结束");
    }

    private void skipByte(RandomAccessFile randomAccessFile, long skipByteCount) throws IOException {
        if (skipByteCount > Integer.MAX_VALUE) {
            int count = (int) (skipByteCount / Integer.MAX_VALUE);
            for (int i = 0; i < count; i++) {
                randomAccessFile.skipBytes(Integer.MAX_VALUE);
            }
            int passCount = (int) (skipByteCount - (long) Integer.MAX_VALUE * count);
            randomAccessFile.skipBytes(passCount);
        } else {
            randomAccessFile.skipBytes((int) skipByteCount);
        }
    }

    private long getSkipByteCount(long value) {
        BigDecimal bigDecimal = new BigDecimal("0." + config.getOptimizationPercentage());
        return bigDecimal.multiply(new BigDecimal(value)).longValue();
    }

    @Override
    protected void preProcessFileList(List<Path> pathList) {
        super.preProcessFileList(pathList);
        if (applicationConfig.isPreProcessViewFile()) {
            String input = ConsoleUtil.getInput("是否要继续(y/n):");
            if (!DEFAULT_YES.equals(input)) {
                System.exit(0);
            }
        }
    }
}
