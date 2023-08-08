package com.lzg.jpa.annotation.query;

import lombok.Getter;

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
public @interface OrderBy {

    String targetFieldName() default "";

    DIRECTION direction() default DIRECTION.DESC;

    int priority() default 1;

    @Getter
    enum DIRECTION {
        /**
         *
         */
        ASC,
        /**
         *
         */
        DESC
    }
}


