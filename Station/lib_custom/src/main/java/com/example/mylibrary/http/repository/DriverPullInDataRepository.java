package com.example.mylibrary.http.repository;

import com.example.mylibrary.domain.model.request.DriverPullInRequest;
import com.example.mylibrary.domain.model.response.DriverPullInEntity;
import com.example.mylibrary.domain.repository.DriverPullInRepository;
import com.google.gson.Gson;

import rx.Observable;

public class DriverPullInDataRepository extends BaseDataRepository implements DriverPullInRepository {
    @Override
    public Observable<DriverPullInEntity> driverPullIn(DriverPullInRequest request) {
        return getOpenPlatformApi().driverPullIn(getRequestBody(new Gson().toJson(request)));
    }
}
