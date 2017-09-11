package com.example.mylibrary.http.network.api;

import com.example.mylibrary.BuildConfig;
import com.example.mylibrary.component.serializer.GsonSerializerHelper;
import com.example.mylibrary.http.network.okhttp.OkHttpClientHelper;
import com.socks.library.KLog;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenPlatformApiService {

    private static class OpenPlatformApiServiceHolder {
        private static OpenPlatformApiService instance = new OpenPlatformApiService();
    }

    public static OpenPlatformApiService getInstance() {
        return OpenPlatformApiService.OpenPlatformApiServiceHolder.instance;
    }

    private static final String BASE_URL = BuildConfig.BASE_URL;

    private Retrofit mRetrofit;

    public <T> T createApi(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

    private OpenPlatformApiService() {
        KLog.e(BASE_URL);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonSerializerHelper.getInstance().getGson()))
                .client(OkHttpClientHelper.getInstance().getOkHttpClient());

        mRetrofit = builder.build();
    }
}
