package com.zt.station.feature.map.location;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 定位
 * Created by hnn00437 on 2017/8/2.
 */

public class Location {

    private static AMapLocationClient mLocationClient;
    private static AMapLocationClientOption mLocationOption;

    public static void getMyLocation(Context context, final AMapLocationListener aMapLocationListener) {
        //声明mLocationOption对象
        if(mLocationClient == null){
            mLocationClient = new AMapLocationClient(context);
        }
        //初始化定位参数
        if(mLocationOption == null){
            mLocationOption = new AMapLocationClientOption();

            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位间隔,单位毫秒,默认为2000ms
//            mLocationOption.setInterval(2000);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
        }
        //设置定位监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                aMapLocationListener.onLocationChanged(aMapLocation);
                stopLocation();
            }
        });
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();
    }

    public static void stopLocation(){
        if(mLocationClient != null){
            mLocationClient.stopLocation();
        }
    }
}
