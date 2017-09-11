package com.example.lib_db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/8 22:29
 * version: v1.0.0
 * description: 行程表实体类
 */

@Entity
public class RoutesLog {

    @Id(autoincrement = true)
    private Long id;

    //手机号
    @Property(nameInDb = "phoneNumber")
    private String phoneNumber;

    //路由行程角色类型: P-乘客发布、D-司机发布
    @Property(nameInDb = "routesLogRoleCategory")
    private String routesLogRoleCategory;

    //上下班行程标识, 其他行程标识
    @Property(nameInDb = "routesLogDirectionCategory")
    private String routesLogDirectionCategory;

    //收藏标识
    @Property(nameInDb = "isFavorites")
    private boolean isFavorites;

    //预计行程时间
    @Property(nameInDb = "routesEstimatedTime")
    private String routesEstimatedTime;

    //起始站台信息: 起始站台的bean --> json --> string存储
    @Property(nameInDb = "startStationInformation")
    private String startStationInformation;

    //终点站台信息: 终点站台的bean --> json --> string存储
    @Property(nameInDb = "endStationInformation")
    private String endStationInformation;

    public static class Builder {
        private Long id;
        private String phoneNumber;
        private String routesLogRoleCategory;
        private String routesLogDirectionCategory;
        private boolean isFavorites;
        private String routesEstimatedTime;
        private String startStationInformation;
        private String endStationInformation;

        public Builder id (Long id) {
            this.id = id;
            return this;
        }

        public Builder phoneNumber (String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder routesLogRoleCategory (String routesLogRoleCategory) {
            this.routesLogRoleCategory = routesLogRoleCategory;
            return this;
        }

        public Builder routesLogDirectionCategory (String routesLogDirectionCategory) {
            this.routesLogDirectionCategory = routesLogDirectionCategory;
            return this;
        }

        public Builder isFavorites (boolean isFavorites) {
            this.isFavorites = isFavorites;
            return this;
        }

        public Builder routesEstimatedTime (String routesEstimatedTime) {
            this.routesEstimatedTime = routesEstimatedTime;
            return this;
        }

        public Builder startStationInformation (String startStationInformation) {
            this.startStationInformation = startStationInformation;
            return this;
        }

        public Builder endStationInformation (String endStationInformation) {
            this.endStationInformation = endStationInformation;
            return this;
        }

        public RoutesLog build() {
            return new RoutesLog(this);
        }
    }

    private RoutesLog(Builder b) {
        this.id = b.id;
        this.phoneNumber = b.phoneNumber;
        this.routesLogRoleCategory = b.routesLogRoleCategory;
        this.routesLogDirectionCategory = b.routesLogDirectionCategory;
        this.isFavorites = b.isFavorites;
        this.routesEstimatedTime = b.routesEstimatedTime;
        this.startStationInformation = b.startStationInformation;
        this.endStationInformation = b.endStationInformation;
    }

    @Generated(hash = 1633283071)
    public RoutesLog(Long id, String phoneNumber, String routesLogRoleCategory,
            String routesLogDirectionCategory, boolean isFavorites, String routesEstimatedTime,
            String startStationInformation, String endStationInformation) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.routesLogRoleCategory = routesLogRoleCategory;
        this.routesLogDirectionCategory = routesLogDirectionCategory;
        this.isFavorites = isFavorites;
        this.routesEstimatedTime = routesEstimatedTime;
        this.startStationInformation = startStationInformation;
        this.endStationInformation = endStationInformation;
    }

    @Generated(hash = 1330764247)
    public RoutesLog() {
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

    public String getRoutesLogRoleCategory() {
        return this.routesLogRoleCategory;
    }

    public void setRoutesLogRoleCategory(String routesLogRoleCategory) {
        this.routesLogRoleCategory = routesLogRoleCategory;
    }

    public String getRoutesLogDirectionCategory() {
        return this.routesLogDirectionCategory;
    }

    public void setRoutesLogDirectionCategory(String routesLogDirectionCategory) {
        this.routesLogDirectionCategory = routesLogDirectionCategory;
    }

    public boolean getIsFavorites() {
        return this.isFavorites;
    }

    public void setIsFavorites(boolean isFavorites) {
        this.isFavorites = isFavorites;
    }

    public String getRoutesEstimatedTime() {
        return this.routesEstimatedTime;
    }

    public void setRoutesEstimatedTime(String routesEstimatedTime) {
        this.routesEstimatedTime = routesEstimatedTime;
    }

    public String getStartStationInformation() {
        return this.startStationInformation;
    }

    public void setStartStationInformation(String startStationInformation) {
        this.startStationInformation = startStationInformation;
    }

    public String getEndStationInformation() {
        return this.endStationInformation;
    }

    public void setEndStationInformation(String endStationInformation) {
        this.endStationInformation = endStationInformation;
    }

}
