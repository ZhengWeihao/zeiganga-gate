package com.zeiganga.gate.enums;

/**
 * 日期格式定义
 * User: ZhengWeihao
 * Date: 2018/6/29
 * Time: 11:45
 */
public enum DateFormatEnum {

    DATE("yyyy-MM-dd"), DATETIME("yyyy-MM-dd HH:mm:ss");

    DateFormatEnum(String format) {
        this.format = format;
    }

    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
