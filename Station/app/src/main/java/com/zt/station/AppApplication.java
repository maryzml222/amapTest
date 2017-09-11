package com.zt.station;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib_db.DBApplication;
import com.example.mylibrary.component.utils.ManifestUtils;
import com.example.mylibrary.component.utils.PreferenceUtils;
import com.example.mylibrary.http.network.okhttp.BasicParamsInterceptor;
import com.example.mylibrary.http.network.okhttp.OkHttpClientHelper;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/7/19
 * Description: AppApplication
 */

public class AppApplication extends DBApplication {

    private static AppApplication instance = null;

    public static final String STATION_HITCHHIKE_FOLDER = "/mnt/sdcard/station/";
    public static final String CACHE_FOLDER = STATION_HITCHHIKE_FOLDER + "imageCache/";

    public static AppApplication getInstance() {
        return instance;
    }

    /**
     * 记录当前用户的位置信息: 经度和纬度
     * 经度: longitude
     * 纬度: latitude
     * @param base
     */
    public double currentLongitude = 0.0;
    public double currentLatitude = 0.0;

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    /**
     * Database发生改变, 控制是否需要更新当前的View
     *
     * @param base
     */
    public boolean isNeedReloadDataFromDB = false;

    @Override
    protected void attachBaseContext(Context base) {
        KLog.init(BuildConfig.DEBUG, "station");
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance = this;

        File cacheFolder = new File(CACHE_FOLDER);
        if (!cacheFolder.exists() && !cacheFolder.isDirectory()) {
            cacheFolder.mkdir();
        }

        // init ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        PreferenceUtils.init(this);

        // init okhttp3
        List<Interceptor> interceptors = new ArrayList<>();

        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addHeaderParam("platform", "android")
                .addHeaderParam("version", ManifestUtils.getVersionName(this))
                .addHeaderParam("sdk_version", String.valueOf(Build.VERSION.SDK_INT))
                .build();
        interceptors.add(basicParamsInterceptor);

        OkHttpClientHelper.getInstance().init(interceptors);
    }
}
