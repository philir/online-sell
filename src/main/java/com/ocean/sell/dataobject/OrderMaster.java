package com.ocean.sell.dataobject;

import com.ocean.sell.enums.CommentStatusEnums;
import com.ocean.sell.enums.OrderStatusEnums;
import com.ocean.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;
    //订单总额
    private BigDecimal orderAmount;
    //订单商品描述
    private String orderProductDes;
    //订单评论状态
    private Integer commentStatus = CommentStatusEnums.NOTCOMMENT.getCode();
    //订单状态 默认0新下单
    private Integer orderStatus = OrderStatusEnums.NEW.getCode();
    //支付状态  默认为0未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
