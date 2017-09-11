package com.example.mylibrary.domain.model.response.passMatchedCar;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/11 0:46
 * version: v1.0.0
 * description:
 */

public class PassMatchedCarResultEntity {

    @SerializedName("carModelList")
    public List<CarEntity> carModelList;

}
