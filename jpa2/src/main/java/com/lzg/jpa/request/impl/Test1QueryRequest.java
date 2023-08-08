package com.lzg.jpa.request.impl;

import com.lzg.jpa.annotation.query.*;
import com.lzg.jpa.request.BaseQueryRequest;
import com.wuchuangroup.jpa.annotation.query.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-01 18:17
 * @since 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class Test1QueryRequest extends BaseQueryRequest {


    @GreaterThan
    private Long id;

    @GreaterThan
    @IgnoreCase
    private String indexField1;

    @Equals
    @OrderBy(targetFieldName = "indexField2")
    private String indexField2;

    @EndingWith
    @OrderBy(priority = 999, direction = OrderBy.DIRECTION.ASC)
    private String indexField3;

    @StartingWith
    @LessThan
    @NotLike
    @OrderBy(priority = 1999, direction = OrderBy.DIRECTION.DESC)
    private String varcharFiled;

    @Containing
    @IgnoreCase
    @Like
    private String textFiled;

    @Before(targetFieldName = "lastModifiedTime")
    private LocalDateTime startTime;

    @After(targetFieldName = "createTime")
    @OrderBy(priority = -1, targetFieldName = "createTime")
    private LocalDateTime endTime;

    @GreaterThanEqual
    @LessThanEqual
    private Long age;

    @In(targetFieldName = "age")
    private List<Long> idList;

    @NotIn(targetFieldName = "age")
    private List<Long> idListNotIn;

    @Between(flag = Between.Flag.START, targetFieldName = "createTime")
    private LocalDateTime betweenStartTime;


    @Between(flag = Between.Flag.END, targetFieldName = "createTime")
    private LocalDateTime betweenEndTime;

}
