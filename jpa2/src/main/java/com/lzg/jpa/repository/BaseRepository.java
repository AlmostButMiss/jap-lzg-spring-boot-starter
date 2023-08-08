package com.lzg.jpa.repository;

import com.lzg.jpa.entity.BaseEntity;
import com.lzg.jpa.request.QueryRequest;
import com.lzg.jpa.request.UpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-07-31 11:03
 * @since 1.0
 **/
public interface BaseRepository<T extends BaseEntity, ID extends java.io.Serializable> extends
        JpaRepository<T, ID>,
        JpaSpecificationExecutor<T> {


    /**
     * d
     *
     * @param request       d
     * @param repositoryClz d
     * @param <S>           d
     * @param <Request>     d
     * @return d
     */
    @Modifying
    @Transactional(rollbackFor = RuntimeException.class)
    <S extends T, Request extends UpdateRequest, Repository extends JpaRepository<S, ID>> S update(Request request, Class<Repository> repositoryClz);


    /**
     * s
     *
     * @param queryRequest s
     * @return
     */
    List<T> findEntityListBySpec(QueryRequest queryRequest);


    /**
     * s
     *
     * @param queryRequest  s
     * @param pageable      s
     * @return
     */
    Page<T> findEntityPageBySpec(QueryRequest queryRequest, Pageable pageable);

}
