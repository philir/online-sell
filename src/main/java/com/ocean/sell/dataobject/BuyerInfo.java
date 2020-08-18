package com.ocean.sell.dataobject;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class BuyerInfo {

    @Id
    private String buyerId;
    //默认地址
    private String addressId = "";

    private String buyerOpenid;

    private String buyerNickname;

    private String buyerHeadimgurl;

    private Integer buyerVip;
}
