package com.lzg.jpa.repository.impl;

import com.lzg.jpa.entity.BaseEntity;
import com.lzg.jpa.enums.LogicalDeleteEnum;
import com.lzg.jpa.repository.BaseRepository;
import com.lzg.jpa.request.QueryRequest;
import com.lzg.jpa.request.UpdateRequest;
import com.lzg.jpa.util.CommonUtils;
import com.lzg.jpa.util.JpaPredicateUtils;
import com.lzg.jpa.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * @author august
 */
@NoRepositoryBean
@Slf4j
public class BaseRepositoryImpl<T extends BaseEntity, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {

    /**
     *
     */
    private final EntityManager em;

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.em = em;
    }


    @Override
    @NonNull
    public <S extends T> S save(@NonNull S entity) {
        // 保存重要数据时可以添加日志
        return super.save(entity);
    }


    @Override
    public <S extends T, Request extends UpdateRequest, Repository extends JpaRepository<S, ID>> S update(Request request, Class<Repository> repositoryClz) {

        //noinspection unchecked
        S entity = SpringContextUtils.getBean(repositoryClz)
                .findById((ID) request.getId())
                .orElseThrow(() -> new RuntimeException("id " + request.getId() + " not exist！"));

        BeanUtils.copyProperties(request, entity, CommonUtils.getNullPropertyNames(request));
        return save(entity);
    }


    @Override
    public List<T> findEntityListBySpec(QueryRequest queryRequest) {
        return findAll(JpaPredicateUtils.getSpecification(queryRequest));
    }

    @Override
    public Page<T> findEntityPageBySpec(QueryRequest queryRequest, Pageable pageable) {
        return findAll(JpaPredicateUtils.getSpecification(queryRequest), pageable);
    }

    @Override
    @NonNull
    public <S extends T> S saveAndFlush(@NonNull S entity) {
        return super.saveAndFlush(entity);
    }

    @Override
    @NonNull
    public <S extends T> List<S> saveAll(@NonNull Iterable<S> entities) {
        return super.saveAll(entities);
    }

    @Modifying
    @Override
    public void deleteById(@NonNull ID id) {
        log.info("====================== delete id:" + id);

        findById(id).ifPresent(entity -> {
            entity.setLogicalDelete(LogicalDeleteEnum.DELETED);
            save(entity);
        });
    }

    @Override
    public void delete(@NonNull T entity) {
        //noinspection unchecked
        deleteById((ID) entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteInBatch(@NonNull Iterable<T> entities) {
        deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        deleteAll(findAll());
    }

    @Override
    public void deleteAllInBatch() {
        log.info(" ===================== deleteAllInBatch");
    }

}