package com.zeiganga.gate.thirdparty.dingtalk;

import com.alibaba.fastjson.JSONObject;
import com.zeiganga.gate.util.HttpRequestUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 钉钉机器人消息
 * User: ZhengWeihao
 * Date: 2018/6/30
 * Time: 15:42
 **/
public class MachineMessageSender {

    private MachineMessageSender() {
        super();
    }

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
        HttpRequestUtil.sendPost(webhook, textMessage.toJSONString());
    }
}
