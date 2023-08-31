package com.example.demo.request;

import com.lzg.jpa.request.BaseUpdateRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-08 16:43
 * @since 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentUpdateRequest extends BaseUpdateRequest {

    private Long id;
    private String name;
    private Integer age;
    private String idCard;

}
