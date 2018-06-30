package com.zeiganga.gate.util;

/**
 * 操作系统工具类
 * User: ZhengWeihao
 * Date: 2018/6/29
 * Time: 14:21
 */
public class SystemUtil {

    private SystemUtil() {
        super();
    }

    /**
     * 获取系统名称
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }
}
