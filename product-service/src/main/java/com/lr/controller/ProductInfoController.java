package com.lr.controller;


import com.lr.entity.ProductInfo;
import com.lr.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author Mr.Lr
 * @since 2020-08-20
 */
@RestController
@RequestMapping("//productInfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;

    @GetMapping("/list")
    public List<ProductInfo> list(){
        return this.productInfoService.list();
    }
    @PutMapping("/subStock/{id}/{quantity}")
    public boolean subStock(@PathVariable("id") Integer id,
                            @PathVariable("quantity") Integer quantity){
        return this.productInfoService.subStock(id,quantity);

    }
}

