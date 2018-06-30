package com.zeiganga.gate.controller;

import com.zeiganga.gate.editor.CustomDateEditor;
import com.zeiganga.gate.enums.HttpResponseEnum;
import com.zeiganga.gate.vo.common.HttpResponseVO;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Date;

/**
 * 全局Controller处理
 * User: ZhengWeihao
 * Date: 2018/6/29
 * Time: 11:34
 */
@ControllerAdvice
public class BaseController {

    /**
     * 异常处理
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public HttpResponseVO exceptionHandler(NativeWebRequest request, Exception e) {
        return new HttpResponseVO(false, HttpResponseEnum.EXCEPTION.name(), e.getMessage());
    }

    /**
     * 格式处理
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }
}
