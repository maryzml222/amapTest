package com.example.mylibrary.http.repository;

import com.example.mylibrary.domain.model.request.PassPublishRequest;
import com.example.mylibrary.domain.model.response.passPublishTrip.PassPublishEntity;
import com.example.mylibrary.domain.repository.PassPublishRepository;
import com.google.gson.Gson;

import rx.Observable;

public class PassPublishDataRepository extends BaseDataRepository implements PassPublishRepository {

    @Override
    public Observable<PassPublishEntity> passPublish(PassPublishRequest request) {
        return getOpenPlatformApi().passPublish(getRequestBody(new Gson().toJson(request)));
    }

}
