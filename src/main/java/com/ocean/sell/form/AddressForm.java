package com.ocean.sell.form;

import lombok.Data;

@Data
public class AddressForm {

    private String addressId;

    private String buyerOpenid;

    private String buyerName;

    private String buyerPhone;

    private Integer buyerSex;

    private Integer tag;

    private String addressName;

    private String detailAddress;
}
