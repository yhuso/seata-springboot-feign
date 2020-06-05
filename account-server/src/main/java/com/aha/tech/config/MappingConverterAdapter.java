package com.aha.tech.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class MappingConverterAdapter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /***
     * 日期参数接收转换器，将字符串转为日期类型
     * @return
     */
    @Bean
    public Converter<String, LocalDate> localDateConvert() {
        return StringToLocalDateTypeConverter.INSTANCE;

    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConvert() {
        return StringToLocalDateTimeTypeConverter.INSTANCE;

    }

    enum StringToLocalDateTypeConverter implements Converter<String, LocalDate> {
        /**
         *
         */
        INSTANCE;

        @Override
        public LocalDate convert(String source) {
            if (StringUtils.isNotBlank(source)) {
                return LocalDate.parse(source);
            }
            return null;
        }
    }

    enum StringToLocalDateTimeTypeConverter implements Converter<String, LocalDateTime> {
        /**
         *
         */
        INSTANCE;

        @Override
        public LocalDateTime convert(String source) {
            if (StringUtils.isNotBlank(source)) {
                return LocalDateTime.parse(source, DATE_TIME_FORMATTER);
            }
            return null;
        }
    }
}  
