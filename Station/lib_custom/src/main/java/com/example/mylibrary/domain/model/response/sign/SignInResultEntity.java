package com.example.mylibrary.domain.model.response.sign;

import com.google.gson.annotations.SerializedName;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/11
 * Description:
 */

public class SignInResultEntity {

    @SerializedName("token")
    public String token;

    @SerializedName("isCarOwner")
    public boolean isCarOwner;

}
