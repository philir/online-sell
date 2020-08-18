package com.ocean.sell.service.imp;

import com.ocean.sell.dataobject.SellerInfo;
import com.ocean.sell.repository.SellerInfoRepository;
import com.ocean.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceeImp implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    /*@Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }*/

    @Override
    public SellerInfo insertSellerInfo(SellerInfo sellerInfo) {
        return repository.save(sellerInfo);
    }

    @Override
    public SellerInfo findSellerInfoById(String userName) {
        return repository.findById(userName).orElse(null);
    }


}
