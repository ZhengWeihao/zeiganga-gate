package com.zeiganga.gate.controller.notifycontrol;

import com.zeiganga.gate.enums.HttpResponseEnum;
import com.zeiganga.gate.logger.CustomLogger;
import com.zeiganga.gate.schedule.SleepNotifySchedule;
import com.zeiganga.gate.vo.common.HttpResponseVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 睡眠提醒控制
 *
 * @Author: ZhengWeihao
 * @Date: 2018/8/17
 * @Time: 13:21
 */
@Controller
@RequestMapping("sleep/control")
public class SleepNotifyController {

    private static final CustomLogger logger = CustomLogger.getLogger(SleepNotifyController.class);

    /**
     * 页面
     */
    @RequestMapping("page")
    public String page() {
        return "notifyControl/sleepNotifyControl";
    }

    /**
     * 睡眠标识
     */
    @ResponseBody
    @RequestMapping("sleeped")
    public HttpResponseVO sleeped() {
        SleepNotifySchedule.NOTIFY_MAP.put(SleepNotifySchedule.KEY_SLEEPED, "1");
        logger.biz("[睡眠提醒]打入睡眠标识");
        return new HttpResponseVO(true, HttpResponseEnum.SUCCESS.getCode(), HttpResponseEnum.SUCCESS.getMsg());
    }

    /**
     * 起床标识
     */
    @ResponseBody
    @RequestMapping("getuped")
    public HttpResponseVO getuped() {
        SleepNotifySchedule.NOTIFY_MAP.put(SleepNotifySchedule.KEY_GETUPED, "1");
        logger.biz("[起床提醒]打入起床标识");
        return new HttpResponseVO(true, HttpResponseEnum.SUCCESS.getCode(), HttpResponseEnum.SUCCESS.getMsg());
    }

}
