package com.lzg.jpa.repository;

import com.lzg.jpa.entity.Test1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-07-31 14:21
 * @since 1.0
 **/
public interface Test1Repository extends BaseRepository<Test1, Long> {


    List<Test1> findAllByIndexFieldLike(String value);

    List<Test1> findAllByIndexFieldNotLike(String value);

    List<Test1> findAllByIndexFieldStartingWith(String value);

    List<Test1> findAllByIndexFieldEndingWith(String value);

    List<Test1> findAllByIndexFieldContaining(String value);

    long countDistinctByTextFiled(String value);

    Page<Test1> findAllByTextFiled(String value, Pageable pageable);
}
