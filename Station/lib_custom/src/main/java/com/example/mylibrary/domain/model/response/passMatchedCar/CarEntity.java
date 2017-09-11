package com.example.mylibrary.domain.model.response.passMatchedCar;

import com.google.gson.annotations.SerializedName;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/14
 * Description: 汽车实体
 */

public class CarEntity {

    @SerializedName("user")
    public UserInfoEntity user;//车主信息

    @SerializedName("color")
    public String color;//车辆颜色

    @SerializedName("brand")
    public String brand;//车辆品牌

    @SerializedName("model")
    public String model;//车辆型号

    @SerializedName("licensePlateNumber")
    public String licensePlateNumber;//车辆牌照

    @SerializedName("location")
    public String carCurrentLocation;//车辆当前位置

    @SerializedName("arrivalTime")
    public int arrivalTime;//到达时间

    @SerializedName("distance")
    public double distance;//距离

    @SerializedName("isInComing")
    public boolean isInComing;//是否即将进站


}
