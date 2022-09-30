package com.gugu.model;

import com.gugu.config.Config;
import com.gugu.utils.ValidUtil;
import lombok.Data;

import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * The type Config model.
 *
 * @author minmin
 * @date 2022 /09/30
 */
@Data
public class ConfigModel {

    private static final String DEFAULT_SCAN_FILE_SIZE = "0";

    private String scanPath;
    private String scanFileNamePattern;
    private String scanFileSize;
    private String optimizationPercentage;

    /**
     * To config config.
     *
     * @return the config
     */
    public Config toConfig(){
        check();
        Config config = new Config();
        config.setScanPath(Paths.get(scanPath));
        config.setScanFileNamePattern(Pattern.compile(scanFileNamePattern));
        config.setFileSizeUnit(getFileSizeUnitByScanFileSize());
        config.setOptimizationPercentage(Integer.parseInt(optimizationPercentage));
        return config;
    }

    private void check(){
        if (ValidUtil.checkDirectoryPath(scanPath)) {
            throw new RuntimeException("配置文件 scanPath 不为文件夹");
        }
        if(ValidUtil.checkPattern(scanFileNamePattern)){
            throw new RuntimeException("配置文件 scanFileNamePattern 不为正则表达式");
        }
        if (checkScanFileSize()){
            throw new RuntimeException("配置文件 scanFileSize 不为指定范围[0,Long.MAX_VALUE]MB/GB");
        }
        if(ValidUtil.checkIntegerStrRange(optimizationPercentage, 1, 99)){
            throw new RuntimeException("配置文件 optimizationPercentage 不为指定范围[1,99]");
        }
    }

    private boolean checkScanFileSize() {
        if (ValidUtil.strIsEmpty(scanFileSize)) {
            return true;
        }
        if (DEFAULT_SCAN_FILE_SIZE.equals(scanFileSize)){
            return false;
        }
        if (scanFileSize.length() < 2){
            return true;
        }
        String scanFileSizeValue = scanFileSize.substring(0, scanFileSize.length() - 2);
        if(ValidUtil.checkIntegerStrRange(scanFileSizeValue, 0)){
            return true;
        }
        String scanFileSizeUnit = scanFileSize.substring(scanFileSize.length() - 2);
        for (SizeUnit sizeUnit : SizeUnit.values()) {
            if (sizeUnit.getUnit().equals(scanFileSizeUnit)){
                return false;
            }
        }
        return true;
    }

    private FileSizeUnit getFileSizeUnitByScanFileSize(){
        if (DEFAULT_SCAN_FILE_SIZE.equals(scanFileSize)){
            return null;
        }
        Long scanFileSizeValue = Long.parseLong(scanFileSize.substring(0, scanFileSize.length() - 2));
        String scanFileSizeUnit = scanFileSize.substring(scanFileSize.length() - 2);
        return new FileSizeUnit(scanFileSizeValue, SizeUnit.valueOf(scanFileSizeUnit));
    }
}
