package com.zeiganga.gate.thirdparty.weather;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zeiganga.gate.util.HttpRequestUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 天气查询工具
 * User: ZhengWeihao
 * Date: 2018/7/3
 * Time: 16:38
 */
public class WeatherHelper {

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

        String url = "https://www.sojson.com/open/api/weather/json.shtml";
        String response = HttpRequestUtil.sendGet(url, "city=" + cityName);
        if (StringUtils.isBlank(response)) {
            return null;
        }

        try {
            JSONObject responseJson = JSON.parseObject(response);
            JSONObject data = responseJson.getJSONObject("data");
            JSONArray forecast = data.getJSONArray("forecast");
            if (CollectionUtils.isEmpty(forecast)) {
                return null;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd日EEEE");
            String dateStr = sdf.format(date);
            for (int i = 0; i < forecast.size(); i++) {
                JSONObject jsonObject = forecast.getJSONObject(i);
                if (dateStr.equals(jsonObject.getString("date"))) {
                    return JSON.parseObject(jsonObject.toJSONString(), Weather.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        Weather hz = getByCityNameAndDate("杭州", new Date());
        System.out.println(JSON.toJSONString(hz));
    }
}
