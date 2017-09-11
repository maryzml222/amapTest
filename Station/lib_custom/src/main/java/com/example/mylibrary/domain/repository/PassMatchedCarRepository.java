package com.example.mylibrary.domain.repository;

import com.example.mylibrary.domain.model.request.PassMatchedCarRequest;
import com.example.mylibrary.domain.model.response.passMatchedCar.PassMatchedCarEntity;

import rx.Observable;

public interface PassMatchedCarRepository {
    /**
     * 乘客匹配车辆
     * @param request
     * @return
     */
    Observable<PassMatchedCarEntity> passMatchedCar(PassMatchedCarRequest request);
}
