package com.gugu.config;

import com.gugu.model.FileSizeUnit;
import lombok.Data;

import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * The type Config.
 *
 * @author minmin
 * @date 2022 /09/30
 */
@Data
public class Config {
    private Path scanPath;
    private Pattern scanFileNamePattern;
    private FileSizeUnit fileSizeUnit;
    private Integer optimizationPercentage;
}
