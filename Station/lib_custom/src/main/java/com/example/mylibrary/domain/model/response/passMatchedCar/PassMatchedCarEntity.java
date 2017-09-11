package com.example.mylibrary.domain.model.response.passMatchedCar;

import com.example.mylibrary.domain.model.base.BaseResponseEntity;
import com.google.gson.annotations.SerializedName;

public class PassMatchedCarEntity extends BaseResponseEntity {

    @SerializedName("data")
    public PassMatchedCarResultEntity data;

}
