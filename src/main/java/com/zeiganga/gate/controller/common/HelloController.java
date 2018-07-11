package com.zeiganga.gate.controller.common;

import com.google.common.collect.Maps;
import com.zeiganga.gate.enums.HttpResponseEnum;
import com.zeiganga.gate.logger.CustomLogger;
import com.zeiganga.gate.util.DateUtil;
import com.zeiganga.gate.vo.common.HttpResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * 启动测试相关接口
 * User: ZhengWeihao
 * Date: 2018/6/29
 * Time: 10:05
 */
@Controller
@RequestMapping("common")
public class HelloController {

    private static final CustomLogger logger = CustomLogger.getLogger(HelloController.class);

    /**
     * 检查服务是否完成启动
     */
    @ResponseBody
    @RequestMapping("started")
    public HttpResponseVO started() throws Exception {
        logger.biz("received started request ..");
        logger.error("received started request ..");
        return new HttpResponseVO(true, HttpResponseEnum.SUCCESS.name(), HttpResponseEnum.SUCCESS.getMsg());
    }
}
