package com.ocean.sell.service.imp;

import com.ocean.sell.dataobject.BuyerInfo;
import com.ocean.sell.repository.BuyerInfoRepository;
import com.ocean.sell.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private BuyerInfoRepository buyerInfoRepository;

    @Override
    @Transactional
    public BuyerInfo insertBuyerInfo(BuyerInfo buyerInfo) {
        return buyerInfoRepository.save(buyerInfo);
    }

    @Override
    public BuyerInfo findBuyerInfoByOpenid(String openid) {
        return buyerInfoRepository.findByBuyerOpenid(openid);
    }

    @Override
    public List<BuyerInfo> findByBuyerOpenidIn(Set<String> set) {
        return buyerInfoRepository.findByBuyerOpenidIn(set);
    }
}
