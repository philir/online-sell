package com.ocean.sell.service.imp;

import com.ocean.sell.dataobject.AddressInfo;
import com.ocean.sell.service.AddressService;
import com.ocean.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceImplTest {

    private final String ORDERID=KeyUtil.genUniqueKey();
    private final String OPENID = "oTgZpwY0gi26ntYJ1N-O5Q7QO9Ls";

    @Autowired
    private AddressService addressService;

    @Test
    public void findList() {
        List<AddressInfo> list = addressService.findList(OPENID);
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findOne() {
        AddressInfo addressInfo = addressService.findOne("1582100927831771604");
        Assert.assertNotNull(addressInfo);
    }

    @Test
    public void save() {
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setAddressId(ORDERID);
        addressInfo.setBuyerOpenid(OPENID);
        addressInfo.setBuyerName("蔡子祥");
        addressInfo.setBuyerPhone("15766278424");
        addressInfo.setBuyerSex(0);
        addressInfo.setTag(2);
        addressInfo.setAddressName("广东海洋大学");
        addressInfo.setDetailAddress("科技楼419");
        AddressInfo result = addressService.save(addressInfo);
        Assert.assertNotNull(result);
    }
}