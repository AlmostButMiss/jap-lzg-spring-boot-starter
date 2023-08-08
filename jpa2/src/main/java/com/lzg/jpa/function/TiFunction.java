package com.lzg.jpa.function;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-02 18:05
 * @since 1.0
 **/
@FunctionalInterface
public interface TiFunction<O, F, T, U, R> {

    R apply(O o, F f, T t, U u) throws Exception;

}
