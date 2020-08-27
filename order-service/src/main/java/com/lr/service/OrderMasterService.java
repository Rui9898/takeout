package com.lr.service;

import com.lr.entity.OrderMaster;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lr.form.OrderForm;
import com.lr.vo.OrderVO;
import com.lr.vo.PageVO;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author Mr.Lr
 * @since 2020-08-20
 */
public interface OrderMasterService extends IService<OrderMaster> {
        public String create(OrderForm orderForm);
        public OrderVO getVOByBuyerIdAndOrderId(String buyerId,String orderId);
        public List<OrderVO> getVOListByBuyerId(String buyerId,Integer current,Integer size);
        public boolean cancel(String buyerId,String orderId);
        public boolean finish(String orderId);
        public boolean pay(String buyerId,String orderId);
        public PageVO<List<OrderMaster>> getOrderByPage(Integer page,Integer size);
       //卖家端处理订单接口
        public boolean handler(String type,String orderId);
}
