package com.example.demo.request;

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
public class StudentCardQueryRequest extends BaseQueryRequest {


    @GreaterThan
    private Long id;

    @GreaterThan
    private Long price;

    @StartingWith
    private String cardNumber;

}
