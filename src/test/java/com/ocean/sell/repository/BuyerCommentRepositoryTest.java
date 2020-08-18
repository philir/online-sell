package com.ocean.sell.repository;

import com.ocean.sell.dataobject.BuyerComment;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BuyerCommentRepositoryTest {

    @Autowired
    BuyerCommentRepository repository;
    @Test
    public void findList() {
        /*String openid = "oJEsx1uG1VvSEcU8xgph7VqIAncc";
        PageRequest request = PageRequest.of(0,10000,Sort.Direction.DESC,"createTime");
        List<BuyerComment> list = repository.findList(openid);
        Assert.assertNotEquals(0,list.size());*/
    }
}