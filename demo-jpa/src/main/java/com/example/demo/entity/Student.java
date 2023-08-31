package com.example.demo.entity;

import com.lzg.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-08 15:40
 * @since 1.0
 **/

@EqualsAndHashCode(callSuper = true)
@Entity(
        name = "Student"
)
@Table(
        name = "jpa_student",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "name_age_unique_key",
                        columnNames = {"name", "age"}
                ),
                @UniqueConstraint(
                        name = "id_card_unique_key",
                        columnNames = {"idCard"}
                )},
        indexes = {
                @Index(
                        name = "name_index",
                        columnList = "name",
                        unique = true
                ),
                @Index(
                        name = "age_id_card_index",
                        columnList = "age,idCard",
                        unique = false
                )}
)
@Where(
        clause = " logical_delete = 0 "
)
@DynamicUpdate
@Data
public class Student extends BaseEntity {

    private String name;
    private Integer age;
    private String idCard;

    public Student() {
    }

    public Student(String name, Integer age, String idCard) {
        this.name = name;
        this.age = age;
        this.idCard = idCard;
    }
}
