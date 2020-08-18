package com.ocean.sell.repository;

import com.ocean.sell.dataobject.BuyerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BuyerInfoRepository extends JpaRepository<BuyerInfo,String> {

    BuyerInfo findByBuyerOpenid(String openid);

    List<BuyerInfo> findByBuyerOpenidIn(Set<String> set);

}
