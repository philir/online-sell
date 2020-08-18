package com.ocean.sell.converter;

import com.ocean.sell.dataobject.AddressInfo;
import com.ocean.sell.form.AddressForm;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

public class AddressForm2AddressInfo {

    public static AddressInfo convert(AddressForm addressForm){
        AddressInfo addressInfo = new AddressInfo();
        BeanUtils.copyProperties(addressForm,addressInfo);
        return addressInfo;
    }
}
