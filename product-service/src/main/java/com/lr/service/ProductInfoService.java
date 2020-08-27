package com.lr.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lr.entity.ProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lr.vo.PageVO;
import com.lr.vo.ProductManageVO;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author Mr.Lr
 * @since 2020-08-20
 */
public interface ProductInfoService extends IService<ProductInfo> {
    public boolean subStock(Integer productId,Integer quantity);
    public PageVO<List<ProductManageVO>> findProductManageVOByPage(Integer page, Integer size);
    public ProductManageVO findById(Integer id);
    public boolean updateProductMangeVO(ProductManageVO productManageVO);
    public boolean updateStatus(Integer id,Boolean status);
}
