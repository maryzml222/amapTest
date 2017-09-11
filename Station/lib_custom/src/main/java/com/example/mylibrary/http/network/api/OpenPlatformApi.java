package com.example.mylibrary.http.network.api;

import com.example.mylibrary.domain.model.response.DriverPublishTripEntity;
import com.example.mylibrary.domain.model.response.DriverPullInEntity;
import com.example.mylibrary.domain.model.response.DriverSubmitPositionEntity;
import com.example.mylibrary.domain.model.response.passMatchedCar.PassMatchedCarEntity;
import com.example.mylibrary.domain.model.response.PassPayEntity;
import com.example.mylibrary.domain.model.response.PassPositionEntity;
import com.example.mylibrary.domain.model.response.passPublishTrip.PassPublishEntity;
import com.example.mylibrary.domain.model.response.sign.SignInEntity;
import com.example.mylibrary.domain.model.response.VerifyCodeEntity;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description:
 */

public interface OpenPlatformApi {

    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    //@Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/station/app/v1/verify/code/item")
    Observable<VerifyCodeEntity> getVerifyCode(@Body RequestBody request);

    /**
     * 登录
     */
    @POST("station/app/v1/user/login")
    Observable<SignInEntity> posSignIn(@Body RequestBody request);


    /**
     * 乘客发布行程
     */
    @POST("station/app/v1/trip/passenger/publish")
    Observable<PassPublishEntity> passPublish(@Body RequestBody request);

    /**
     * 乘客获取可乘坐的车辆
     */
    @POST("station/app/v1/trip/passenger/matched/car")
    Observable<PassMatchedCarEntity> passMatchedCar(@Body RequestBody request);

    /**
     * 车主上送当前坐标
     */
    @POST("/station/app/v1/trip/car/position")
    Observable<DriverSubmitPositionEntity> driverSubmitPosition(@Body RequestBody request);

    /**
     * 车主行程发布
     */
    @POST("/station/app/v1/trip/car/publish")
    Observable<DriverPublishTripEntity> driverPublishTrip(@Body RequestBody request);

    /**
     * 车主车辆进站
     */
    @POST("/station/app/v1/trip/car/pull/in")
    Observable<DriverPullInEntity> driverPullIn(@Body RequestBody request);

    /**
     * 位置
     */
    @POST("station/app/v1/position/save")
    Observable<PassPositionEntity> passPosition(@Body RequestBody request);


    /**
     * 订单支付
     */
    @POST("station/app/v1/pay/apply")
    Observable<PassPayEntity> passPay(@Body RequestBody request);
}
