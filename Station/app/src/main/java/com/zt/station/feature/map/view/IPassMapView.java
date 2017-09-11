package com.zt.station.feature.map.view;

import com.example.mylibrary.domain.model.response.passMatchedCar.CarEntity;
import com.example.mylibrary.domain.model.response.passPublishTrip.PassPublishEntity;
import com.example.mylibrary.uiframwork.base.mvp.view.MvpView;

import java.util.List;

/**
 * Created by ${Mary} on 2017/7/24.
 */

public interface IPassMapView extends MvpView {
    void passGetMatchedCarSuccess(List<CarEntity> list);
    void passGetMatchedCarFail();

    void passPublishTripFial(String errMsg);
    void passPublishTripSuccess(PassPublishEntity passPublishEntity);
}
