package com.lzg.jpa.entity;

import com.lzg.jpa.enums.LogicalDeleteEnum;
import com.lzg.jpa.listener.SimpleAuditingEntityListener;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : liuzg
 * @date : 2023-07-31 10:40
 * @since 1.0
 **/
@Data
@MappedSuperclass
@EntityListeners(SimpleAuditingEntityListener.class)
public class BaseEntity {

    /**
     * 主键自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version = 1L;

    private String createUserId;

    private String lastModifiedUserId;

    private LocalDateTime createTime;

    private LocalDateTime lastModifiedTime;

    @Convert(converter = LogicalDeleteEnum.LogicalDeleteConverter.class)
    private LogicalDeleteEnum logicalDelete = LogicalDeleteEnum.UN_DELETE;
}