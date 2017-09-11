package com.example.mylibrary.http.repository;

import com.example.mylibrary.domain.model.request.VerifyCodeRequest;
import com.example.mylibrary.domain.model.response.VerifyCodeEntity;
import com.example.mylibrary.domain.repository.VerifyCodeRepository;
import com.google.gson.Gson;

import rx.Observable;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/3
 * Description:
 */

public class VerifyCodeDataRepository extends BaseDataRepository implements VerifyCodeRepository {

    @Override
    public Observable<VerifyCodeEntity> getVerifyCode(VerifyCodeRequest request) {
        return getOpenPlatformApi().getVerifyCode(getRequestBody(new Gson().toJson(request)));
    }
}
