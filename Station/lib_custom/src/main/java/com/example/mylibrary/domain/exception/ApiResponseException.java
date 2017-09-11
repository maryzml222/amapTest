package com.example.mylibrary.domain.exception;

import com.example.mylibrary.domain.model.base.BaseResponseEntity;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description:
 */

public class ApiResponseException extends Exception {
    //Todo...需要和后台沟通API返回的成功码, Added by Jack.Li
    /**
     * 访问成功
     */
    public static final String RESP_CODE_SUCCESS = "1";

    public static final String RESP_CODE_SS = "1";

    private final String mResponseCode;

    private final String mResponseMessage;

    public ApiResponseException(BaseResponseEntity baseEntity) {
        super(baseEntity.getMessage());
        //super(baseEntity.getSubMsg());
        mResponseCode = baseEntity.getCode();
        mResponseMessage = baseEntity.getMessage();
    }

    public String getResponseCode() {
        return mResponseCode;
    }

    public String getmResponseMessage() {
        return mResponseMessage;
    }
}
