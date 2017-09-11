package com.example.mylibrary.domain.repository;

import com.example.mylibrary.domain.model.request.DriverPublishTripRequest;
import com.example.mylibrary.domain.model.response.DriverPublishTripEntity;

import rx.Observable;

public interface DriverPublishTripRepository {
    /**
     * 车主发布行程
     * @param request
     * @return
     */
    Observable<DriverPublishTripEntity> driverPublishTrip(DriverPublishTripRequest request);
}
