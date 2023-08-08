package com.lzg.jpa.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static com.lzg.jpa.util.NumberConstants.PAGE_OF_PAGEABLE;
import static com.lzg.jpa.util.NumberConstants.SIZE_OF_PAGEABLE;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-04 10:39
 * @since 1.0
 **/
public class PageUtils {

    public static PageRequest getPageRequest() {
        return PageRequest.of(PAGE_OF_PAGEABLE, SIZE_OF_PAGEABLE);
    }


    public static PageRequest getPageRequest(int page, int size) {
        return getPageRequest(page, size, Sort.unsorted());
    }

    public static PageRequest getPageRequest(Sort sort) {
        return getPageRequest(PAGE_OF_PAGEABLE, SIZE_OF_PAGEABLE, sort);
    }

    public static PageRequest getPageRequest(int page, int size, Sort sort) {
        return PageRequest.of(page, size, sort);
    }

}
