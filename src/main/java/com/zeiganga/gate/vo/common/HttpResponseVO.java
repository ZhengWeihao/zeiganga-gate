package com.zeiganga.gate.vo.common;

import java.io.Serializable;
import java.util.Map;

/**
 * 响应视图对象
 * User: ZhengWeihao
 * Date: 2018/6/29
 * Time: 10:06
 * 用于HTTP接口响应
 */
public class HttpResponseVO implements Serializable {

    public HttpResponseVO(Boolean result, String code, String msg) {
        this.result = result;
        this.code = code;
        this.msg = msg;
    }

    public HttpResponseVO(Boolean result, String code, String msg, Map<String, Object> data) {
        this.result = result;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Boolean result;// 请求结果
    private String code;// 响应码
    private String msg;// 响应消息
    private Map<String, Object> data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public HttpResponseVO fillData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
}
