package com.example.lib_db.model;

/**
 * Description
 * 数据库中需要用到的一些标识
 */

public class DBTags {
    //begin====================================路由订单的交易状态==============================
    public static final String ROUTE_TRANS_STATUS_UNPUBLISHED_CODE = "0";//路由订单未发布状态码
    public static final String ROUTE_TRANS_STATUS_PUBLISHED_CODE = "1";//路由订单已发布状态码
    public static final String ROUTE_TRANS_STATUS_CANCELED_CODE = "2";//路由订单取消状态码
    public static final String ROUTE_TRANS_STATUS_PAID_CODE = "3";//路由订单已支付状态码
    public static final String ROUTE_TRANS_STATUS_FINISHED_CODE = "4";//路由订单已完成状态码

    public static final String ROUTE_TRANS_STATUS_UNPUBLISHED_DESC = "未发布";//路由订单未发布描述信息
    public static final String ROUTE_TRANS_STATUS_PUBLISHED_DESC = "已发布";//路由订单已发布描述信息
    public static final String ROUTE_TRANS_STATUS_CANCELED_DESC = "已取消";//路由订单已取消描述信息
    public static final String ROUTE_TRANS_STATUS_PAID_DESC = "已支付";//路由订单已支付描述信息
    public static final String ROUTE_TRANS_STATUS_FINISHED_DESC = "已完成";//路由订单已完成描述信息
    //end======================================路由订单的交易状态================================

    //begin====================================路由订单分类==============================
    public static final String ROUTE_TRANS_CATEGORY_PASSENGER = "P";//乘客发布
    public static final String ROUTE_TRANS_CATEGORY_DRIVER = "D";//司机发布
    //end====================================路由订单分类================================

    //begin====================================路由行程上下班标识==============================
    public static final String ROUTES_LOG_ON_WORK = "on_work";
    public static final String ROUTES_LOG_OFF_WORK = "off_work";
    public static final String ROUTES_LOG_OTHER = "other_routes";
    //begin====================================路由行程上下班标识==============================

    //begin====================================系统价格设置==============================
    public static final double STATION_HITCHHIKE_PASSENGER_DEFAULT_AMOUNT = 4;//默认乘客系统搭车费用为RMB: 4元
    //end====================================系统价格设置================================
}
