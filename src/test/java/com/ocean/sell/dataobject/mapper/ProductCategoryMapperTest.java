package com.ocean.sell.dataobject.mapper;

import com.ocean.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("categoryName","男生最不爱");
        map.put("category_type",101);
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }

    @Test
    public void insertByObject() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最不爱");
        productCategory.setCategoryType(102);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1,result);
    }

    @Test
    public void findByCategoryType() {
        ProductCategory result = mapper.findByCategoryType(102);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryName() {
        List<ProductCategory> result = mapper.findByCategoryName("男生最不爱");
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void updateByCategoryType(){
        int result = mapper.updateByCategoryType("男生最爱",102);
        Assert.assertEquals(1, result);
    }

    @Test
    public void updateByObject(){
        ProductCategory productCategory = mapper.findByCategoryType(101);
        productCategory.setCategoryName("女生最爱");
        int result = mapper.updateByObject(productCategory);
        Assert.assertEquals(1, result);
    }

    @Test
    public void deleteByCategoryType(){
        int result = mapper.deleteByCategoryType(102);
        Assert.assertEquals(1, result);
    }

    @Test
    public void selectByCategoryType(){
        ProductCategory productCategory = mapper.selectByCategoryType(101);
        Assert.assertNotNull(productCategory);
    }
}