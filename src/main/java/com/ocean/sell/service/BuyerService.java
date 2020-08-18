package com.ocean.sell.service;

import com.ocean.sell.dataobject.BuyerInfo;

import java.util.List;
import java.util.Set;

public interface BuyerService {

    /**
     * 通过openid查询买家端信息
     * @param openid
     * @return
     */
    /* SellerInfo findSellerInfoByOpenid(String openid);*/

    BuyerInfo insertBuyerInfo(BuyerInfo sellerInfo);

    BuyerInfo findBuyerInfoByOpenid(String openid);
    //通过数组查找
    List<BuyerInfo> findByBuyerOpenidIn(Set<String> set);
}
