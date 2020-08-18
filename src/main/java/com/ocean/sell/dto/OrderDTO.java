package com.ocean.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocean.sell.dataobject.OrderDetail;
import com.ocean.sell.enums.OrderStatusEnums;
import com.ocean.sell.enums.PayStatusEnum;
import com.ocean.sell.serializer.Date2LongSerializer;
import com.ocean.sell.utils.EnumsUtil;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;
    //订单总额
    private BigDecimal orderAmount;
    //订单评论状态
    private Integer commentStatus;
    //订单状态 默认0新下单
    private Integer orderStatus;
    //支付状态  默认为0未支付
    private Integer payStatus;
    //订单商品描述
    private String orderProductDes;
    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    //更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnums getOrderStatusEnum(){
        return EnumsUtil.getByCode(orderStatus,OrderStatusEnums.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumsUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
