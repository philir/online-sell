package com.ocean.sell.service.imp;

import com.ocean.sell.dataobject.BuyerComment;
import com.ocean.sell.service.CommentService;
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
public class CommentServiceImplTest {

    private String commentId = KeyUtil.genUniqueKey();
    private String orderId = "1581412561369713270";
    private String openid = "oTgZpwY0gi26ntYJ1N-O5Q7QO9Ls";


    @Autowired
    private CommentService commentService;

    @Test
    public void save() {
        BuyerComment buyerComment = new BuyerComment();
        buyerComment.setCommentId(commentId);
        buyerComment.setOrderId(orderId);
        buyerComment.setBuyerOpenid(openid);
        buyerComment.setContext("好吃");
        buyerComment.setScoreStartNum(4);
        buyerComment.setSeverStartNum(5);
        buyerComment.setCommentImag1("/sell/image/jianai.jpg");
        buyerComment.setCommentImag2("/sell/image/mapodoufu.jpg");
        buyerComment.setCommentImag3("/sell/image/pijiuya.jpg");
        BuyerComment result = commentService.save(buyerComment);
        Assert.assertNotNull(result);
    }




}