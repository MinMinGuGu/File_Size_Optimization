package com.gugu.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The type Valid util.
 *
 * @author minmin
 * @date 2022 /09/26
 */
public class ValidUtil {
    private ValidUtil(){}

    /**
     * Check file path boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public static boolean checkFilePath(Path path) {
        return Files.exists(path) && Files.isDirectory(path);
    }

    /**
     * Check directory path boolean.
     *
     * @param scanPath the scan path
     * @return the boolean
     */
    public static boolean checkDirectoryPath(String scanPath) {
        if (strIsEmpty(scanPath)){
            return true;
        }
        return !checkFilePath(Paths.get(scanPath));
    }

    /**
     * Check pattern boolean.
     *
     * @param scanFileNamePattern the scan file name pattern
     * @return the boolean
     */
    public static boolean checkPattern(String scanFileNamePattern) {
        if (strIsEmpty(scanFileNamePattern)){
            return true;
        }
        try {
            Pattern.compile(scanFileNamePattern);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Check integer str range boolean.
     *
     * @param scanFileSize the scan file size
     * @param min          the min
     * @return the boolean
     */
    public static boolean checkIntegerStrRange(String scanFileSize, int min) {
        return checkIntegerStrRange(scanFileSize, min, Integer.MAX_VALUE);
    }

    /**
     * Check integer str range boolean.
     *
     * @param scanFileSize the scan file size
     * @param min          the min
     * @param max          the max
     * @return the boolean
     */
    public static boolean checkIntegerStrRange(String scanFileSize, int min, int max) {
        if (strIsEmpty(scanFileSize)){
            return true;
        }
        try {
            int value = Integer.parseInt(scanFileSize);
            if (value < min || value > max){
                return true;
            }
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    /**
     * Str is empty boolean.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean strIsEmpty(String value){
        if (Objects.isNull(value)){
            return true;
        }
        value = value.trim();
        return value.length() < 1;
    }

    /**
     * Array is empty boolean.
     *
     * @param <T>    the type parameter
     * @param target the target
     * @return the boolean
     */
    public static <T> boolean arrayIsEmpty(T[] target){
        if (Objects.isNull(target)){
            return true;
        }
        if (target.length < 1){
            return true;
        }
        for (T t : target) {
            if (Objects.nonNull(t)){
                return false;
            }
        }
        return true;
    }

    /**
     * List is empty boolean.
     *
     * @param list the list
     * @return the boolean
     */
    public static boolean listIsEmpty(List<?> list){
        if (Objects.isNull(list)) {
            return true;
        }
        for (Object obj : list) {
            if (Objects.nonNull(obj)){
                return false;
            }
        }
        return true;
    }
}
