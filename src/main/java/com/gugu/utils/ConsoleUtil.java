package com.gugu.utils;

import java.util.Scanner;

/**
 * The type Console util.
 *
 * @author minmin
 * @date 2022 /09/30
 */
public class ConsoleUtil {

    private static final String CONSOLE_SPLIT_FLAG = "--------------------";

    private ConsoleUtil() {
    }

    /**
     * Print split.
     */
    public static void printSplit() {
        System.out.println(CONSOLE_SPLIT_FLAG);
    }

    /**
     * Print msg.
     *
     * @param msg the msg
     */
    public static void printMsg(String msg) {
        System.out.println(CONSOLE_SPLIT_FLAG + msg + CONSOLE_SPLIT_FLAG);
    }

    /**
     * 显示提示获取控制台输入
     *
     * @param msg 提示
     * @return 获取控制台一行输入的结果 input
     */
    public static String getInput(String msg) {
        System.out.print(msg);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Output control bar.
     *
     * @param num the num
     * @param max the max
     */
    public static void outputControlBar(int num, int max){
        String print = String.format("\r%s%s/%s%s", CONSOLE_SPLIT_FLAG, num, max, CONSOLE_SPLIT_FLAG);
        System.out.print(print);
    }
}
