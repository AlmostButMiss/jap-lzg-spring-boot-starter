package com.lzg.jpa.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.Order;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-03 20:30
 * @since 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderObj {

    private int priority;

    private Order order;
}
