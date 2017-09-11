package com.example.lib_db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/1
 * Description: 行程订单表实体类
 */

@Entity
public class TransLog {

    @Id(autoincrement = true)
    private Long id;

    //手机号
    @Property(nameInDb = "phoneNumber")
    private String phoneNumber;

    //行程状态码
    @Property(nameInDb = "routeStatusCode")
    private String routeStatusCode;

    //行程状态描述信息
    @Property(nameInDb = "routeStatusDesc")
    private String routeStatusDesc;

    //路由订单创建时间
    @Property(nameInDb = "createTime")
    private String createTime;

    //路由订单结束时间
    @Property(nameInDb = "endTime")
    private String endTime;

    //路由订单类型: P-乘客发布、D-司机发布
    @Property(nameInDb = "routeTransCategory")
    private String routeTransCategory;

    //路由信息: 包含起始站点和中间经过站台信息的json->String串
    @Property(nameInDb = "routeInfo")
    private String routeInfo;

    //搭载乘客信息
    @Property(nameInDb = "passengerArrayInfo")
    private String passengerArrayInfo;

    //乘客行程trip id: 后台返回
    @Property(nameInDb = "passengerTripId")
    private String passengerTripId;

    //路由订单金额
    @Property(nameInDb = "transAmount")
    private double transAmount;

    //Builder模式构建实体类
    public static class Builder {
        private Long id;
        private String phoneNumber;
        private String routeStatusCode;
        private String routeStatusDesc;
        private String createTime;
        private String endTime;
        private String routeTransCategory;
        private String routeInfo;
        private String passengerArrayInfo;
        private String passengerTripId;
        private double transAmount;

        public Builder id (Long id) {
            this.id = id;
            return this;
        }

        public Builder phoneNumber (String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder routeStatusCode (String routeStatusCode) {
            this.routeStatusCode = routeStatusCode;
            return this;
        }

        public Builder routeStatusDesc (String routeStatusDesc) {
            this.routeStatusDesc = routeStatusDesc;
            return this;
        }

        public Builder createTime (String createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder endTime (String endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder routeTransCategory (String routeTransCategory) {
            this.routeTransCategory = routeTransCategory;
            return this;
        }

        public Builder routeInfo (String routeInfo) {
            this.routeInfo = routeInfo;
            return this;
        }

        public Builder passengerArrayInfo (String passengerArrayInfo) {
            this.passengerArrayInfo = passengerArrayInfo;
            return this;
        }

        public Builder passengerTripId (String passengerTripId) {
            this.passengerTripId = passengerTripId;
            return this;
        }

        public Builder transAmount (double transAmount) {
            this.transAmount = transAmount;
            return this;
        }

        public TransLog build() {
            return new TransLog(this);
        }
    }

    private TransLog(Builder b) {
        this.id = b.id;
        this.phoneNumber = b.phoneNumber;
        this.routeStatusCode = b.routeStatusCode;
        this.routeStatusDesc = b.routeStatusDesc;
        this.createTime = b.createTime;
        this.endTime = b.endTime;
        this.routeTransCategory = b.routeTransCategory;
        this.routeInfo = b.routeInfo;
        this.passengerArrayInfo = b.passengerArrayInfo;
        this.passengerTripId = b.passengerTripId;
        this.transAmount = b.transAmount;
    }

    @Generated(hash = 368064344)
    public TransLog(Long id, String phoneNumber, String routeStatusCode,
            String routeStatusDesc, String createTime, String endTime,
            String routeTransCategory, String routeInfo, String passengerArrayInfo,
            String passengerTripId, double transAmount) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.routeStatusCode = routeStatusCode;
        this.routeStatusDesc = routeStatusDesc;
        this.createTime = createTime;
        this.endTime = endTime;
        this.routeTransCategory = routeTransCategory;
        this.routeInfo = routeInfo;
        this.passengerArrayInfo = passengerArrayInfo;
        this.passengerTripId = passengerTripId;
        this.transAmount = transAmount;
    }

    @Generated(hash = 1489542577)
    public TransLog() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRouteStatusCode() {
        return this.routeStatusCode;
    }

    public void setRouteStatusCode(String routeStatusCode) {
        this.routeStatusCode = routeStatusCode;
    }

    public String getRouteStatusDesc() {
        return this.routeStatusDesc;
    }

    public void setRouteStatusDesc(String routeStatusDesc) {
        this.routeStatusDesc = routeStatusDesc;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRouteTransCategory() {
        return this.routeTransCategory;
    }

    public void setRouteTransCategory(String routeTransCategory) {
        this.routeTransCategory = routeTransCategory;
    }

    public String getRouteInfo() {
        return this.routeInfo;
    }

    public void setRouteInfo(String routeInfo) {
        this.routeInfo = routeInfo;
    }

    public String getPassengerArrayInfo() {
        return this.passengerArrayInfo;
    }

    public void setPassengerArrayInfo(String passengerArrayInfo) {
        this.passengerArrayInfo = passengerArrayInfo;
    }

    public String getPassengerTripId() {
        return this.passengerTripId;
    }

    public void setPassengerTripId(String passengerTripId) {
        this.passengerTripId = passengerTripId;
    }

    public double getTransAmount() {
        return this.transAmount;
    }

    public void setTransAmount(double transAmount) {
        this.transAmount = transAmount;
    }

}
