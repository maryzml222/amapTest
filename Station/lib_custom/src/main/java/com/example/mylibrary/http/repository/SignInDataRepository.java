package com.example.mylibrary.http.repository;

import com.example.mylibrary.domain.model.request.SignInRequest;
import com.example.mylibrary.domain.model.response.sign.SignInEntity;
import com.example.mylibrary.domain.repository.SignInRepository;
import com.google.gson.Gson;

import rx.Observable;

public class SignInDataRepository extends BaseDataRepository implements SignInRepository {

    @Override
    public Observable<SignInEntity> posSignIn(SignInRequest request) {
        return getOpenPlatformApi().posSignIn(getRequestBody(new Gson().toJson(request)));
    }

}
