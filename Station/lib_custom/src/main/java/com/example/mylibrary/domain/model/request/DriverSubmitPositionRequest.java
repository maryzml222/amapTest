package com.example.mylibrary.domain.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description: 上送当前坐标
 */

public class DriverSubmitPositionRequest {

    @SerializedName("carOwnerTripId")
    public int carOwnerTripId;//车主行程ID

    @SerializedName("location")
    public String location;//坐标

    @SerializedName("token")
    public String token;//用户标识

}
