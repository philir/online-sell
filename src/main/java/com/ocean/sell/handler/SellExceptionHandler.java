package com.ocean.sell.handler;

import com.ocean.sell.VO.ResultVO;
import com.ocean.sell.config.ProjectUrlConfig;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.ResponseException;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.exception.SellerAuthorizeException;
import com.ocean.sell.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    //@ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handlerAuthorizeException(){
        Map<String,String> map = new HashMap<>();
        map.put("msg", ResultEnum.USER_NOT_AUTHORIZATION.getMessage());
        map.put("url", "/sell/weixin/login");
        return new ModelAndView("common/tip", map);
       /* return new ModelAndView("redirect:"
                .concat("/sell/seller/login"));*/
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    //@ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = ResponseException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlerResponseException(){

    }
}
