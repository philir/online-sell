package com.ocean.sell.repository;

import com.ocean.sell.dataobject.BuyerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuyerInfoRepositoryTest {

    @Autowired
    private BuyerInfoRepository repository;

    @Test
    public void findBuyerInfosByBuyerOpenid() {
        HashSet<String> set = new HashSet<>();
        set.add("oTgZpwY0gi26ntYJ1N-O5Q7QO9Ls");
        set.add("oJEsx1uG1VvSEcU8xgph7VqIAncc");
        List<BuyerInfo> buyerInfoList = repository.findByBuyerOpenidIn(set);
        Assert.assertNotEquals(0,buyerInfoList.size());
    }
}