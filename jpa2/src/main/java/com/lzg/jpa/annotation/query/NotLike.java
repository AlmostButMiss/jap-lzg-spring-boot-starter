package com.lzg.jpa.annotation.query;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-02 11:52
 * @since 1.0
 **/
@Target({FIELD})
@Retention(RUNTIME)
public @interface NotLike {
    String targetFieldName() default "";
}


