package com.example.mylibrary.http.repository;

import com.example.mylibrary.domain.model.request.DriverSubmitPositionRequest;
import com.example.mylibrary.domain.model.response.DriverSubmitPositionEntity;
import com.example.mylibrary.domain.repository.DriverSubmitPositionRepository;
import com.google.gson.Gson;

import rx.Observable;

public class DriverSubmitPositionDataRepository extends BaseDataRepository implements DriverSubmitPositionRepository {
    @Override
    public Observable<DriverSubmitPositionEntity> driverSubmitPosition(DriverSubmitPositionRequest request) {
        return getOpenPlatformApi().driverSubmitPosition(getRequestBody(new Gson().toJson(request)));
    }
}
