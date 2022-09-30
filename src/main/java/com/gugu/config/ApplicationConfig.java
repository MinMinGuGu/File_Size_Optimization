package com.gugu.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Application config.
 *
 * @author minmin
 * @date 2022 /09/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationConfig {
    /**
     * 调式  打印一些调式信息
     */
    private boolean debug;
    /**
     * 优化后的文件替换原文件 如果被占用很可能会失败
     */
    private boolean replaceSourceFile;
    /**
     * 处理文件前是否需要确认
     */
    private boolean preProcessViewFile;

    /**
     * 删除处理过的文件
     */
    private boolean deleteProcessedFile;

    /**
     * Debug.
     *
     * @param msg the msg
     */
    public void debug(Object msg){
        if (debug){
            System.out.println(msg);
        }
    }
}
