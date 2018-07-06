package com.zeiganga.gate.schedule;

import com.zeiganga.gate.thirdparty.dingtalk.DingtalkMessageSender;
import com.zeiganga.gate.thirdparty.weather.Weather;
import com.zeiganga.gate.thirdparty.weather.WeatherHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 消息通知定时任务
 * User: ZhengWeihao
 * Date: 2018-06-30
 * Time: 16:01:08
 */
@Component
public class NotifySchedule {

    private static final int RETRY_MAX_TIME = 10;// 查询失败的最大重试次数
    private static int RETRY_TIME = 0;// 当前重试次数
    private static final long RETRY_INTERVAL = 60000;// 重试间隔时间

    /**
     * 测试通知
     */
    @Scheduled(cron = "0 0 20 * * ?")
    public void weatherNotify() {
        checkRanyWeather(false);
    }

    /**
     * 检查次日天气是否为雨天
     */
    public static void checkRanyWeather(boolean retry) {
        if (!retry) {
            RETRY_TIME = 0;
        } else if (++RETRY_TIME <= RETRY_MAX_TIME) {// 超过最大重试次数
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Weather weather = WeatherHelper.getByCityNameAndDate("杭州", calendar.getTime());
        if (weather == null) {// 尝试重试
            try {
                Thread.sleep(RETRY_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkRanyWeather(true);
        } else {
            String type = weather.getType();
            if (type.contains("雨")) {
                String content = "明天天气：" + type + "，" + weather.getNotice();
                DingtalkMessageSender.sendSimpleMessage(DingtalkMessageSender.EATING_GROUP_MACHINE_WEBHOOK, content, true);
            }
        }
    }

}
