package com.lzg.jpa.request.impl;

import com.lzg.jpa.annotation.query.Between;
import com.lzg.jpa.annotation.query.GreaterThan;
import com.lzg.jpa.annotation.query.In;
import com.lzg.jpa.annotation.query.OrderBy;
import com.lzg.jpa.request.BaseQueryRequest;
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
public class Test1PageQueryRequest extends BaseQueryRequest {


    @GreaterThan
    private Long id;

    private String indexField1;

    @OrderBy(priority = 1999, direction = OrderBy.DIRECTION.DESC)
    private String indexField2;

    private String indexField3;

    private String varcharFiled;

    private String textFiled;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OrderBy(priority = 111, direction = OrderBy.DIRECTION.DESC)
    private Long age;

    @In(targetFieldName = "id")
    private List<Long> idList;

    private List<Long> idListNotIn;


    @OrderBy(priority = 222, direction = OrderBy.DIRECTION.DESC, targetFieldName = "createTime")
    @Between(flag = Between.Flag.START, targetFieldName = "createTime")
    private LocalDateTime betweenStartTime;

    @Between(flag = Between.Flag.END, targetFieldName = "createTime")
    private LocalDateTime betweenEndTime;

}
