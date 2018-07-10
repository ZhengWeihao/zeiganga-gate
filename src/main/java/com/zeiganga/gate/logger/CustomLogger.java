package com.zeiganga.gate.logger;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义日志
 * User: ZhengWeihao
 * Date: 2018/6/30 0030
 * Time: 15:52
 */
public class CustomLogger {

    private static final Logger bizLogger = LoggerFactory.getLogger("bizLogger");
    private static Logger logger;

    private String from;

    private static final Level LEVEL_BIZ = null;

    public CustomLogger(Class clazz) {
        super();
        this.logger = LoggerFactory.getLogger(clazz);
        from = clazz.getCanonicalName();
    }

    public CustomLogger(String name) {
        super();
        this.logger = LoggerFactory.getLogger(name);
        from = name;
    }

    public static CustomLogger getLogger(Class clazz) {
        return new CustomLogger(clazz);
    }

    public static CustomLogger getLogger(String name) {
        return new CustomLogger(name);
    }

    private String getBizContent(String content) {
        return content;
    }

    public void biz(String content) {
        bizLogger.debug(getBizContent(content));
    }

    public void biz(String content, Throwable t) {
        bizLogger.debug(getBizContent(content), t);
    }

    public void biz(String format, Object... argArray) {
        bizLogger.debug(getBizContent(format), argArray);
    }

    public void error(String content) {
        logger.error(content);
    }

    public void error(String content, Throwable e) {
        logger.error(content, e);
    }

    public void error(String format, Object... argArray) {
        logger.error(format, argArray);
    }

}
