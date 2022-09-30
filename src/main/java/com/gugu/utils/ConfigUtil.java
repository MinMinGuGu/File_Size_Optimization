package com.gugu.utils;

import com.gugu.config.ApplicationConfig;
import com.gugu.config.Config;
import com.gugu.model.ConfigModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * The type Config util.
 *
 * @author minmin
 * @date 2022 /09/30
 */
public class ConfigUtil {

    private static final String DEFAULT_CONFIG_FILE_NAME = "config.properties";

    private ConfigUtil() {
    }

    /**
     * Load config config.
     *
     * @param applicationConfig the application config
     * @return the config
     * @throws IOException the io exception
     */
    public static Config loadConfig(ApplicationConfig applicationConfig) throws IOException {
        Path configPath = getConfigPath(applicationConfig);
        ConfigModel configModel = readProperties(configPath);
        return configModel.toConfig();
    }

    private static ConfigModel readProperties(Path configPath) throws IOException {
        Properties properties = new Properties();
        ConfigModel configModel;
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(configPath.toFile()), StandardCharsets.UTF_8)) {
            properties.load(reader);
            configModel = new ConfigModel();
            configModel.setScanPath(properties.getProperty("scanPath"));
            configModel.setScanFileNamePattern(properties.getProperty("scanFileNamePattern"));
            configModel.setScanFileSize(properties.getProperty("scanFileSize"));
            configModel.setOptimizationPercentage(properties.getProperty("optimizationPercentage"));
        }
        return configModel;
    }

    private static Path getConfigPath(ApplicationConfig applicationConfig) {
        Path configPath = Paths.get(System.getProperty("user.dir"));
        if (applicationConfig.isDebug()){
            configPath = configPath.resolve("src").resolve("main").resolve("resources");
        }
        configPath = configPath.resolve(DEFAULT_CONFIG_FILE_NAME);
        if (ValidUtil.checkFilePath(configPath)) {
            throw new RuntimeException("配置文件不存在");
        }
        return configPath;
    }
}
