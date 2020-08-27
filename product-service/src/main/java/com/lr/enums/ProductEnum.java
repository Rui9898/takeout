package com.lr.enums;

import lombok.Getter;

@Getter
public enum ProductEnum {
    PRODUCT_STOCK_ERROR(0,"商品库存异常"),
    PRODUCT_ID_NULL(1,"商品编号为空"),
    PRODUCT_NOT_EXIST(2,"商品不存在");


    private Integer code;
    private String msg;

    ProductEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
