package com.ocean.sell.controller;

import com.ocean.sell.VO.ResultVO;
import com.ocean.sell.dataobject.BuyerInfo;
import com.ocean.sell.service.BuyerService;
import com.ocean.sell.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer")
public class BuyerUserController {

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/buyerInfo")
    public ResultVO buyerInfo(@RequestParam("openid") String openid){
        BuyerInfo buyerInfo = buyerService.findBuyerInfoByOpenid(openid);
        return ResultVOUtil.success(buyerInfo);
    }
}
