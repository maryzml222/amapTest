package com.example.mylibrary.http.repository;

import com.example.mylibrary.domain.model.request.DriverPublishTripRequest;
import com.example.mylibrary.domain.model.response.DriverPublishTripEntity;
import com.example.mylibrary.domain.repository.DriverPublishTripRepository;
import com.google.gson.Gson;

import rx.Observable;

public class DriverPublishTripDataRepository extends BaseDataRepository implements DriverPublishTripRepository {
    @Override
    public Observable<DriverPublishTripEntity> driverPublishTrip(DriverPublishTripRequest request) {
        return getOpenPlatformApi().driverPublishTrip(getRequestBody(new Gson().toJson(request)));
    }
}
