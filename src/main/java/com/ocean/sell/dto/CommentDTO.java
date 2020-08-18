package com.ocean.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocean.sell.serializer.Date2LongSerializer;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CommentDTO {

    private String commentId;

    private String orderId;

    private String buyerOpenid;

    private String context;

    private Integer scoreStartNum;

    private Integer severStartNum;

    private List<String> imgUrls = new ArrayList<>();
    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    private String buyerNickname;

    private String buyerHeadimgurl;

    private Integer buyerVip;
}
