package com.zeiganga.gate.schedule;

import com.zeiganga.gate.logger.CustomLogger;
import com.zeiganga.gate.thirdparty.dingtalk.DingtalkMessageSender;
import com.zeiganga.gate.thirdparty.weather.WeatherChecker;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 消息通知定时任务
 * @Author: ZhengWeihao
 * @Date: 2018-06-30
 * @Time: 16:01:08
 */
@Component
@EnableScheduling
public class WeatherNotifySchedule {

    private static final CustomLogger logger = CustomLogger.getLogger(WeatherNotifySchedule.class);

    /**
     * 天气检查
     */
    @Scheduled(cron = "0 0 20 * * ?")
    public void weatherNotify() {
        logger.biz("进行次日天气检查");
        WeatherChecker.checkRanyWeather();
    }

}
