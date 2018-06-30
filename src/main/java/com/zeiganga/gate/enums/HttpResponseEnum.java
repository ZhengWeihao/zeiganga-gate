package com.zeiganga.gate.enums;

/**
 * Created with IntelliJ IDEA.
 * User: ZhengWeihao
 * Date: 2018/6/29
 * Time: 10:11
 * Description:
 */
public enum HttpResponseEnum {

    SUCCESS("成功"),// 默认成功
    EXCEPTION("异常"),// 接口异常
    FILED("失败")// 默认失败
    ;

    HttpResponseEnum(String msg) {
        this.msg = msg;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
