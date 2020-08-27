package com.lr.util;

import com.lr.entity.ProductInfo;
import com.lr.vo.ProductInfoVO;

/**
 * 实体类转VO
 */
public class EntityToVO {

    public static ProductInfoVO ProductInfoToProductInfoVO(ProductInfo productInfo){
        ProductInfoVO result = new ProductInfoVO();
        result.setId(productInfo.getProductId());
        result.setDescription(productInfo.getProductDescription());
        result.setIcon(productInfo.getProductIcon());
        result.setName(productInfo.getProductName());
        result.setPrice(productInfo.getProductPrice());
        result.setStock(productInfo.getProductStock());
        return result;
    }
}
