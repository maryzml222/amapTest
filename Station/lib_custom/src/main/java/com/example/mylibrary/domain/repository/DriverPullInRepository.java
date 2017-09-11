package com.example.mylibrary.domain.repository;

import com.example.mylibrary.domain.model.request.DriverPullInRequest;
import com.example.mylibrary.domain.model.response.DriverPullInEntity;

import rx.Observable;

public interface DriverPullInRepository {
    /**
     * 车主车辆进站
     * @param request
     * @return
     */
    Observable<DriverPullInEntity> driverPullIn(DriverPullInRequest request);
}
