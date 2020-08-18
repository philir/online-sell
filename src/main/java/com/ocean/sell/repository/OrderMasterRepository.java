package com.ocean.sell.repository;

import com.ocean.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid,Pageable pageable);
    //新订单
    Page<OrderMaster> findNewOrderByOrderStatusAndPayStatus(Integer orderStatus,Integer payStatus,Pageable pageable);

    Page<OrderMaster> findOrderListByOrderStatus(Integer orderStatus,Pageable pageable);
}
