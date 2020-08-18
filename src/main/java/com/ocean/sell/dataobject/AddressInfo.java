package com.ocean.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class AddressInfo {

    @Id
    private String addressId;

    private String buyerOpenid;

    private String buyerName;

    private String buyerPhone;

    private Integer buyerSex;

    private Integer tag;

    private String addressName;

    private String detailAddress;


}
