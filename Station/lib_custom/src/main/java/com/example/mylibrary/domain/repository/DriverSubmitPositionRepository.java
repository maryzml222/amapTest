package com.example.mylibrary.domain.repository;

import com.example.mylibrary.domain.model.request.DriverSubmitPositionRequest;
import com.example.mylibrary.domain.model.response.DriverSubmitPositionEntity;

import rx.Observable;

public interface DriverSubmitPositionRepository {
    /**
     * 车主上送当前坐标
     * @param request
     * @return
     */
    Observable<DriverSubmitPositionEntity> driverSubmitPosition(DriverSubmitPositionRequest request);
}
