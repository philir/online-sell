package com.ocean.sell.controller;

import com.ocean.sell.VO.ResultVO;
import com.ocean.sell.converter.OrderForm2OrderDTOConverter;
import com.ocean.sell.dataobject.OrderMaster;
import com.ocean.sell.dto.OrderDTO;
import com.ocean.sell.enums.OrderStatusEnums;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.form.OrderForm;
import com.ocean.sell.repository.OrderMasterRepository;
import com.ocean.sell.service.OrderMasterService;
import com.ocean.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家端
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("[创建订单] 参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[创建订单] 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderMasterService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVOUtil.success(map);
    }
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("[查询订单列表] openid为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = PageRequest.of(page,size,Sort.Direction.DESC,"createTime");
        Page<OrderDTO> orderMasterPage = orderMasterService.findList(openid,request);
        return  ResultVOUtil.success(orderMasterPage.getContent());
    }
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                           @RequestParam("orderId") String orderId){
        if(StringUtils.isEmpty(openid)){
            log.error("[查询订单详情] openid为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if(StringUtils.isEmpty(orderId)){
            log.error("[查询订单详情] orderId为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[查询订单详情] 订单的openid不一致,openid={},orderDTO.openid={}",openid,orderDTO.getBuyerOpenid());
            throw new SellException(ResultEnum.ORDER_USERNAME_ERROR);
        }
        return ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("[取消] openid为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error("[取消] orderId为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[取消] 订单的openid不一致,openid={},orderDTO.openid={}",openid,orderDTO.getBuyerOpenid());
            throw new SellException(ResultEnum.ORDER_USERNAME_ERROR);
        }
        if(!"oTgZpwY0gi26ntYJ1N-O5Q7QO9Ls".equalsIgnoreCase(orderDTO.getBuyerOpenid())){
            OrderMaster orderMaster = orderMasterRepository.getOne(orderId);
            if(orderMaster == null){
                throw new SellException(ResultEnum.ORDER_NOT_EXIST);
            }
            orderMaster.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
            OrderMaster result = orderMasterRepository.save(orderMaster);
            if(result == null){
                throw new SellException(ResultEnum.ORDER_CANCEL_FAIL);
            }
            return ResultVOUtil.success();
        }
        orderMasterService.cancel(orderDTO);
        return ResultVOUtil.success();
    }

    @PostMapping("/delete")
    public ResultVO delete(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("[取消] openid为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error("[取消] orderId为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[取消] 订单的openid不一致,openid={},orderDTO.openid={}",openid,orderDTO.getBuyerOpenid());
            throw new SellException(ResultEnum.ORDER_USERNAME_ERROR);
        }
        orderMasterService.delete(orderDTO);
        return ResultVOUtil.success();
    }
}
