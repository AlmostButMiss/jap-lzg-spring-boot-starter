package com.lzg.jpa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author august
 */
@Configuration
public class LocalDateTimeFormatConfig {
    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)));
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

//    @Bean
//    public Formatter<LocalDate> localDateFormatter() {
//        return new Formatter<LocalDate>() {
//            @Override
//            public @Nullable
//            String print(@Nullable LocalDate object, @Nullable Locale locale) {
//                if (Objects.isNull(object)) {
//                    return null;
//                }
//                return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            }
//
//            @Override
//            public @Nullable
//            LocalDate parse(@Nullable String text, @Nullable Locale locale) {
//                if (!StringUtils.hasText(text)) {
//                    return null;
//                }
//                return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            }
//        };
//    }
//
//    @Bean
//    public Formatter<LocalDateTime> localDateTimeFormatter() {
//        return new Formatter<LocalDateTime>() {
//            @Override
//            public @Nullable
//            String print(@Nullable LocalDateTime object, @Nullable Locale locale) {
//                if (Objects.isNull(object)) {
//                    return null;
//                }
//                return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            }
//
//            @Override
//            public @Nullable
//            LocalDateTime parse(@Nullable String text, @Nullable Locale locale) {
//                if (!StringUtils.hasText(text)) {
//                    return null;
//                }
//                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            }
//        };
//    }
//
//    @Bean
//    public Formatter<LocalTime> localTimeFormatter() {
//        return new Formatter<LocalTime>() {
//            @Override
//            public @Nullable
//            String print(@Nullable LocalTime object, @Nullable Locale locale) {
//                if (Objects.isNull(object)) {
//                    return null;
//                }
//                return object.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//            }
//
//            @Override
//            public @Nullable
//            LocalTime parse(@Nullable String text, @Nullable Locale locale) {
//                if (!StringUtils.hasText(text)) {
//                    return null;
//                }
//                return LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss"));
//            }
//        };
//    }

}