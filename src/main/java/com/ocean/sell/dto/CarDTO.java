package com.ocean.sell.dto;

import lombok.Data;

/**
 * 购物车
 */
@Data
public class CarDTO {
    //商品Id
    private String productId;
    //商品数量
    private Integer productQuantity;

    public CarDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
