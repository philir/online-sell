package com.ocean.sell.repository;

import com.ocean.sell.dataobject.BuyerComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyerCommentRepository extends JpaRepository<BuyerComment,String> {

    List<BuyerComment> findByBuyerOpenid(String openid,Pageable pageable);

    BuyerComment findByOrderId(String orderId);

    List<BuyerComment> findAll();
}
