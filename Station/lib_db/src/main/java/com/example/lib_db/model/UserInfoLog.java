package com.example.lib_db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ${Mary} on 2017/8/16.
 */
@Entity
public class UserInfoLog {
    @Id(autoincrement = true)
    private Long id;

    //手机号
    @Property(nameInDb = "phoneNumber")
    private String phoneNumber;

    //用户标识
    @Property(nameInDb = "token")
    private String token;

    //是否登录
    @Property(nameInDb = "isLogin")
    private boolean isLogin;

    //是否为车主
    @Property(nameInDb = "isDriver")
    private boolean isDriver;

    //昵称
    @Property(nameInDb = "nickname")
    private String nickname;

    //性别
    @Property(nameInDb = "sex")
    private String sex;

    //出生日期
    @Property(nameInDb = "birth")
    private String birth;

    //就职单位
    @Property(nameInDb = "inaugurationUnit")
    private String inaugurationUnit;

    //所在行业
    @Property(nameInDb = "industry")
    private String industry;

    //职位
    @Property(nameInDb = "position")
    private String position;

    //个性签名
    @Property(nameInDb = "signature")
    private String signature;

    //真实姓名
    @Property(nameInDb = "realName")
    private String realName;

    //驾驶证编号
    @Property(nameInDb = "driverLicenseNumber")
    private String driverLicenseNumber;

    //车牌号码
    @Property(nameInDb = "licensePlateNumber")
    private String licensePlateNumber;

    //车辆所有人
    @Property(nameInDb = "vehicleOwner")
    private String vehicleOwner;

    //车辆品牌
    @Property(nameInDb = "vehicleBrand")
    private String vehicleBrand;

    //行驶证注册日期
    @Property(nameInDb = "registerDate")
    private String registerDate;

    //头像URL
    @Property(nameInDb = "headUrl")
    private String headUrl;

    //行驶证URL
    @Property(nameInDb = "drivingLicenseUrl")
    private String drivingLicenseUrl;

    //驾驶证URL
    @Property(nameInDb = "driverLicense")
    private String driverLicense;

    public UserInfoLog(Long id, String phoneNumber, boolean isLogin,
            boolean isDriver, String nickname, String sex, String birth,
            String inaugurationUnit, String industry, String position,
            String signature, String realName, String driverLicenseNumber,
            String licensePlateNumber, String vehicleOwner, String vehicleBrand,
            String registerDate, String headUrl, String drivingLicenseUrl,
            String driverLicense) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.isLogin = isLogin;
        this.isDriver = isDriver;
        this.nickname = nickname;
        this.sex = sex;
        this.birth = birth;
        this.inaugurationUnit = inaugurationUnit;
        this.industry = industry;
        this.position = position;
        this.signature = signature;
        this.realName = realName;
        this.driverLicenseNumber = driverLicenseNumber;
        this.licensePlateNumber = licensePlateNumber;
        this.vehicleOwner = vehicleOwner;
        this.vehicleBrand = vehicleBrand;
        this.registerDate = registerDate;
        this.headUrl = headUrl;
        this.drivingLicenseUrl = drivingLicenseUrl;
        this.driverLicense = driverLicense;
    }

    public UserInfoLog() {
    }

    @Generated(hash = 135051718)
    public UserInfoLog(Long id, String phoneNumber, String token, boolean isLogin,
            boolean isDriver, String nickname, String sex, String birth,
            String inaugurationUnit, String industry, String position,
            String signature, String realName, String driverLicenseNumber,
            String licensePlateNumber, String vehicleOwner, String vehicleBrand,
            String registerDate, String headUrl, String drivingLicenseUrl,
            String driverLicense) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.token = token;
        this.isLogin = isLogin;
        this.isDriver = isDriver;
        this.nickname = nickname;
        this.sex = sex;
        this.birth = birth;
        this.inaugurationUnit = inaugurationUnit;
        this.industry = industry;
        this.position = position;
        this.signature = signature;
        this.realName = realName;
        this.driverLicenseNumber = driverLicenseNumber;
        this.licensePlateNumber = licensePlateNumber;
        this.vehicleOwner = vehicleOwner;
        this.vehicleBrand = vehicleBrand;
        this.registerDate = registerDate;
        this.headUrl = headUrl;
        this.drivingLicenseUrl = drivingLicenseUrl;
        this.driverLicense = driverLicense;
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

    public boolean getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean getIsDriver() {
        return this.isDriver;
    }

    public void setIsDriver(boolean isDriver) {
        this.isDriver = isDriver;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return this.birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getInaugurationUnit() {
        return this.inaugurationUnit;
    }

    public void setInaugurationUnit(String inaugurationUnit) {
        this.inaugurationUnit = inaugurationUnit;
    }

    public String getIndustry() {
        return this.industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDriverLicenseNumber() {
        return this.driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public String getLicensePlateNumber() {
        return this.licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getVehicleOwner() {
        return this.vehicleOwner;
    }

    public void setVehicleOwner(String vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
    }

    public String getVehicleBrand() {
        return this.vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getRegisterDate() {
        return this.registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getHeadUrl() {
        return this.headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getDrivingLicenseUrl() {
        return this.drivingLicenseUrl;
    }

    public void setDrivingLicenseUrl(String drivingLicenseUrl) {
        this.drivingLicenseUrl = drivingLicenseUrl;
    }

    public String getDriverLicense() {
        return this.driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
