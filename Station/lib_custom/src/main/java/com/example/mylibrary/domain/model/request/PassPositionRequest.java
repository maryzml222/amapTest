package com.example.mylibrary.domain.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description: 获取位置功能
 */

public class PassPositionRequest {
    //Todo...需要更新请求的参数信息

    @SerializedName("latitude")
    public String latitude;//纬度

    @SerializedName("longitude")
    public String longitude;//经度

    @SerializedName("token")
    public String token;//用户标识

}
