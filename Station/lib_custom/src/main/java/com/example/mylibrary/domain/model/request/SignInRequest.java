package com.example.mylibrary.domain.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description: 登录功能
 */

public class SignInRequest {

    @SerializedName("cellphoneNumber")
    public String cellphoneNumber;

    @SerializedName("code")
    public String code;

}
