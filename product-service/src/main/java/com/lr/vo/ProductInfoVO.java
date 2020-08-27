package com.lr.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoVO {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private String icon;
    private Integer quantity = 0;
    private Integer stock;
}
