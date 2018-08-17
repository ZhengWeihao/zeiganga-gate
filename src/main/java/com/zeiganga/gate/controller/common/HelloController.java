package com.zeiganga.gate.controller.common;

import com.zeiganga.gate.enums.HttpResponseEnum;
import com.zeiganga.gate.logger.CustomLogger;
import com.zeiganga.gate.thirdparty.weather.WeatherChecker;
import com.zeiganga.gate.vo.common.HttpResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 启动测试相关接口
 * @Author: ZhengWeihao
 * @Date: 2018/6/29
 * @Time: 10:05
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
    public HttpResponseVO started(String test) throws Exception {
        logger.biz("received started request ..");
        logger.error("received started request ..");
        return new HttpResponseVO(true, HttpResponseEnum.SUCCESS.name(), HttpResponseEnum.SUCCESS.getMsg());
    }

    /**
     * 测试天气检查功能
     */
    @ResponseBody
    @RequestMapping("testWeatherCheck")
    public HttpResponseVO testWeatherCheck() throws Exception {
        logger.biz("准备检查天气");
        WeatherChecker.checkRanyWeather();
        return new HttpResponseVO(true, HttpResponseEnum.SUCCESS.name(), HttpResponseEnum.SUCCESS.getMsg());
    }
}
