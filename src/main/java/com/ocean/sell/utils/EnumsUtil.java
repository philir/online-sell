package com.ocean.sell.utils;

import com.ocean.sell.enums.CodeEnum;

public class EnumsUtil {

    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for(T each : enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
