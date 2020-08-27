package com.lr.util;

import com.lr.entity.OrderMaster;
import com.lr.form.OrderForm;

public class OrderFormToOrderMaster {

    public static OrderMaster transfor(OrderForm orderForm){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress(orderForm.getAddress());
        orderMaster.setBuyerName(orderForm.getName());
        orderMaster.setBuyerOpenid(orderForm.getId());
        orderMaster.setBuyerPhone(orderForm.getPhone());
        return orderMaster;
    }
}
