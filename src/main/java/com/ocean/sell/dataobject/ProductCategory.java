package com.ocean.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目
 */
//@Proxy(lazy = false)
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    //类目id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    //类目名字
    private String categoryName;
    //类目编号
    private Integer categoryType;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
