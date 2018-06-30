package com.zeiganga.gate.schedule;

import com.zeiganga.gate.thirdparty.dingtalk.MachineMessageSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 消息通知定时任务
 * User: ZhengWeihao
 * Date: 2018-06-30
 * Time: 16:01:08
 */
@Component
public class NotifySchedule {

    private static Boolean notified = false;

    /**
     * 测试通知
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void testNotify() {
        if (notified) {// 不重复通知
            return;
        }

        for (int i = 0; i < 10; i++) {
            String content = "起床尿尿了！";
            boolean atAll = true;
            MachineMessageSender.sendSimpleMessage(MachineMessageSender.EATING_GROUP_MACHINE_WEBHOOK, content, atAll);
        }
        notified = true;
    }
}
