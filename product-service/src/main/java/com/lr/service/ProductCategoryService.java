package com.lr.service;

import com.lr.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lr.vo.ProductCategoryVO;
import com.lr.vo.ResultVO;

import java.util.List;

/**
 * <p>
 * 类目表 服务类
 * </p>
 *
 * @author Mr.Lr
 * @since 2020-08-20
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    public ResultVO<List<ProductCategoryVO>> voList();
    public List<ProductCategoryVO> getAllcategoryVO();

}
