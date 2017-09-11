package com.example.mylibrary.domain.repository;

import com.example.mylibrary.domain.model.request.SignInRequest;
import com.example.mylibrary.domain.model.response.sign.SignInEntity;

import rx.Observable;

public interface SignInRepository {
    /**
     * 签到
     * @param request
     * @return
     */
    Observable<SignInEntity> posSignIn(SignInRequest request);
}
