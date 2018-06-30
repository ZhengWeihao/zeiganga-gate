package com.zeiganga.gate.editor;

import com.zeiganga.gate.enums.DateFormatEnum;
import com.zeiganga.gate.util.DateUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateEditor extends PropertyEditorSupport {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DateFormatEnum.DATE.getFormat());
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(DateFormatEnum.DATETIME.getFormat());

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isParsable(text)) {// 尝试解析时间戳格式
            long timestamp = NumberUtils.toLong(text);
            setValue(new Date(timestamp));
        } else {// 尝试解析字符串格式
            Date date = null;
            try {
                date = DATETIME_FORMAT.parse(text);
            } catch (Exception e) {
                try {
                    date = DATE_FORMAT.parse(text);
                } catch (Exception e1) {
                }
            }
            setValue(date);
        }
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        if (value == null) {
            return null;
        }
        Long timestamp = value.getTime();
        return timestamp.toString();
    }

}
