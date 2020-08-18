package com.ocean.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ocean.sell.enums.OrderStatusEnums;
import com.ocean.sell.enums.ProductStatusEnum;
import com.ocean.sell.utils.EnumsUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 */
@Entity
@DynamicUpdate
@Data
public class ProductInfo {
    //商品Id
    @Id
    private  String productId;
    //商品名称
    private String productName;
    //商品单格
    private BigDecimal productPrice;
    //商品库存
    private Integer productStock;
    //商品描述
    private String productDescription;
    //商品小图
    private String productIcon;
    //商品状态 0正常1下架
    private Integer productStatus;
    //商品类目编号
    private Integer categoryType;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumsUtil.getByCode(productStatus,ProductStatusEnum.class);
    }
}
