package com.example.mylibrary.domain.model.base;

import com.example.mylibrary.domain.DomainConstants;
import com.example.mylibrary.domain.exception.ApiResponseException;
import com.google.gson.annotations.SerializedName;

import rx.Observable;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description:
 */

public class BaseResponseEntity {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseResponseEntity{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public Observable filterWebService() {
        if (DomainConstants.API_RESPONSE_CODE_SUCCESS.equalsIgnoreCase(code)) {
            return Observable.just(this);
        } else {
            return Observable.error(new ApiResponseException(this));
        }
    }
}
