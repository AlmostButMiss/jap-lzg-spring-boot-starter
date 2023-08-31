package com.example.demo.entity;

import com.lzg.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-09 10:42
 * @since 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Entity(
        name = "StudentCard"
)
@Table(
        name = "jpa_student_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "card_number_unique_key",
                        columnNames = {"cardNumber"}
                )},
        indexes = {
                @Index(
                        name = "card_number_unique_index",
                        columnList = "cardNumber",
                        unique = true
                ),
                @Index(
                        name = "price_card_number_union_index",
                        columnList = "price,cardNumber",
                        unique = false
                )}
)
@Where(
        clause = " logical_delete = 0 "
)
@DynamicUpdate
@Data
public class StudentIdCard extends BaseEntity {

    private String cardNumber;

    private Long price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_id_pk"))
    private Student student;

    public StudentIdCard() {
    }

    public StudentIdCard(String cardNumber, Long price, Student student) {
        this.cardNumber = cardNumber;
        this.price = price;
        this.student = student;
    }
}
