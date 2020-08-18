package com.ocean.sell.service.imp;

import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.ocean.sell.dto.OrderDTO;
import com.ocean.sell.service.OrderMasterService;
import com.ocean.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;
    @Autowired
    private OrderMasterService orderMasterService;
    @Test
   public void create() {
        OrderDTO orderDTO = orderMasterService.findOne("1578109465786722027");
        payService.create(orderDTO);
    }

    @Test
    public void refund(){
        OrderDTO orderDTO = orderMasterService.findOne("1578630843738400773");
        payService.refund(orderDTO);
    }
}