package com.ocean.sell.controller;

import com.ocean.sell.VO.ResultVO;
import com.ocean.sell.converter.AddressForm2AddressInfo;
import com.ocean.sell.dataobject.AddressInfo;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.form.AddressForm;
import com.ocean.sell.service.AddressService;
import com.ocean.sell.utils.KeyUtil;
import com.ocean.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/findList")
    public ResultVO findList(@RequestParam("openid") String openid){
        if(StringUtils.isEmpty(openid)){
            log.error("[查找地址] openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        List<AddressInfo> list = addressService.findList(openid);
        return ResultVOUtil.success(list);
    }

    @GetMapping("/findOne")
    public ResultVO findOne(@RequestParam("addressId") String addressId){
        if(StringUtils.isEmpty(addressId)){
            log.error("[查找地址] addressId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        AddressInfo addressInfo = addressService.findOne(addressId);
        return ResultVOUtil.success(addressInfo);
    }

    @PostMapping("/add")
    public ResultVO add(@Valid AddressForm form,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("[地址保存] 参数有错");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        String addressId = KeyUtil.genUniqueKey();
        AddressInfo addressInfo = AddressForm2AddressInfo.convert(form);
        addressInfo.setAddressId(addressId);
        addressService.save(addressInfo);
        return ResultVOUtil.success();
    }

    @PostMapping("/alter")
    public ResultVO index(@Valid AddressForm form,
                        BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("[地址修改] 参数有错");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        AddressInfo addressInfo = AddressForm2AddressInfo.convert(form);
        addressService.save(addressInfo);
        return ResultVOUtil.success();
    }

    @GetMapping("/delete")
    public ResultVO delete(@RequestParam("addressId") String addressId){
        if(StringUtils.isEmpty(addressId)){
            log.error("[删除地址] addressId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        addressService.delete(addressId);
        return ResultVOUtil.success();
    }
}
