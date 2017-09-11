package com.example.mylibrary.http.network.okhttp;


import com.socks.library.KLog;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogger implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        KLog.json(message);
    }
}
