package com.ocean.sell.service.imp;

import com.ocean.sell.converter.BuyerComment2CommentDTO;
import com.ocean.sell.dataobject.BuyerComment;
import com.ocean.sell.dto.CommentDTO;
import com.ocean.sell.repository.BuyerCommentRepository;
import com.ocean.sell.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private BuyerCommentRepository repository;

    @Override
    public List<CommentDTO> findList(String buyerOpenid,Pageable pageable) {
        List<BuyerComment> buyerComments = repository.findByBuyerOpenid(buyerOpenid,pageable);
        List<CommentDTO> commentDTOS = BuyerComment2CommentDTO.convert(buyerComments);
        return commentDTOS;
    }

    @Override
    public List<CommentDTO> findList() {
        List<BuyerComment> buyerComments = repository.findAll();
        List<CommentDTO> commentDTOS = BuyerComment2CommentDTO.convert(buyerComments);
        return commentDTOS;
    }

    @Override
    public BuyerComment save(BuyerComment buyerComment) {

        return repository.save(buyerComment);
    }

    @Override
    public CommentDTO findOne(String orderId) {
        BuyerComment buyerComment = repository.findByOrderId(orderId);
        CommentDTO commentDTO = BuyerComment2CommentDTO.convert(buyerComment);
        return commentDTO;
    }
}
