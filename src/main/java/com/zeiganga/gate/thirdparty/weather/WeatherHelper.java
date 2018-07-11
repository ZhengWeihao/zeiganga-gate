package com.zeiganga.gate.thirdparty.weather;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zeiganga.gate.util.HttpRequestUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

    /**
     * 解析contact为map
     */
    public static Map<String, Object> getContact(String contact, String defalutNull) {
        if (StringUtils.isBlank(contact)) {// 解析contact
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

    public static void main(String[] args) {
        Map<String, Object> contactMap = getContact("\t{\"address\":\"山西省阳泉市平定县打算发顺丰\",\"address_local\":\"打算发顺丰\",\"city\":\"16\",\"province\":\"4\",\"real_name\":\"第三方\",\"telephone\":\"13322221111\"}", "-");
        System.out.println(JSON.toJSONString(contactMap));
    }
}
