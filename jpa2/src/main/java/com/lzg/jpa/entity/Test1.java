package com.lzg.jpa.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 实体类示例
 *
 * </p>
 *
 * <pre>
 *    &#064;Entity(name = "Test1") JPQL语法时使用
 *    &#064;Table(
 *        # 表名
 *        name = "wc_test1",
 *        uniqueConstraints = {
 *                 &#064;UniqueConstraint(
 *                         # 唯一键的name , 注意命名规则
 *                         # 生成的sql :
 *                         # alter table wc_test1 add constraint tableName_ColumnName_ColumnName_unique_key unique (name1, unique_key_field)
 *                         name = "tableName_ColumnName_ColumnName_unique_key",
 *                         # 联合唯一键示例
 *                         columnNames = {"name1", "uniqueKeyField"}),
 *                 &#064;UniqueConstraint(
 *                         # 唯一键的name , 注意命名规则
 *                         # 生成的sql :
 *                         # alter table wc_test1 add constraint tableName_ColumnName_unique_key unique (unique_key_field)
 *                         name = "tableName_ColumnName_unique_key",
 *                         # 唯一键示例
 *                         columnNames = {"uniqueKeyField"}),
 *         },
 *        indexes = {
 *                 # name 单字段索引的名字,注意命名规则,columnList:索引字段,unique 是否唯一索引 true:唯一索引,默认false
 *                 # 生成的sql :
 *                 #  alter table wc_test1 add constraint tableName_fieldName_index unique (index_field)
 *                 &#064;Index(name = "tableName_fieldName_index", columnList = "indexField", unique = true),
 *                 # name 联合索引的名字,注意命名规则,columnList:索引字段 联合索引用','隔开就可以了,unique 是否唯一索引 true:唯一索引,默认false
 *                 # 生成的sql :
 *                 #  alter table wc_test1 add constraint tableName_fieldName_index unique (index_field)
 *                 &#064;Index(name = "tableName_fieldName_fieldName1_index", columnList = "indexField,indexField1", unique = false),
 *         })
 *    &#064;Where(clause = " logical_delete = 0 ") 逻辑删除
 *    &#064;DynamicUpdate 动态更新,仅更新需要更新的字段
 * </pre>
 * <p>
 * <p>
 * 字段和表名的命名规则为驼峰转大小写，也可以手动命名
 *
 *
 * <p></p>
 * mysql字符串常用类型及其使用规范
 *
 * <ul>
 *     <li style="color:ff3300">CHAR : <i>禁止使用</i></li>
 *     <li style="color:33ff99">VARCHAR : <i>默认长度255，最长可以存储65535个字符，使用时推荐指定长度</i></li>
 *     <li style="color:33ff99">TEXT : <i>用于存储较长的字符串，最长可以存储65535个字符。</i></li>
 *     <li>BLOB : <i>用于存储二进制数据，最长可以存储65535个字节</i></li>
 *     <li style="color:ff3300">TINYTEXT : <i>禁止使用，用于存储短文本，最长可以存储255个字符</i></li>
 *     <li>MEDIUMTEXT : <i>用于存储中等长度的文本，最长可以存储16,777,215个字符。</i></li>
 *     <li>LONGTEXT : <i>用于存储非常长的文本，最长可以存储4,294,967,295个字符</i></li>
 * </ul>
 *
 * @author august
 * @see org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
 * @since Java Persistence 1.0
 * @since 1.0
 */

@Entity(name = "Test1")
@Table(
        name = "jpa_test1",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "tableName_ColumnName_ColumnName_unique_key",
                        columnNames = {"name1", "uniqueKeyField"}),
                @UniqueConstraint(
                        name = "tableName_ColumnName_unique_key",
                        columnNames = {"uniqueKeyField"})
        },

        indexes = {
                @Index(name = "tableName_fieldName_index", columnList = "indexField", unique = true),
                @Index(name = "tableName_fieldName_fieldName1_index", columnList = "indexField,indexField1", unique = false)
        })
@Where(clause = " logical_delete = 0 ")
@DynamicUpdate
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Test1 extends BaseEntity {

    @Column(name = "name1")
    private String name1;

    private String uniqueKeyField;

    private String indexField;
    private String indexField1;
    private String indexField2;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String indexField3;


    @Column(nullable = true, columnDefinition = "VARCHAR(999) comment '这是varcharFiled的备注' ")
    private String varcharFiled;

    @Column(nullable = true, columnDefinition = "TEXT comment '这是textFiled的备注' ")
    private String textFiled;

    @Column(nullable = true, columnDefinition = "MEDIUMTEXT comment '这是mediumtextFiled的备注' ")
    private String mediumtextFiled;

    @Column(nullable = true, columnDefinition = "LONGTEXT comment '这是longtextFiled的备注' ")
    private String longtextFiled;

    @Column(columnDefinition = "decimal(5,2) comment '这是doubleField4的备注' ")
    private BigDecimal doubleField4;

    private Long age;


    public Test1(String name1) {
        this.name1 = name1;
    }
}
