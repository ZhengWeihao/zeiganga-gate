package com.zeiganga.gate.schedule;

import com.alibaba.fastjson.JSON;
import com.zeiganga.gate.logger.CustomLogger;
import com.zeiganga.gate.thirdparty.dingtalk.DingtalkMessageSender;
import com.zeiganga.gate.thirdparty.weather.Weather;
import com.zeiganga.gate.thirdparty.weather.WeatherChecker;
import com.zeiganga.gate.thirdparty.weather.WeatherHelper;
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
public class NotifySchedule {

    private static final CustomLogger logger = CustomLogger.getLogger(NotifySchedule.class);

    /**
     * 天气检查
     */
    @Scheduled(cron = "0 0 20 * * ?")
    public void weatherNotify() {
        logger.biz("进行次日天气检查");
        WeatherChecker.checkRanyWeather();
    }

    /**
     * 月底提示
     */
    @Scheduled(cron = "0 0 16 * * ?")
    public void monthlyNotify() {
        logger.biz("开始检查月底提醒");
        Calendar calendar = Calendar.getInstance();
        boolean isLastDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) == calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH);
        if (!isLastDayOfMonth) {
            logger.biz("不是当月最后一天，跳过此任务");
            return;
        }

        String content = "一个月又过去咯，这个月的目标都实现了吗，下个月的目标又是什么呢？";
        boolean atAll = true;
        DingtalkMessageSender.sendSimpleMessage(DingtalkMessageSender.PRIVATE_GROUP_MACHINE_WEBHOOK, content, atAll);
        logger.biz("完成月底提醒");
    }

}
