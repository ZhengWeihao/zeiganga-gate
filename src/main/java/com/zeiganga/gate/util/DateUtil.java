package com.zeiganga.gate.util;

import com.zeiganga.gate.enums.DateFormatEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具
 * User: ZhengWeihao
 * Date: 2018/6/29
 * Time: 11:48
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DateFormatEnum.DATE.getFormat());
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(DateFormatEnum.DATETIME.getFormat());

    private DateUtil() {
        super();
    }

    /**
     * 格式化日期
     */
    public static final String formatDate(Date date) {
        return formatDate(date, DATE_FORMAT);
    }

    /**
     * 格式化时间
     */
    public static final String formatDatetime(Date date) {
        return formatDate(date, DATETIME_FORMAT);
    }

    /**
     * 根据格式化工具格式化时间
     */
    private static final String formatDate(Date date, SimpleDateFormat dateFormat) {
        if (date == null || dateFormat == null) {
            return null;
        }

        return dateFormat.format(date);
    }

    /**
     * 解析日期
     */
    public static final Date parseDate(String dateStr) {
        return parseDate(dateStr, DATE_FORMAT);
    }

    /**
     * 解析时间
     */
    public static final Date parseDatetime(String dateStr) {
        return parseDate(dateStr, DATETIME_FORMAT);
    }

    /**
     * 根据格式化工具解析时间字符串
     */
    private static final Date parseDate(String dateStr, SimpleDateFormat dateFormat) {
        if (StringUtils.isBlank(dateStr) || dateFormat == null) {
            return null;
        }

        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            logger.error("解析日期异常：", e);
        }

        return null;
    }

}
