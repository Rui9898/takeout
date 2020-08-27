package com.lr.exception;


import com.lr.enums.ProductEnum;

public class ProductException extends RuntimeException{
    public ProductException(ProductEnum productEnum) {
        super(productEnum.getMsg());
    }
}
