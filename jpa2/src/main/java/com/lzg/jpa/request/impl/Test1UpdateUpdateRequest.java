package com.lzg.jpa.request.impl;

import com.lzg.jpa.request.BaseUpdateRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-01 18:17
 * @since 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class Test1UpdateUpdateRequest extends BaseUpdateRequest {


    private Long id;

    private String indexField1;

    private String indexField2;

    private String indexField3;

    private String varcharFiled;

    private String textFiled;

}
