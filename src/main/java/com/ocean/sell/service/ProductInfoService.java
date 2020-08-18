package com.ocean.sell.service;

import com.ocean.sell.dataobject.ProductInfo;
import com.ocean.sell.dto.CarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);
    //查询所有在架的商品列表
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CarDTO> carDTOList);
    //减库存
    void decreaseStock(List<CarDTO> carDTOList);

    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);
}
