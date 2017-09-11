package com.example.mylibrary.domain.model.response.passPublishTrip;

import com.example.mylibrary.domain.model.base.BaseResponseEntity;
import com.google.gson.annotations.SerializedName;

public class PassPublishEntity extends BaseResponseEntity {

    @SerializedName("data")
    public PassPublishResultEntity data;

}
