package com.ocean.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.ocean.sell.dataobject.OrderMaster;
import com.ocean.sell.dto.OrderDTO;
import com.ocean.sell.enums.PayStatusEnum;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.repository.OrderMasterRepository;
import com.ocean.sell.service.OrderMasterService;
import com.ocean.sell.service.PayService;
import com.ocean.sell.service.imp.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private WebSocket webSocket;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                       @RequestParam("returnUrl") String returnUrl,
                       Map<String,Object> map){
        OrderMaster orderMaster = orderMasterRepository.getOne(orderId);
        //特殊处理
        if(!"oTgZpwY0gi26ntYJ1N-O5Q7QO9Ls".equalsIgnoreCase(orderMaster.getBuyerOpenid())){
            orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
            OrderMaster result = orderMasterRepository.save(orderMaster);
            if(result == null){
                throw new SellException(ResultEnum.ORDER_PAY_FAIL);
            }
            webSocket.sendMessage(orderMaster.getOrderId());
            map.put("returnUrl", returnUrl.startsWith("http://") ? returnUrl : URLDecoder.decode(returnUrl));
            return new ModelAndView("pay/newcreate",map);
        }
        //1.查询订单
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.发起支付
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl", returnUrl.startsWith("http://") ? returnUrl : URLDecoder.decode(returnUrl));
        return new ModelAndView("pay/create",map);
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){

        payService.notify(notifyData);

        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
