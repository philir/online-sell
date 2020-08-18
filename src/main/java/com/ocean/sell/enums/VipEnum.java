package com.ocean.sell.enums;

import lombok.Getter;

@Getter
public enum VipEnum {

    VIP(1,"VIP"),
    NOTVIP(0,"不是VIP"),
    ;
    private Integer code;
    private String message;

    VipEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
