package com.zeiganga.gate.schedule;

import com.google.common.collect.Maps;
import com.zeiganga.gate.helper.loopexecutor.LoopExecuteBusiness;
import com.zeiganga.gate.helper.loopexecutor.LoopExecutor;
import com.zeiganga.gate.logger.CustomLogger;
import com.zeiganga.gate.thirdparty.dingtalk.DingtalkMessageSender;
import com.zeiganga.gate.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 睡眠提醒任务
 *
 * @Author: ZhengWeihao
 * @Date: 2018/8/17
 * @Time: 11:17
 */
@Component
@EnableScheduling
public class SleepNotifySchedule {

    private static final CustomLogger logger = CustomLogger.getLogger(WeatherNotifySchedule.class);

    public static final Map<String, Object> NOTIFY_MAP = Maps.newConcurrentMap();
    public static final String KEY_SLEEPED = "sleeped";
    public static final String KEY_SLEEPED_LATER = "sleeped_later";
    public static final String KEY_GETUPED = "getuped";
    public static final long SLEEP_MILLIS = 30000L;

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

        String content = "一个月过去了，任务都踏马完成了吗，还有心情出去玩吗？ヾ(￣▽￣)";
        boolean atAll = true;
        DingtalkMessageSender.sendSimpleMessage(DingtalkMessageSender.PRIVATE_GROUP_MACHINE_WEBHOOK, content, atAll);
        logger.biz("完成月底提醒");
    }

    /**
     * 睡觉提醒
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void sleepNotify() {
        logger.biz("[睡眠提醒]开始睡眠提醒");

        LoopExecuteBusiness loopExecuteBusiness = new LoopExecuteBusiness() {
            @Override
            public void execute() {
                String content = "好睡觉了，就是这么一天天变丑的ヾ(=ﾟ･ﾟ=)ﾉ";
                boolean atAll = true;
                DingtalkMessageSender.sendSimpleMessage(DingtalkMessageSender.PRIVATE_GROUP_MACHINE_WEBHOOK, content, atAll);
            }

            @Override
            public boolean isStop() {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if (hour < 23 && hour > 2) {
                    // 只在23:00~02:00间发送
                    return true;
                }

                return StringUtils.isNotBlank((String) NOTIFY_MAP.get(KEY_SLEEPED));
            }

            @Override
            public void finallyExecute() {
                // 任务停止时判断是否晚睡
                NOTIFY_MAP.remove(KEY_SLEEPED_LATER);
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if (hour >= 0) {
                    // 0点以后睡进行晚睡标识
                    NOTIFY_MAP.put(KEY_SLEEPED_LATER, "1");
                    logger.biz("[睡眠提醒]晚睡标记,date:{}", DateUtil.formatDate(new Date()));
                }

                NOTIFY_MAP.remove(KEY_SLEEPED);
                logger.biz("[睡眠提醒]睡眠提醒结束,完成标记清除");
            }
        };
        new LoopExecutor(loopExecuteBusiness).loopExecute(SLEEP_MILLIS);

        logger.biz("[睡眠提醒]完成睡眠提醒");
    }

    /**
     * 起床提醒
     */
    @Scheduled(cron = "0 30 7 * * ?")
    public void getupNotify() {
        logger.biz("[起床提醒]开始起床提醒");

        LoopExecuteBusiness loopExecuteBusiness = new LoopExecuteBusiness() {
            @Override
            public void execute() {
                String content = "起床了！早睡早起身体好！";

                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if (hour >= 8 && StringUtils.isNotBlank((String) NOTIFY_MAP.get(KEY_SLEEPED_LATER))) {
                    content = "晚上么不睡，白天么不起，你真棒！(๑•̀ㅂ•́)و✧";
                }
                boolean atAll = true;
                DingtalkMessageSender.sendSimpleMessage(DingtalkMessageSender.PRIVATE_GROUP_MACHINE_WEBHOOK, content, atAll);
            }

            @Override
            public boolean isStop() {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if (hour < 7 && hour > 8) {
                    // 只在7:00~8:00间发送
                    return true;
                }

                return StringUtils.isNotBlank((String) NOTIFY_MAP.get(KEY_GETUPED));
            }

            @Override
            public void finallyExecute() {
                NOTIFY_MAP.remove(KEY_GETUPED);
                logger.biz("[起床提醒]起床提醒结束,完成标记清除");
            }
        };
        new LoopExecutor(loopExecuteBusiness).loopExecute(SLEEP_MILLIS);

        logger.biz("[起床提醒]完成起床提醒");
    }

}
