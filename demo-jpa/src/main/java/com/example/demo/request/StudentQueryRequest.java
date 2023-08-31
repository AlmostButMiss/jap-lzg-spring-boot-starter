package com.example.demo.request;

import com.lzg.jpa.annotation.query.Equals;
import com.lzg.jpa.annotation.query.GreaterThan;
import com.lzg.jpa.annotation.query.StartingWith;
import com.lzg.jpa.request.BaseQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-08 16:35
 * @since 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentQueryRequest extends BaseQueryRequest {

    @Equals
    private String name;
    @GreaterThan
    private Integer age;
    @StartingWith
    private String idCard;

}
