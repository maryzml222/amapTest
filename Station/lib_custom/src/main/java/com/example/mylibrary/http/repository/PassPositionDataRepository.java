package com.example.mylibrary.http.repository;

import com.example.mylibrary.domain.model.request.PassPositionRequest;
import com.example.mylibrary.domain.model.response.PassPositionEntity;
import com.example.mylibrary.domain.repository.PassPositionRepository;
import com.google.gson.Gson;

import rx.Observable;

public class PassPositionDataRepository extends BaseDataRepository implements PassPositionRepository {

    @Override
    public Observable<PassPositionEntity> passPostion(PassPositionRequest request) {
        return getOpenPlatformApi().passPosition(getRequestBody(new Gson().toJson(request)));
    }

}
