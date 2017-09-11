package com.example.mylibrary.domain.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description: 乘客已匹配车辆功能
 */

public class PassMatchedCarRequest {
    //Todo...需要更新请求的参数信息

    @SerializedName("destinationStationId")
    public int destinationStationId;//目的地站台ID

    @SerializedName("originStationId")
    public int originStationId;//出发站台ID

    @SerializedName("passengerTripId")
    public int passengerTripId;//乘客行程ID

    @SerializedName("token")
    public String token;//用户标识

    @SerializedName("isDemo")
    public boolean isDemo;//演示环境下匹配车辆信息
}
