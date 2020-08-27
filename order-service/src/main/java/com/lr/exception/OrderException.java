package com.lr.exception;

import com.lr.enums.OrderEnum;

public class OrderException extends RuntimeException{

    public OrderException(OrderEnum orderEnum) {
        super(orderEnum.getMsg());

    }
}
