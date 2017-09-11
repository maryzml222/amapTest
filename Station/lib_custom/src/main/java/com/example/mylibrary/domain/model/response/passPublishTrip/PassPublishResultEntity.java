package com.example.mylibrary.domain.model.response.passPublishTrip;

import com.google.gson.annotations.SerializedName;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/11 0:27
 * version: v1.0.0
 * description:
 */

public class PassPublishResultEntity {

    @SerializedName("passengerTripId")
    public int passengerTripId;

    @SerializedName("originStationId")
    public int originStationId;

    @SerializedName("destinationStationId")
    public int destinationStationId;

    @SerializedName("origin")
    public String origin;

    @SerializedName("destination")
    public String destination;

}
