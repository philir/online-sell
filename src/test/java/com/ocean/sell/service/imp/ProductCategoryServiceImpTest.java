package com.ocean.sell.service.imp;

import com.ocean.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductCategoryServiceImpTest {

    @Autowired
    private ProductCategoryServiceImp categoryService;

    @Test
    void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(2,10));
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void save() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(7);
        productCategory.setCategoryName("最美奶茶");
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}