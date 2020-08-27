package com.lr.mapper;

import com.lr.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 类目表 Mapper 接口
 * </p>
 *
 * @author Mr.Lr
 * @since 2020-08-20
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    public String findNameByType(Integer type);
}
