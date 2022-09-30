package com.gugu.utils;

import java.io.File;

/**
 * The type Try util.
 *
 * @author minmin
 * @date 2022 /09/30
 */
public class TryUtil {

    private static final Long DEFAULT_WAIT_TIME = 10 * 1000L;

    private TryUtil(){}

    /**
     * Try delete file boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean tryDeleteFile(File file){
        return tryDeleteFile(file, DEFAULT_WAIT_TIME);
    }

    /**
     * Try delete file boolean.
     *
     * @param file     the file
     * @param waitTime the wait time
     * @return the boolean
     */
    public static boolean tryDeleteFile(File file, long waitTime){
        long start = System.currentTimeMillis();
        while (!file.delete()){
            long now = System.currentTimeMillis();
            if (now - start > waitTime){
                return false;
            }
        }
        return true;
    }

    /**
     * Try rename file boolean.
     *
     * @param source  the source
     * @param newFile the new file
     * @return the boolean
     */
    public static boolean tryRenameFile(File source, File newFile){
        return tryRenameFile(source, newFile, DEFAULT_WAIT_TIME);
    }

    /**
     * Try rename file boolean.
     *
     * @param source   the source
     * @param newFile  the new file
     * @param waitTime the wait time
     * @return the boolean
     */
    public static boolean tryRenameFile(File source, File newFile, long waitTime){
        long start = System.currentTimeMillis();
        while (!source.renameTo(newFile)){
            long now = System.currentTimeMillis();
            if (now - start > waitTime){
                return false;
            }
        }
        return true;
    }
}
