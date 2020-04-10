package org.study.mybatis.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * 日期字符串->日期对象
 * @author lipo
 * @date 2020-04-10 14:09
 */
@JsonComponent
@Slf4j
public class DateDeserialize extends JsonDeserializer<Date> {
    private String[] patterns = new String[] {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateString = jsonParser.getText();
        try {
            return DateUtils.parseDate(dateString, patterns);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
