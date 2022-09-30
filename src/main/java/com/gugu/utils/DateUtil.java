package com.gugu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Date util.
 *
 * @author minmin
 * @date 2022 /09/30
 */
public class DateUtil {
    private DateUtil(){}

    /**
     * Get default format string.
     *
     * @return the string
     */
    public static String getDefaultFormat(){
        return getDefaultFormat(new Date());
    }

    /**
     * Get default format string.
     *
     * @param date the date
     * @return the string
     */
    public static String getDefaultFormat(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return simpleDateFormat.format(date);
    }
}
