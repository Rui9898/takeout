package com.lr.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("product")
public interface OrderFeign {
    @PutMapping("/productInfo/subStock/{id}/{quantity}")
    public boolean subStack(@PathVariable("id") Integer id,
                            @PathVariable("quantity") Integer quantity);

}
