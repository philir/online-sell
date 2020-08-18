package com.ocean.sell.repository;

import com.ocean.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }

    @Test
    //@Transactional
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("绿茶");
        productCategory.setCategoryType(4);
        repository.save(productCategory);
        /*ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);*/
    }

    @Test
    //@Transactional
    public void updateTest(){
        ProductCategory productCategory = repository.findById(6).orElse(null);
        productCategory.setCategoryName("黄茶");
        productCategory.setCategoryType(10);
        repository.save(productCategory);
        /*ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);*/
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,10);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());

    }
}