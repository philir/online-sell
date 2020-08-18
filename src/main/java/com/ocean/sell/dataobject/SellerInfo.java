package com.ocean.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SellerInfo {
    @Id
    private String username;

    private String password;
}
