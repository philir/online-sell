package com.ocean.sell.service;

import com.ocean.sell.dto.OrderDTO;

/**
 * 推送消息
 */
public interface PushMessage {

    /**
     * 订单状态更变消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
