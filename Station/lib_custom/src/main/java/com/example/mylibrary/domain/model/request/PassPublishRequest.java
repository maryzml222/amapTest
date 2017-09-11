package com.example.mylibrary.domain.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description: 乘客发布行程功能
 */

public class PassPublishRequest {

    @SerializedName("destination")
    public String destination;//目的地

    @SerializedName("origin")
    public String origin;//出发点

    @SerializedName("endStationName")
    public String endStationName;// 终点站台名

    @SerializedName("startStationName")
    public String startStationName;

    @SerializedName("token")
    public String token;

}
