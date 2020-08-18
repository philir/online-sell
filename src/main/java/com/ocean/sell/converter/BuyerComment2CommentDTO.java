package com.ocean.sell.converter;

import com.ocean.sell.dataobject.BuyerComment;
import com.ocean.sell.dto.CommentDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BuyerComment2CommentDTO {

    public static CommentDTO convert(BuyerComment buyerComment){
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(buyerComment,commentDTO);
        if(!"undefined".equalsIgnoreCase(buyerComment.getCommentImag1()))
            commentDTO.getImgUrls().add(buyerComment.getCommentImag1());
        if(!"undefined".equalsIgnoreCase(buyerComment.getCommentImag2()))
            commentDTO.getImgUrls().add(buyerComment.getCommentImag2());
        if(!"undefined".equalsIgnoreCase(buyerComment.getCommentImag3()))
            commentDTO.getImgUrls().add(buyerComment.getCommentImag3());
        return commentDTO;
    }

    public static List<CommentDTO> convert(List<BuyerComment> buyerCommentList){
        return buyerCommentList.stream()
                .map(e -> convert(e))
                .collect(Collectors.toList());
    }
}
