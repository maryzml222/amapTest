package com.example.mylibrary.http.repository;

import com.example.mylibrary.http.network.api.OpenPlatformApi;
import com.example.mylibrary.http.network.api.OpenPlatformApiService;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/7/19 23:50
 * version: v1.0.0
 * description:
 */

public class BaseDataRepository {

    public static final String TAG = BaseDataRepository.class.getSimpleName();
    private static final String MEDIA_TYPE = "application/json";

    /**
     * 获取Retrofit定义的API接口
     *
     * @return
     */
    protected OpenPlatformApi getOpenPlatformApi() {
        return OpenPlatformApiService.getInstance().createApi(OpenPlatformApi.class);
    }


    /**
     * 获取请求参数
     *
     * @param request 接口文档定义的post参数
     * @return
     */
    protected RequestBody getRequestBody(String request) {
        return RequestBody.create(MediaType.parse(MEDIA_TYPE), request);
    }
}
