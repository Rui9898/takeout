package com.lr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lr.entity.OrderDetail;
import com.lr.entity.OrderMaster;
import com.lr.entity.ProductInfo;
import com.lr.enums.OrderEnum;
import com.lr.exception.OrderException;
import com.lr.feign.OrderFeign;
import com.lr.form.OrderForm;
import com.lr.mapper.OrderDetailMapper;
import com.lr.mapper.OrderMasterMapper;
import com.lr.mapper.ProductInfoMapper;
import com.lr.service.OrderMasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lr.util.OrderFormToOrderMaster;
import com.lr.vo.OrderVO;
import com.lr.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author Mr.Lr
 * @since 2020-08-20
 */
@Service
@Slf4j
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements OrderMasterService {
    @Autowired
    private OrderMasterMapper orderMasterMapper;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private OrderFeign orderFeign;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private WebSocket webSocket;
    @Override
    public String create(OrderForm orderForm) {
        //保存OrderMaster
        OrderMaster orderMaster = OrderFormToOrderMaster.transfor(orderForm);
        List<OrderDetail> items = orderForm.getItems();
        BigDecimal amout = new BigDecimal(0);
        for (OrderDetail item : items) {
            amout =  amout.add(this.productInfoMapper.getPriceById(item.getProductId()).multiply(new BigDecimal(item.getProductQuantity())));
            //减库存
            this.orderFeign.subStack(item.getProductId(),item.getProductQuantity());
        }
        orderMaster.setOrderAmount(amout);
        int orderMasterRow = orderMasterMapper.insert(orderMaster);
        for (OrderDetail item : items) {
            //保存订单详情
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderMaster.getOrderId());
            ProductInfo productInfo = this.productInfoMapper.selectById(item.getProductId());
            orderDetail.setProductQuantity(item.getProductQuantity());
            orderDetail.setProductId(item.getProductId());
            orderDetail.setProductIcon(productInfo.getProductIcon());
            orderDetail.setProductName(productInfo.getProductName());
            orderDetail.setProductPrice(productInfo.getProductPrice());
            this.orderDetailMapper.insert(orderDetail);
        }
        //通知后台管理系统
        this.webSocket.sendMessage("有新的订单");
        if(orderMasterRow == 1) return orderMaster.getOrderId();
        return null;
    }

    @Override
    public OrderVO getVOByBuyerIdAndOrderId(String buyerId, String orderId) {
        if (buyerId == null){
            log.info("[订单详情],参数异常，buyerId={}",buyerId);
            throw new OrderException(OrderEnum.ORDER_PARAM_ERROR);
        }
        if(orderId == null){
            log.info("[订单详情]，参数异常，orderId={}",orderId);
            throw new OrderException(OrderEnum.ORDER_PARAM_ERROR);
        }
        //通过buyerId orderId查询OrderMaster
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("buyer_openid",buyerId);
        wrapper.eq("order_id",orderId);
        OrderMaster orderMaster = this.orderMasterMapper.selectOne(wrapper);
        if(orderMaster == null){
            log.info("[订单详情],订单不存在，orderMaster={}",orderMaster);
            throw new OrderException(OrderEnum.ORDER_CREATE_FAIL);
        }
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orderMaster,orderVO);
        //查询OrderMaster下属的OrderDetail完成封装
        wrapper = new QueryWrapper();
        wrapper.eq("order_id",orderId);
        List<OrderDetail> orderDetailList = this.orderDetailMapper.selectList(wrapper);
        //VO
        orderVO.setOrderDetailList(orderDetailList);
        return orderVO;
    }

    @Override
    public List<OrderVO> getVOListByBuyerId(String buyerId,Integer page,Integer size){
        if (buyerId == null){
            log.info("[订单详情],参数异常，buyerId={}",buyerId);
            throw new OrderException(OrderEnum.ORDER_PARAM_ERROR);
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("buyer_openid",buyerId);
        Page<OrderMaster> orderMasterPage = new Page<>(page,size);
        Page<OrderMaster> result = this.orderMasterMapper.selectPage(orderMasterPage, wrapper);
        List<OrderMaster> list =  result.getRecords();
        List<OrderVO> orderVOList = new ArrayList<>();
        OrderVO orderVO = null;
        for (OrderMaster orderMaster : list) {
            orderVO = new OrderVO();
            BeanUtils.copyProperties(orderMaster,orderVO);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public boolean cancel( String buyerId, String orderId) {
        //取消订单
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("buyer_openid",buyerId);
//        wrapper.eq("order_id",orderId);
//        OrderMaster orderMaster = this.orderMasterMapper.selectOne(wrapper);
//        if(orderMaster == null){
//            log.info("[取消订单]，订单不存在，orderMaster={}",orderMaster);
//            throw new OrderException(OrderEnum.ORDER_NOT_EXIST);
//        }
//        if(orderMaster.getOrderStatus() != 0){
//            log.info("[取消订单]，订单已取消，orderMaster={}",orderMaster);
//            throw new OrderException(OrderEnum.ORDER_STATUS_ERROR);
//        }
//        orderMaster.setOrderStatus(2);
//        int row = this.orderMasterMapper.updateById(orderMaster);
//        if(row == 1)return true;
//        return false;
        int row = this.orderMasterMapper.cancel(buyerId, orderId);
        if (row == 1) return true;
        return false;
    }

    @Override
    public boolean finish(String orderId) {
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("order_id",orderId);
//        OrderMaster orderMaster = this.orderMasterMapper.selectOne(wrapper);
//        Integer orderStatus = orderMaster.getOrderStatus();
//        if(orderMaster == null){
//            log.info("[完成订单]，订单不存在，orderMaster={}",orderMaster);
//            throw new OrderException(OrderEnum.ORDER_NOT_EXIST);
//        }
//        if(orderMaster.getOrderStatus() != 0){
//            log.info("[完成订单]，订单状态异常，orderMaster={}",orderMaster);
//            throw new OrderException(OrderEnum.ORDER_STATUS_ERROR);
//        }
//        orderMaster.setOrderStatus(1);
//        int row = this.orderMasterMapper.updateById(orderMaster);
//        if(row == 1)return true;
//        return false;
        int row = this.orderMasterMapper.finish(orderId);
        if (row == 1) return true;
        return false;
    }

    @Override
    public boolean pay(String buyerId, String orderId) {
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("buyer_openid",buyerId);
//        wrapper.eq("order_id",orderId);
//        OrderMaster orderMaster = this.orderMasterMapper.selectOne(wrapper);
//        if(orderMaster == null){
//            log.info("[支付订单]，订单不存在，orderMaster={}",orderMaster);
//            throw new OrderException(OrderEnum.ORDER_NOT_EXIST);
//        }
//        if(orderMaster.getPayStatus() != 0){
//            log.info("[支付订单]，订单状态异常，orderMaster={}",orderMaster);
//            throw new OrderException(OrderEnum.PAY_STATUS_ERROR);
//        }
//        orderMaster.setPayStatus(1);
//        int row = this.orderMasterMapper.updateById(orderMaster);
//        if(row == 1)return true;
//        return false;
        int row = this.orderMasterMapper.pay(buyerId, orderId);
        if (row == 1) return true;
        return false;
    }

    @Override
    public PageVO<List<OrderMaster>> getOrderByPage(Integer page, Integer size) {
        Page<OrderMaster> condition = new Page<>(page,size);
        Page<OrderMaster> result  = this.orderMasterMapper.selectPage(condition,null);
        PageVO<List<OrderMaster>> pageVO = new PageVO<>();
        pageVO.setContent(result.getRecords());
        pageVO.setSize(result.getSize());
        pageVO.setTotal(result.getTotal());
        return pageVO;
    }

    @Override
    public boolean handler(String type, String orderId) {
        OrderMaster orderMaster = this.orderMasterMapper.selectById(orderId);
        if(orderMaster.getOrderStatus() != 0){
            log.info("[处理订单]，订单状态异常，orderMaster={}",orderMaster);
            throw new OrderException(OrderEnum.ORDER_STATUS_ERROR);
        }
        int row = 0;
        switch (type){
            case "cancel":
                row = this.orderMasterMapper.handler(2,orderId);
                break;
            case "finish":
                row = this.orderMasterMapper.handler(1,orderId);
                break;
        }
        if (row ==1)return true;
        return false;
    }
}
