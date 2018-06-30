package com.zeiganga.gate.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 命令行工具
 * User: ZhengWeihao
 * Date: 2018/6/29
 * Time: 14:19
 */
public class ShellUtil {

    private static final String CHARSET = SystemUtil.getOsName().toUpperCase().contains("WINDOWS") ? "GBK" : "UTF-8";// 根据操作系统类型定义字符集

    private ShellUtil() {
        super();
    }

    /**
     * 串行命令
     */
    public static String serialExec(String command) {
        try {
            Process exec = Runtime.getRuntime().exec(command);
            int i = exec.waitFor();
            if (i != 0) {
                System.err.println("exit unexpect, exitValue:" + exec.exitValue());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream(), Charset.forName(CHARSET)));
            StringBuffer result = new StringBuffer();

            String line = null;
            while ((line = reader.readLine()) != null) {
                if (result.length() > 0) {
                    result.append("\n");
                }

                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
