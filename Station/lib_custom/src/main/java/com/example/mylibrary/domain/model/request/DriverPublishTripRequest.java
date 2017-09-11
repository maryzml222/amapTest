package com.example.mylibrary.domain.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description: 车主行程发布
 */

public class DriverPublishTripRequest {

    @SerializedName("destination")
    public String destination;//目的地

    @SerializedName("distance")
    public String distance;//行驶距离,单位：米 ,

    @SerializedName("origin")
    public String origin;//出发点 ,

    @SerializedName("stepSize")
    public int stepSize;// 导航路段数

    @SerializedName("strategy")
    public String strategy;//驾车选择策略,保留字段，无需传值，默认：0 ,

    @SerializedName("token")
    public String token;//用户标识

    @SerializedName("trafficLightSize")
    public String trafficLightSize;//红绿灯数量

}
