package com.ocean.sell.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum OrderStatusEnums implements CodeEnum {
    NEW(0,"新订单"),
    FINISHED(1,"订单完成"),
    CANCEL(2,"订单取消"),
    ACCEPTED(3,"已接单"),
    ;

    private Integer code;
    private String message;

    OrderStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
