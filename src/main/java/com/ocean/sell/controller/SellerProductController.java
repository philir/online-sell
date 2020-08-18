package com.ocean.sell.controller;

import com.ocean.sell.dataobject.ProductCategory;
import com.ocean.sell.dataobject.ProductInfo;
import com.ocean.sell.enums.ProductStatusEnum;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.form.ProductForm;
import com.ocean.sell.service.ProductCategoryService;
import com.ocean.sell.service.ProductInfoService;
import com.ocean.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        PageRequest request = PageRequest.of(page - 1,size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("product/list",map);
    }
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try{
            productInfoService.offSale(productId);
        }catch (SellException e){
            log.error("[卖家端下架商品] 发生异常={}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.PRODUCT_OFFSALE_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try{
            productInfoService.onSale(productId);
        }catch (SellException e){
            log.error("[卖家端上架商品] 发生异常={}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.PRODUCT_ONSALE_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                      Map<String,Object> map){
        if(!StringUtils.isEmpty(productId)){
             ProductInfo productInfo = productInfoService.findOne(productId);
             map.put("productInfo",productInfo);
        }

        //查询所有类目
       List<ProductCategory> productCategoryList =  productCategoryService.findAll();
        map.put("productCategoryList",productCategoryList);
        return new ModelAndView("product/index",map);
    }

    /**
     *添加和修改
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        try{
            ProductInfo productInfo = productInfoService.findOne(form.getProductId());
            //如果为空  证明是新增
            if(productInfo == null){
                productInfo = new ProductInfo();
                form.setProductId(KeyUtil.genUniqueKey());
                productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
            }
            BeanUtils.copyProperties(form,productInfo);
            productInfoService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.SUCCESS);
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
