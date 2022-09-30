package com.gugu.utils;

import com.gugu.config.ApplicationConfig;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Application config util.
 *
 * @author minmin
 * @date 2022 /09/30
 */
public class ApplicationConfigUtil {

    private ApplicationConfigUtil(){}

    private static String getSetMethodByFieldName(String fieldName){
        String substring = fieldName.substring(0, 1);
        String mapName = substring.toUpperCase() + fieldName.substring(1);
        return "set" + mapName;
    }


    /**
     * Load application config application config.
     *
     * @param commandMap the command map
     * @return the application config
     * @throws NoSuchMethodException     the no such method exception
     * @throws InstantiationException    the instantiation exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     */
    public static ApplicationConfig loadApplicationConfig(Map<String, String> commandMap) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (Objects.isNull(commandMap) || commandMap.size() < 1){
            return new ApplicationConfig();
        }
        ApplicationConfig applicationConfig = new ApplicationConfig();
        Class<ApplicationConfig> applicationConfigClass = ApplicationConfig.class;
        Field[] declaredFields = applicationConfigClass.getDeclaredFields();
        Map<String, Field> fieldMap = Arrays.stream(declaredFields).collect(Collectors.toMap(Field::getName, item -> item));
        for (Map.Entry<String, String> entry : commandMap.entrySet()) {
            String key = entry.getKey();
            Field field = fieldMap.get(key);
            if (Objects.isNull(field)){
                continue;
            }
            Method setFieldMethod = applicationConfigClass.getMethod(getSetMethodByFieldName(field.getName()), field.getType());
            String value = entry.getValue();
            // 这里我知道全是boolean 所以我直接偷懒不做类型判断
            Boolean aBoolean = Boolean.valueOf(value);
            setFieldMethod.invoke(applicationConfig, aBoolean);
        }
        return applicationConfig;
    }
}
