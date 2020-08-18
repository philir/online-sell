package com.ocean.sell.repository;

import com.ocean.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdereDetailRepositoryTest {

    @Autowired
    private OrdereDetailRepository repository;

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("159165341654");
        orderDetail.setDetailId("16789713");
        orderDetail.setProductId("16546500");
        orderDetail.setProductIcon("wqdwerfwef");
        orderDetail.setProductName("奶茶");
        orderDetail.setProductPrice(new BigDecimal(10.0));
        orderDetail.setProductQuantity(50);
        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByOrderId() {
        List<OrderDetail> list = repository.findByOrderId("159165341654");
        Assert.assertNotEquals(0,list.size());
    }
}