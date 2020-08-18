package com.ocean.sell.dataobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocean.sell.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class BuyerComment {

    @Id
    private String commentId;

    private String orderId;

    private String buyerOpenid;

    private String context;

    private Integer scoreStartNum;

    private Integer severStartNum;

    private String commentImag1;

    private String commentImag2;

    private String commentImag3;
    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
}
