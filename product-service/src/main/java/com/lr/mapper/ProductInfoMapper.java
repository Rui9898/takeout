package com.lr.mapper;

import com.lr.entity.ProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author Mr.Lr
 * @since 2020-08-20
 */
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
        public int updateStatus(Integer id ,Integer status);
}
