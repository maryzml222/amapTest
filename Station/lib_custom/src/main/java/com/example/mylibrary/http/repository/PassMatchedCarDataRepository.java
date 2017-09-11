package com.example.mylibrary.http.repository;

import com.example.mylibrary.domain.model.request.PassMatchedCarRequest;
import com.example.mylibrary.domain.model.response.passMatchedCar.PassMatchedCarEntity;
import com.example.mylibrary.domain.repository.PassMatchedCarRepository;
import com.google.gson.Gson;

import rx.Observable;

public class PassMatchedCarDataRepository extends BaseDataRepository implements PassMatchedCarRepository {

    @Override
    public Observable<PassMatchedCarEntity> passMatchedCar(PassMatchedCarRequest request) {
        return getOpenPlatformApi().passMatchedCar(getRequestBody(new Gson().toJson(request)));
    }

}
