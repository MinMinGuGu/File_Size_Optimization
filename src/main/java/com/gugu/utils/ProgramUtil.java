package com.gugu.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Program util.
 *
 * @author minmin
 * @date 2022 /09/30
 */
public class ProgramUtil {

    private static final String DEFAULT_SPLIT = "=";

    private ProgramUtil(){}

    /**
     * Param 2 map map.
     *
     * @param args the args
     * @return the map
     */
    public static Map<String, String> param2Map(String[] args){
        if (ValidUtil.arrayIsEmpty(args)) {
            return null;
        }
        Map<String,String> map = new LinkedHashMap<>();
        for (String arg : args) {
            if (arg.contains(DEFAULT_SPLIT)){
                String[] split = arg.split(DEFAULT_SPLIT);
                String key = split[0];
                if (ValidUtil.strIsEmpty(key)){
                    continue;
                }
                String value = split[1];
                if (ValidUtil.strIsEmpty(value)){
                    continue;
                }
                map.put(key, value);
            }
        }
        return map;
    }
}
