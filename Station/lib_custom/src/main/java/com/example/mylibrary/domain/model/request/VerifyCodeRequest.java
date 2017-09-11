package com.example.mylibrary.domain.model.request;

import com.example.mylibrary.domain.model.base.BaseRequestEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/3
 * Description: 获取验证码的功能
 */

public class VerifyCodeRequest extends BaseRequestEntity{

    @SerializedName("cellphoneNumber")
    public String cellphoneNumber;

    @SerializedName("type")
    public int type;

}
