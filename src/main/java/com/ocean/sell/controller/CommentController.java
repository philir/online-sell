package com.ocean.sell.controller;

import com.ocean.sell.VO.ResultVO;
import com.ocean.sell.converter.BuyerComment2CommentDTO;
import com.ocean.sell.dataobject.BuyerComment;
import com.ocean.sell.dataobject.BuyerInfo;
import com.ocean.sell.dataobject.OrderMaster;
import com.ocean.sell.dto.CommentDTO;
import com.ocean.sell.enums.CommentStatusEnums;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.repository.OrderMasterRepository;
import com.ocean.sell.service.BuyerService;
import com.ocean.sell.service.CommentService;
import com.ocean.sell.service.OrderMasterService;
import com.ocean.sell.utils.KeyUtil;
import com.ocean.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("/add")
    public ResultVO add(@RequestParam("orderId") String orderId,
                         @RequestParam("openid") String openid,
                         @RequestParam("context") String context,
                         @RequestParam("scoreStartNum") Integer scoreStartNum,
                         @RequestParam("severStartNum") Integer severStartNum,
                         @RequestParam(value = "commentImag1",defaultValue = "") String commentImag1,
                         @RequestParam(value = "commentImag2",defaultValue = "") String commentImag2,
                         @RequestParam(value = "commentImag2",defaultValue = "") String commentImag3){
        if(StringUtils.isEmpty(orderId)){
            log.error("[添加评论] orderId为空 ");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(StringUtils.isEmpty(openid)){
            log.error("[添加评论] openid为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        if(StringUtils.isEmpty(context)) {
            log.error("[添加评论] context为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        BuyerComment buyerComment = new BuyerComment();
        String commentId = KeyUtil.genUniqueKey();
        buyerComment.setCommentId(commentId);
        buyerComment.setOrderId(orderId);
        buyerComment.setBuyerOpenid(openid);
        buyerComment.setContext(context);
        buyerComment.setScoreStartNum(scoreStartNum);
        buyerComment.setSeverStartNum(severStartNum);
        buyerComment.setCommentImag1(commentImag1);
        buyerComment.setCommentImag2(commentImag2);
        buyerComment.setCommentImag3(commentImag3);
        commentService.save(buyerComment);
        OrderMaster orderMaster = orderMasterRepository.getOne(orderId);
        orderMaster.setCommentStatus(CommentStatusEnums.COMMENTED.getCode());
        orderMasterRepository.save(orderMaster);
        return ResultVOUtil.success();
    }

    @GetMapping("/findOne")
    public ResultVO findOne(@RequestParam("orderId") String orderId,
                            @RequestParam("openid") String openid){
        if(StringUtils.isEmpty(orderId)) {
            log.error("[查找评价] orderId为空 ");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        CommentDTO commentDTO = commentService.findOne(orderId);
        return ResultVOUtil.success(commentDTO);
    }

    @GetMapping("/findAll")
    public ResultVO findAll(){
        //PageRequest request = PageRequest.of(0,10000,Sort.Direction.DESC,"createTime");
        List<CommentDTO> commentDTOList = commentService.findList();
        HashSet<String> set = new HashSet<>();
        for(CommentDTO commentDTO : commentDTOList) {
            set.add(commentDTO.getBuyerOpenid());
        }
        List<BuyerInfo> buyerInfoList =  buyerService.findByBuyerOpenidIn(set);
        for(BuyerInfo buyerInfo : buyerInfoList){
            for(int i = 0; i < commentDTOList.size(); i++){
                if((commentDTOList.get(i).getBuyerOpenid().equalsIgnoreCase(buyerInfo.getBuyerOpenid()))){
                    commentDTOList.get(i).setBuyerHeadimgurl(buyerInfo.getBuyerHeadimgurl());
                    commentDTOList.get(i).setBuyerNickname(buyerInfo.getBuyerNickname());
                    commentDTOList.get(i).setBuyerVip(buyerInfo.getBuyerVip());
                }
            }
        }
        return ResultVOUtil.success(commentDTOList);
    }

    @GetMapping("/findList")
    public ResultVO findList(@RequestParam("openid") String openid){
        if(StringUtils.isEmpty(openid)){
            log.error("[添加评论] openid为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = PageRequest.of(0,10000,Sort.Direction.DESC,"createTime");
        List<CommentDTO> commentDTOList = commentService.findList(openid,request);

        return ResultVOUtil.success(commentDTOList);
    }
}
