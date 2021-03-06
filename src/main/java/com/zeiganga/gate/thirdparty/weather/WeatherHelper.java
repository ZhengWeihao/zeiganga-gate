package com.zeiganga.gate.thirdparty.weather;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zeiganga.gate.logger.CustomLogger;
import com.zeiganga.gate.util.DateUtil;
import com.zeiganga.gate.util.EncodeUtil;
import com.zeiganga.gate.util.HttpRequestUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 天气查询工具
 * User: ZhengWeihao
 * Date: 2018/7/3
 * Time: 16:38
 */
public class WeatherHelper {

    private static final CustomLogger logger = CustomLogger.getLogger(WeatherHelper.class);

    private WeatherHelper() {
        super();
    }

    /**
     * 根据日期查询天气（只支持本周的日期）
     */
    public static Weather getByCityNameAndDate(String cityName, Date date) {
        if (StringUtils.isBlank(cityName) || date == null) {
            return null;
        }

        String url = "https://www.sojson.com/open/api/weather/json.shtml?city=" + EncodeUtil.urlEncode(cityName);
        String response = HttpRequestUtil.sendGet(url, "");
        if (StringUtils.isBlank(response)) {
            logger.error("响应数据为空，url：{}", url);
            return null;
        }

        try {
            JSONObject responseJson = JSON.parseObject(response);
            JSONObject data = responseJson.getJSONObject("data");
            JSONArray forecast = data.getJSONArray("forecast");
            if (CollectionUtils.isEmpty(forecast)) {
                logger.error("无法获取到天气数据，url：{}，response：{}", url, response);
                return null;
            }


            String dateStr = getDateStr(date);
            for (int i = 0; i < forecast.size(); i++) {
                JSONObject jsonObject = forecast.getJSONObject(i);
                String getDate = jsonObject.getString("date");
                boolean equals = dateStr.equals(getDate);
                if (equals) {
                    return JSON.parseObject(jsonObject.toJSONString(), Weather.class);
                }
            }
            logger.error("找不到日期对应的天气数据，response：{}，date：{}", response, dateStr);
        } catch (Exception e) {
            logger.error("解析天气异常，url：{}，response：{}", url, response, e);
        }

        return null;
    }

    /**
     * 解析contact为map
     */
    public static Map<String, Object> getContact(String contact, String defalutNull) {
        if (StringUtils.isBlank(contact)) {
            // 解析contact
            return null;
        }
        try {
            Map<String, Object> result = Maps.newHashMap();

            if (NumberUtils.isNumber(contact)) {
                result.put("phone", contact);
            } else {
                JSONObject contactJson = JSON.parseObject(contact);
                String address = contactJson.getString("address");
                result.put("addr", StringUtils.isBlank(address) ? defalutNull : address);
                String telephone = contactJson.getString("telephone");
                result.put("phone", StringUtils.isBlank(telephone) ? defalutNull : telephone);
                String realName = contactJson.getString("realName");
                result.put("realName", StringUtils.isBlank(realName) ? defalutNull : realName);
                String province = contactJson.getString("province");
                result.put("province", StringUtils.isBlank(province) ? defalutNull : province);
                String city = contactJson.getString("city");
                result.put("city", StringUtils.isBlank(city) ? defalutNull : city);
                String addressLocal = contactJson.getString("addressLocal");
                result.put("addressLocal", StringUtils.isBlank(addressLocal) ? defalutNull : addressLocal);
            }
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取符合接口日期格式的字符串
     */
    private static String getDateStr(Date date) {
        if (date == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int dayInt = c.get(Calendar.DAY_OF_MONTH);
        int weekInt = c.get(Calendar.DAY_OF_WEEK) - 1;
        return (dayInt < 10 ? "0" : "") + dayInt + "日星期" + getChineseNum(weekInt);
    }

    /**
     * 阿拉伯数字转中文
     */
    private static String getChineseNum(int i) {
        String sd = "";
        switch (i) {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 0:
                sd = "日";
                break;
            default:
                break;
        }
        return sd;
    }

}
