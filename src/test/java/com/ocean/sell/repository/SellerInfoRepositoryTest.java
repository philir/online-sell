package com.ocean.sell.repository;

import com.ocean.sell.dataobject.SellerInfo;
import com.ocean.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void save(){
        SellerInfo sellereInfo = new SellerInfo();
        sellereInfo.setUsername("philir");
        sellereInfo.setPassword("123456");
        SellerInfo sellerInfo = sellerInfoRepository.save(sellereInfo);
        Assert.assertNotNull(sellereInfo);
    }

    @Test
    public void findByOpenid() {
    }
}