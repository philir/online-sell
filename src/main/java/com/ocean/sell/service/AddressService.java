package com.ocean.sell.service;

import com.ocean.sell.dataobject.AddressInfo;

import java.util.List;

public interface AddressService {

    List<AddressInfo> findList(String openid);

    AddressInfo findOne(String addressId);

    AddressInfo save(AddressInfo addressInfo);

    void delete(String addressId);
}
