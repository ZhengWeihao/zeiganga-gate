package com.zeiganga.gate.thirdparty.dingtalk;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zeiganga.gate.util.HttpRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 钉钉机器人消息
 * User: ZhengWeihao
 * Date: 2018/6/30
 * Time: 15:42
 **/
public class DingtalkMessageSender {

    private DingtalkMessageSender() {
        super();
    }

    private static final Logger logger = LoggerFactory.getLogger(DingtalkMessageSender.class);

    public static final String EATING_GROUP_MACHINE_WEBHOOK = "https://oapi.dingtalk.com/robot/send?access_token=a4c9f7382794f8c41505fb8fef03243382ec77f2f15ad1a2ec861ccfecef5b3b";// 吃饭小组测试机器人

    /**
     * 生成文本类消息
     */
    private static JSONObject getTextMessage(String content, boolean atAll) {
        JSONObject params = new JSONObject();
        params.put("msgtype", "text");

        JSONObject text = new JSONObject();
        text.put("content", content);
        params.put("text", text);

        JSONObject at = new JSONObject();
        at.put("isAtAll", atAll);
        params.put("at", at);

        return params;
    }

    /**
     * 发送文本消息
     */
    public static void sendSimpleMessage(String webhook, String content, boolean atAll) {
        if (StringUtils.isBlank(webhook) || StringUtils.isBlank(content)) {
            return;
        }

        JSONObject textMessage = getTextMessage(content, atAll);
        Map<String, String> header = Maps.newHashMap();
        header.put("Content-Type", "application/json; charset=UTF-8");
        String response = HttpRequestUtil.sendPost(webhook, header, textMessage.toJSONString());
        logger.info("发送钉钉消息完成，response：{}", response);
    }

}
