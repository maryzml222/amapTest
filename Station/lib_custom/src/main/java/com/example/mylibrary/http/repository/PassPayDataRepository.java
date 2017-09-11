package com.example.mylibrary.http.repository;

import com.example.mylibrary.domain.model.request.PassPayRequest;
import com.example.mylibrary.domain.model.response.PassPayEntity;
import com.example.mylibrary.domain.repository.PassPayRepository;
import com.google.gson.Gson;

import rx.Observable;

public class PassPayDataRepository extends BaseDataRepository implements PassPayRepository {

    @Override
    public Observable<PassPayEntity> passPay(PassPayRequest request) {
        return getOpenPlatformApi().passPay(getRequestBody(new Gson().toJson(request)));
    }

}
