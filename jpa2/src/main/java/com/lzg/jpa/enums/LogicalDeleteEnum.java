package com.lzg.jpa.enums;

import lombok.Getter;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-05-17 10:11
 * @since 1.0
 **/
@Getter
public enum LogicalDeleteEnum {

    /**
     * UN_DELETE
     */
    UN_DELETE(0),
    /**
     * DELETED
     */
    DELETED(1),
    ;

    private int isDeleted = 0;

    LogicalDeleteEnum(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    private static final Map<Integer, LogicalDeleteEnum> map = Arrays.stream(LogicalDeleteEnum.values()).collect(Collectors.toMap(
            LogicalDeleteEnum::getIsDeleted,
            item -> item
    ));

    public static LogicalDeleteEnum getLogicalDeleteEnumByIsDeleted(int isDeleted) {
        return map.getOrDefault(isDeleted, UN_DELETE);
    }

    public static class LogicalDeleteConverter implements AttributeConverter<LogicalDeleteEnum, Integer> {

        @Override
        public Integer convertToDatabaseColumn(LogicalDeleteEnum logicalDeleteEnum) {
            return logicalDeleteEnum.getIsDeleted();
        }

        @Override
        public LogicalDeleteEnum convertToEntityAttribute(Integer isDeleted) {
            return getLogicalDeleteEnumByIsDeleted(isDeleted);
        }
    }
}
