package com.ocean.sell.repository;

import com.ocean.sell.dataobject.AddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressInfoRespository extends JpaRepository<AddressInfo,String> {

    List<AddressInfo> findByBuyerOpenid(String openid);

}
