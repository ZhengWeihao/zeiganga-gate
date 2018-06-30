package com.zeiganga.gate.util;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 发布工具
 * User: ZhengWeihao
 * Date: 2018/6/29
 * Time: 13:44
 */
public class DeployUtil {

    public static void main(String[] args) {
        String result = ShellUtil.serialExec("ipconfig");
        System.out.println(result);
    }

}
