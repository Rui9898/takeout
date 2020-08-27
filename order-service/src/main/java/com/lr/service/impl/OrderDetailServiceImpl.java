package com.lr.service.impl;

import com.lr.entity.OrderDetail;
import com.lr.mapper.OrderDetailMapper;
import com.lr.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author Mr.Lr
 * @since 2020-08-20
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
