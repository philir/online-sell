package com.ocean.sell.service;

import com.ocean.sell.dataobject.SellerInfo;

import java.util.Optional;

public interface SellerService {

    /**
     * 通过openid查询卖家端信息
     * @param openid
     * @return
     */
   /* SellerInfo findSellerInfoByOpenid(String openid);*/

    SellerInfo insertSellerInfo(SellerInfo sellerInfo);

    SellerInfo findSellerInfoById(String userName);

}
