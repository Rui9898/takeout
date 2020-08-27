package com.lr.controller;

import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.lr.entity.ProductInfo;
import com.lr.enums.ProductEnum;
import com.lr.exception.ProductException;
import com.lr.service.ProductCategoryService;
import com.lr.service.ProductInfoService;
import com.lr.util.ResultVOUtil;
import com.lr.vo.PageVO;
import com.lr.vo.ProductManageVO;
import com.lr.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller/product")
@Slf4j
public class ProductCategorySellerController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductInfoService productInfoService;

    @GetMapping("/findAllProductCategory")
    public ResultVO findAllProductCategory(){
        Map map = new HashMap();
        map.put("content",this.productCategoryService.getAllcategoryVO());
        return ResultVOUtil.success(map);
    }

    @PostMapping("/add")
    public ResultVO add(@RequestBody ProductInfo productInfo){
        this.productInfoService.save(productInfo);
        return ResultVOUtil.success(null);
    }

    @GetMapping("list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page,
                         @PathVariable("size") Integer size){
        PageVO<List<ProductManageVO>> productManageVOByPage = this.productInfoService.findProductManageVOByPage(page, size);
        return ResultVOUtil.success(productManageVOByPage);
    }

    @DeleteMapping("/delete/{id}")
    public ResultVO delete(@PathVariable("id") Integer id){
        this.productInfoService.removeById(id);
        return ResultVOUtil.success(null);
    }

    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id){
        if(id == null){
            log.info("[查询商品]，商品编号为空，id={} ",id);
            throw new ProductException(ProductEnum.PRODUCT_ID_NULL);
        }
        ProductManageVO productManageVO = this.productInfoService.findById(id);
        return ResultVOUtil.success(productManageVO);
    }

    @PutMapping("/update")
    public ResultVO update(@RequestBody ProductManageVO productManageVO){
        boolean result = this.productInfoService.updateProductMangeVO(productManageVO);
        if(!result) return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }

    @PutMapping("/updateStatus/{id}/{status}")
    public ResultVO updateStatus(@PathVariable("id") Integer id,@PathVariable("status") Boolean status){
        boolean result = this.productInfoService.updateStatus(id, status);
        if(!result) return ResultVOUtil.fail();
        return ResultVOUtil.success(status);
    }

}
