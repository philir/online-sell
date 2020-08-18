package com.ocean.sell.service.imp;

import com.ocean.sell.dataobject.OrderDetail;
import com.ocean.sell.dto.OrderDTO;
import com.ocean.sell.enums.OrderStatusEnums;
import com.ocean.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImpTest {

    @Autowired
    private OrderMasterServiceImp orderMasterService;

    private final String BUYER_OPENID = "oTgZpwY0gi26ntYJ1N-O5Q7QO9Ls";

    private final String ORDER_ID = "1581408094521164162";
    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("蔡先生");
        orderDTO.setBuyerAddress("龙芳");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("12345");
        o1.setProductQuantity(97);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123456");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderMasterService.create(orderDTO);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderMasterService.findOne(ORDER_ID);
        log.info("[查询单个订单]orderDTO={}",orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderMasterService.findList(BUYER_OPENID,pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }


    @Test
    public void cancel() {
        OrderDTO orderDTO = orderMasterService.findOne(ORDER_ID);
        OrderDTO result = orderMasterService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnums.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderMasterService.findOne(ORDER_ID);
        OrderDTO result = orderMasterService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnums.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderMasterService.findOne(ORDER_ID);
        OrderDTO result = orderMasterService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void delete() {
        OrderDTO orderDTO = orderMasterService.findOne(ORDER_ID);
        OrderDTO result = orderMasterService.delete(orderDTO);
        Assert.assertNotNull(result);
    }
}