package com.lr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lr.entity.ProductInfo;

import java.math.BigDecimal;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author Mr.Lr
 * @since 2020-08-20
 */
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
    public BigDecimal getPriceById(Integer id);
}
