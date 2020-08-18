package com.ocean.sell.controller;

import com.ocean.sell.dto.OrderDTO;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     *
     * @param page  第几页，从第一页开始
     * @param size  一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             @RequestParam(value = "number",defaultValue = "0") Integer number,
                             Map<String,Object> map) {
        PageRequest request = PageRequest.of(page - 1, size,Sort.Direction.DESC,"createTime");
        String title = "";
        Page<OrderDTO> orderDTOPage = null;
        orderDTOPage = orderMasterService.findList(number,request);
        if (number == 0) {
            title = "新订单";
        } else if (number == 1) {
            title = "已完结订单";
        } else if (number == 2) {
            title = "取消订单";
        }else if(number == 3){
            title = "已接单";
        }else{
            title = "所有订单";
        }
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("title", title);
        map.put("number", number);
        return new ModelAndView("order/list", map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               @RequestParam(value = "number",defaultValue = "0") Integer number,
                               Map<String,String> map) {
        try {
            OrderDTO orderDTO = orderMasterService.findOne(orderId);
            orderMasterService.cancel(orderDTO);
        } catch (SellException e){
            log.error("[卖家端取消订单] 发生异常={}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list?number=" + number);
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               @RequestParam(value = "number",defaultValue = "0") Integer number,
                               Map<String,Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderMasterService.findOne(orderId);
        } catch (SellException e){
            log.error("[卖家端查询订单详情] 发生异常={}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list?number=" + number);
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDTO);
        map.put("number",number);
        return new ModelAndView("order/detail",map);
    }

    @GetMapping("/finsh")
    public ModelAndView finsh(@RequestParam("orderId") String orderId,
                              @RequestParam(value = "number",defaultValue = "0") Integer number,
                               Map<String,Object> map) {
        try {
            OrderDTO orderDTO = orderMasterService.findOne(orderId);
            orderMasterService.finish(orderDTO);
        } catch (SellException e){
            log.error("[卖家端完结订单] 发生异常={}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list?number=" + number);
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_FINSH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list?number=" + number);
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/accept")
    public ModelAndView accepted(@RequestParam("orderId") String orderId,
                                 @RequestParam(value = "number",defaultValue = "0") Integer number,
                                 Map<String,Object> map){
        try {
            OrderDTO orderDTO = orderMasterService.findOne(orderId);
            orderMasterService.accept(orderDTO);
        } catch (SellException e){
            log.error("[卖家端接收订单] 发生异常={}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list?number=" + number);
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_ACCEPTED_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list?number=" + number);
        return new ModelAndView("common/success",map);
    }

}
