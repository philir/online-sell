package com.ocean.sell.service.imp;

import com.ocean.sell.dataobject.AddressInfo;
import com.ocean.sell.repository.AddressInfoRespository;
import com.ocean.sell.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressInfoRespository respository;
    @Override
    public List<AddressInfo> findList(String openid) {
        return respository.findByBuyerOpenid(openid);
    }

    @Override
    public AddressInfo findOne(String addressId) {
        return respository.getOne(addressId);
    }

    @Override
    @Transactional
    public AddressInfo save(AddressInfo addressInfo) {
        return respository.save(addressInfo);
    }

    @Override
    @Transactional
    public void delete(String addressId) {
        respository.deleteById(addressId);
    }


}
