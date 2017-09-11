package com.example.mylibrary.domain.model.base;

import com.google.gson.annotations.SerializedName;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description:
 */

public class BaseResponse<T extends BaseResponseEntity> {

    @SerializedName(value = "response", alternate = {""})
    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
