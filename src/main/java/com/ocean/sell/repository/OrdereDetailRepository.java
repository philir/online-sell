package com.ocean.sell.repository;

import com.ocean.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdereDetailRepository extends JpaRepository<OrderDetail,String> {

    List<OrderDetail> findByOrderId(String ordereId);
}
