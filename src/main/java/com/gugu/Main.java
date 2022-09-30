package com.gugu;

import com.gugu.service.DefaultFileService;
import com.gugu.utils.ProgramUtil;

/**
 * The type Main.
 *
 * @author minmin
 * @date 2022 /09/26
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception{
        DefaultFileService defaultFileService = new DefaultFileService(ProgramUtil.param2Map(args));
        defaultFileService.start();
    }
}
