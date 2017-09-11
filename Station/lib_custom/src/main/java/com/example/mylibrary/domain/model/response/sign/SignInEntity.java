package com.example.mylibrary.domain.model.response.sign;

import com.example.mylibrary.domain.model.base.BaseResponseEntity;
import com.google.gson.annotations.SerializedName;

public class SignInEntity extends BaseResponseEntity {

    @SerializedName("data")
    public SignInResultEntity data;

}
