package com.example.mylibrary.domain.repository;

import com.example.mylibrary.domain.model.request.PassPublishRequest;
import com.example.mylibrary.domain.model.response.passPublishTrip.PassPublishEntity;

import rx.Observable;

public interface PassPublishRepository {
    /**
     * 乘客发布行程
     * @param request
     * @return
     */
    Observable<PassPublishEntity> passPublish(PassPublishRequest request);
}
