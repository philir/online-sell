package com.ocean.sell.service.imp;

import com.ocean.sell.dataobject.ProductInfo;
import com.ocean.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImpTest {

    @Autowired
    private ProductInfoServiceImp productInfoService;

    @Test
    public void findOne() {
        ProductInfo result = productInfoService.findOne("12345");
        Assert.assertEquals("12345",result.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoLists = productInfoService.findUpAll();
        Assert.assertNotEquals(0,productInfoLists.size());
    }

    @Test
    public void findAll() {
        PageRequest request = PageRequest.of(0,2);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("465464");
        productInfo.setProductName("红米粥");
        productInfo.setProductPrice(new BigDecimal(10.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好喝的粥");
        productInfo.setProductIcon("http://xxxxxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSale(){
        ProductInfo productInfo = productInfoService.onSale("465464");
        Assert.assertNotNull(productInfo);

    }

    @Test
    public void offSale(){
        ProductInfo productInfo = productInfoService.offSale("12345");
        Assert.assertNotNull(productInfo);

    }
}