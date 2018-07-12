package com.zeiganga.gate.util;

import com.zeiganga.gate.logger.CustomLogger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码工具
 *
 * @Author: ZhengWeihao
 * @Date: 2018/7/12
 * @Time: 15:08
 */
public class EncodeUtil {

    private static final CustomLogger logger = CustomLogger.getLogger(EncodeUtil.class);
    private static final String CHARSET = "UTF-8";

    private EncodeUtil() {
        super();
    }

    /**
     * URL编码
     */
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, CHARSET);
        } catch (Exception e) {
            logger.error("URL编码异常：", e);
        }

        return str;
    }

    /**
     * URL解码
     */
    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, CHARSET);
        } catch (Exception e) {
            logger.error("URL解码异常：", e);
        }

        return str;
    }
}
