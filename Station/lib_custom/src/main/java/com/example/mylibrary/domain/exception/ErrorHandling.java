package com.example.mylibrary.domain.exception;

import android.content.Context;

import com.example.mylibrary.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description:
 */

public class ErrorHandling {

    private static final String TAG = ErrorHandling.class.getSimpleName();

    private ErrorHandling() {
    /*never invoked*/
    }

    public static String propagate(Context context, Throwable throwable) {

        String message;

        if (throwable instanceof TimeoutException
                || throwable instanceof ConnectException
                || throwable instanceof UnknownHostException
                || throwable instanceof SocketTimeoutException) {
            // 网络错误
            message = context.getResources().getString(R.string.exception_network_error);
        } else if (throwable instanceof ApiResponseException) {
            // 接口错误
            message = throwable.getMessage();
        } else {
            // 未知错误
            message = context.getResources().getString(R.string.exception_unknow_error);
        }

        return message;
    }
}
