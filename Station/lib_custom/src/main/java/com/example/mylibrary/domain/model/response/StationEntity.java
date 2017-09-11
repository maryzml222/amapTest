package com.example.mylibrary.domain.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/7
 * Description:
 */

public class StationEntity implements Serializable {

    @SerializedName("busStationId")
    public String busStationId;

    @SerializedName("busStationName")
    public String busStationName;

    //公交站台路由标识: 0、1、2、3、4、5、6、7、8、9...
    //起始站台的标识值暂定为: 0
    //终点站台的标识值暂定为: 99999
    @SerializedName("busStationRouteIndex")
    public int busStationRouteIndex;

    //站台是否是起始站台
    @SerializedName("busStartStation")
    public boolean busStartStation;

    //站台是否是终点站台
    @SerializedName("busEndStation")
    public boolean busEndStation;

    //站台经度坐标
    @SerializedName("busStationLongitude")
    public double busStationLongitude;

    //站台纬度坐标
    @SerializedName("busStationLatitude")
    public double busStationLatitude;

    //城市编码
    @SerializedName("cityCode")
    public String cityCode;

    //距离目标点的距离
    @SerializedName("distance")
    public int distance;

    //地区编码
    @SerializedName("adCode")
    public String adCode;

    //公交线路信息
    @SerializedName("busLineItems")
    public List<String> busLineItems;
}
