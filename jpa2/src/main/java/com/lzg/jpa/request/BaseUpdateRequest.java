package com.lzg.jpa.request;

import java.io.Serializable;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-01 18:17
 * @since 1.0
 **/
public class BaseUpdateRequest implements UpdateRequest, Serializable {

    @Override
    public Long getId() {
        return null;
    }
}
