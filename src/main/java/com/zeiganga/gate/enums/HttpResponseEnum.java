package com.zeiganga.gate.enums;

/**
 * Http常见响应标识
 * User: ZhengWeihao
 * Date: 2018/6/29
 * Time: 10:11
 * Description:
 */
public enum HttpResponseEnum {

    SUCCESS("200", "成功"),// 默认成功
    EXCEPTION("500", "异常"),// 接口异常
    FAILED("400", "失败")// 默认失败
    ;

    HttpResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
