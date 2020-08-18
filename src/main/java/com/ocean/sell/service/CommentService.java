package com.ocean.sell.service;

import com.ocean.sell.dataobject.BuyerComment;
import com.ocean.sell.dto.CommentDTO;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CommentService {

    List<CommentDTO> findList(String buyerOpenid,Pageable pageable);

    List<CommentDTO> findList();

    BuyerComment save(BuyerComment buyerComment);

    CommentDTO findOne(String orderId);
}
