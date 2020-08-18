package com.ocean.sell.service.imp;

import com.ocean.sell.dto.OrderDTO;
import com.ocean.sell.service.OrderMasterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageImplTest {

    @Autowired
    private PushMessageImpl pushMessage;

    @Autowired
    private OrderMasterService orderMasterService;

    @Test
    public void orderStatus() {
        OrderDTO orderDTO = orderMasterService.findOne("1578985661232995385");
        pushMessage.orderStatus(orderDTO);

    }
}