package com.example.mylibrary.domain.repository;

import com.example.mylibrary.domain.model.request.PassPositionRequest;
import com.example.mylibrary.domain.model.response.PassPositionEntity;

import rx.Observable;

public interface PassPositionRepository {
    /**
     * 位置
     * @param request
     * @return
     */
    Observable<PassPositionEntity> passPostion(PassPositionRequest request);
}
