package com.ocean.sell.service;

import com.ocean.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 */
public interface OrderMasterService {
    //创建订单
    OrderDTO create(OrderDTO orderDTO);
    //查询单个订单
    OrderDTO findOne(String orderId);
    //查询个人订单列表
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);
    //查询全部订单列表
    Page<OrderDTO> findList(Integer orderStatus,Pageable pageable);
    //查询全部新订单列表
    Page<OrderDTO> findNewOrderList(Pageable pageable);
    //查询全部预支付订单列表
    Page<OrderDTO> findUnpaidList(Pageable pageable);
    //查询全部取消订单列表
    Page<OrderDTO> findCancelList(Pageable pageable);
    //查询全部已完结订单列表
    Page<OrderDTO> findFinshedList(Pageable pageable);
    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);
    //删除订单
    OrderDTO delete(OrderDTO orderDTO);
    //接收订单
    OrderDTO accept(OrderDTO orderDTO);
    //完结订单
    OrderDTO finish(OrderDTO orderDTO);
    //支付订单
    OrderDTO paid(OrderDTO orderDTO);

}
