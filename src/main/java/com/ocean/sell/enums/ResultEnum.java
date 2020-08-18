package com.ocean.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),

    PARAM_ERROR(1,"参数错误"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),

    PRODUCT_STOCK_ERROR(11,"库存不正确"),

    ORDER_NOT_EXIST(12,"订单不存在"),

    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),

    ORDER_STATUS_ERROR(14,"订单状态错误"),

    ORDER_UPDATE_FAIL(15,"订单更新失败"),

    ORDER_DETAIL_EMPTY(16,"订单商品详情为空"),

    ORDER_PAY_STATUS_ERROR(17,"订单支付状态错误"),

    CART_EMPTY(18,"购物车为空"),

    ORDER_USERNAME_ERROR(19,"订单用户名不一致"),

    WXCHAT_MP_ERROR(20,"微信公众账号方面错误"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21,"微信支付异步通知金额校验不通过"),

    ORDER_CANCEL_SUCCESS(22,"订单取消成功"),

    ORDER_FINSH_SUCCESS(23,"订单完结成功"),

    PRODUCT_STATUS_ERROR(24,"商品状态不正确"),

    PRODUCT_OFFSALE_SUCCESS(25,"商品下架成功"),

    PRODUCT_ONSALE_SUCCESS(26,"商品上架成功"),

    WXCHAT_OPEN_ERROR(27,"微信登陆方面错误"),

    LOGIN_SUCCESS(28,"登录成功"),

    LOGIN_FAIL(29,"登录失败"),

    LOGOUT_SUCCESS(30,"注销成功"),

    USER_NOT_EXIST(31,"该用户不存在，请检查userName是否正确"),

    USER_PASSWORD_INCORRECT(32,"密码不正确,请重新登录"),

    USER_NOT_AUTHORIZATION(33,"还未授权，请先登录"),

    USER_SUCCESS_AUTHORIZATION(34,"授权成功，即将为你跳转"),

    USER_SUCCESS_SIGNUP(35,"注册成功"),

    USER_EXIST(36,"该用户已存在"),

    ORDER_PAY_FAIL(37,"订单支付失败"),

    ORDER_CANCEL_FAIL(38,"订单支付失败"),

    BUYERINFO_ADD_FAIL(39,"买家信息添加失败"),

    FILE_IS_NULL(40,"图片不能为空，请添加图片"),

    FILE_SIZE_ERROR(41,"图片大小不能超过10M"),

    FILE_FORMAT_ERROR(42,"图片格式错误,请选择jpg,jpeg,gif,png格式的图片"),

    FILE_UPLOAD_FAIL(43,"图片上传失败"),

    ORDER_ACCEPTED_SUCCESS(44,"订单接收成功"),

    SIGNUP_FAIL(45,"注册失败"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
