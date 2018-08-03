package com.zeiganga.gate.websocket.controller;

import com.alibaba.fastjson.JSON;
import com.zeiganga.gate.logger.CustomLogger;
import com.zeiganga.gate.util.DateUtil;
import com.zeiganga.gate.websocket.domain.WsMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 测试控制器
 *
 * @Author: ZhengWeihao
 * @Date: 2018/8/3
 * @Time: 10:06
 */
@Controller
@EnableScheduling
public class WsHelloController {

    private static final CustomLogger logger = CustomLogger.getLogger(WsHelloController.class);

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/wsWelcome")
    public String wsWelcome() {
        return "wsWelcome";
    }

    /**
     * 接受客户端消息推送
     */
    @MessageMapping("/send")
    @SendTo("/topic/send")
    public WsMessage send(WsMessage message) throws Exception {
        logger.biz("收到ws消息：{}", JSON.toJSONString(message));

        WsMessage response = new WsMessage();
        response.setMessage(DateUtil.formatDatetime(new Date()));
        return response;
    }

    /**
     * 每隔1秒推送服务器端的时间
     */
    @Scheduled(fixedRate = 1000)
    @SendTo("/topic/callback")
    public Object callback() throws Exception {
        simpMessagingTemplate.convertAndSend("/topic/callback", DateUtil.formatDatetime(new Date()));
        return "callback";
    }
}
