package com.example.mylibrary.domain.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description: 登录功能
 */

public class DriverPullInRequest {

    @SerializedName("carOwnerTripId")
    public int carOwnerTripId;//站台标识 ,

    @SerializedName("stationID")
    public int stationID;//车主行程Id ,

    @SerializedName("token")
    public String token;// 用户标识

}
