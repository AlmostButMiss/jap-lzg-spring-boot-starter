package com.lzg.jpa.listener;

import com.lzg.jpa.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-07-25 17:15
 * @since 1.0
 **/
@Slf4j
public class SimpleAuditingEntityListener {

    @PrePersist
    private void prePersist(BaseEntity entity) {

        LocalDateTime now = LocalDateTime.now();
        entity.setCreateTime(now);
        entity.setLastModifiedTime(now);
        entity.setCreateUserId("todo setCreateUserId");
        entity.setLastModifiedUserId("todo setLastModifiedUserId");

        log.info("prePersist::{}", entity.toString());
    }

    @PostPersist
    public void postPersist(BaseEntity entity) {
        log.info("postPersist::{}", entity.toString());
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setLastModifiedUserId("update-setLastModifiedUserId");
        entity.setLastModifiedTime(LocalDateTime.now());

        log.info("preUpdate::{}", entity.toString());
    }

    @PostUpdate
    public void postUpdate(BaseEntity entity) {

        log.info("postUpdate::{}", entity.toString());
    }

    @PreRemove
    public void preRemove(BaseEntity entity) {
        log.info("preRemove::{}", entity.toString());
    }

    @PostRemove
    public void postRemove(BaseEntity entity) {
        log.info("postRemove::{}", entity.toString());
    }

    @PostLoad
    public void postLoad(BaseEntity entity) {
        // 查询方法里面可以对一些敏感信息做一些日志
        log.info("postLoad::{}", entity.toString());
    }
}