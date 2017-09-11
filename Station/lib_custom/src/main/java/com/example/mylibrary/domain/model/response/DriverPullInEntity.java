package com.example.mylibrary.domain.model.response;

import com.example.mylibrary.domain.model.base.BaseResponseEntity;
import com.google.gson.annotations.SerializedName;

public class DriverPullInEntity extends BaseResponseEntity {

    @SerializedName("data")
    public String data;

}
