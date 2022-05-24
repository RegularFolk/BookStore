package com.atguigu.constant;

/**
 * 存储常量以解决硬编码问题
 */

public class Constants {
    public static final String USER_SESSION_KEY = "loginUser";  //user存储到Session时的key
    public static final String CART_SESSION_KEY = "cart";       //cart存储到Session时的key
    public static final String ORDER_SEQUENCE = "orderSequence";//设置的订单序号
    public static final Integer UNFILLED_ORDER = 0;                   //订单未发货状态码
    public static final Integer FILLED_ORDER = 1;                     //订单已发货状态码
    public static final Integer SIGNED_ORDER = 2;                     //订单已签收状态码
    public static final String USERNAME = "username";            //request中携带的username
    public static final String EMAIL = "email";                  //request中携带的email
}
