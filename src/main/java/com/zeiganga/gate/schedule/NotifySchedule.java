package com.zeiganga.gate.schedule;

import com.alibaba.fastjson.JSON;
import com.zeiganga.gate.logger.CustomLogger;
import com.zeiganga.gate.thirdparty.dingtalk.DingtalkMessageSender;
import com.zeiganga.gate.thirdparty.weather.Weather;
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
     * 查询失败的最大重试次数
     */
    private static final int RETRY_MAX_TIME = 10;
    /**
     * 当前重试次数
     */
    private static int RETRY_TIME = 0;
    /**
     * 重试间隔时间
     */
    private static final long RETRY_INTERVAL = 60000;
    /**
     * 需要提醒的关键字
     */
    private static final String NOTIFY_WORD = "雨";

    /**
     * 测试通知
     */
    @Scheduled(cron = "0 0 20 * * ?")
    public void weatherNotify() {
        logger.biz("进行次日天气检查");
        checkRanyWeather(false);
    }

    /**
     * 检查次日天气是否为雨天
     */
    public static void checkRanyWeather(boolean retry) {
        if (!retry) {
            RETRY_TIME = 0;
        } else if (++RETRY_TIME >= RETRY_MAX_TIME) {
            // 超过最大重试次数
            logger.biz("达到最大重试次数，放弃，RETRY_TIME：{}，RETRY_MAX_TIME：{}", RETRY_TIME, RETRY_MAX_TIME);
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Weather weather = WeatherHelper.getByCityNameAndDate("杭州", calendar.getTime());
        if (weather == null) {
            // 尝试重试
            try {
                Thread.sleep(RETRY_INTERVAL);
            } catch (InterruptedException e) {
                logger.error("暂停异常：", e);
            }
            checkRanyWeather(true);
        } else {
            logger.biz("查询到次日天气，weather：{}", JSON.toJSONString(weather));
            String type = weather.getType();
            boolean needNotify = type.contains(NOTIFY_WORD);
            if (needNotify) {
                String content = "明天天气：" + type + "，" + weather.getFx() + " " + weather.getFl() + "，最低温度"
                        + weather.getLow() + "，" + weather.getNotice();
                DingtalkMessageSender.sendSimpleMessage(DingtalkMessageSender.EATING_GROUP_MACHINE_WEBHOOK, content, true);
                logger.biz("完成天气预警通知，content：{}", content);
                return;
            }
            logger.biz("天气检查完成，type：{}，needNotify：{}", type, needNotify);
        }
    }

}
