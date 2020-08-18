package com.ocean.sell.service.imp;

import com.ocean.sell.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceeImpTest {

    @Autowired
    private SellerServiceeImp sellerServiceeImp;

    @Test
    public void findSellerInfoByOpenid() {
        /*SellerInfo sellerInfo = sellerServiceeImp.findSellerInfoByOpenid("philir");
        Assert.assertNotNull(sellerInfo);*/
    }
}