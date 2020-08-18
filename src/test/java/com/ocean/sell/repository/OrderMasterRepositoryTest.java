package com.ocean.sell.repository;

import com.ocean.sell.dataobject.OrderMaster;
import com.ocean.sell.enums.OrderStatusEnums;
import com.ocean.sell.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("15984613");
        orderMaster.setBuyerName("蔡先生");
        orderMaster.setBuyerPhone("123465879");
        orderMaster.setBuyerAddress("龙芳");
        orderMaster.setBuyerOpenid("philir");
        orderMaster.setOrderAmount(new BigDecimal(5.4));
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderMaster> page = repository.findByBuyerOpenid("philir",pageRequest);
        System.out.println(page.getTotalElements() + " + " + page.getTotalPages());
        Assert.assertNotEquals(0,page.getTotalElements());
    }

    @Test
    public void findNewOrOrderByOrderStatusAndPayStatus(){
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderMaster> page = repository.findNewOrderByOrderStatusAndPayStatus(OrderStatusEnums.NEW.getCode(),PayStatusEnum.SUCCESS.getCode(),pageRequest);
        System.out.println(page.getTotalElements() + " + " + page.getTotalPages());
        Assert.assertNotEquals(0,page.getTotalElements());
    }
}