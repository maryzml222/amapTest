package com.zt.station.feature.map.presenter;

import com.example.lib_db.dbutil.RoutesLogUtils;
import com.example.lib_db.dbutil.TransLogUtils;
import com.example.lib_db.model.DBTags;
import com.example.lib_db.model.RoutesLog;
import com.example.lib_db.model.TransLog;
import com.example.mylibrary.component.serializer.GsonSerializerHelper;
import com.example.mylibrary.component.utils.DateUtils;
import com.example.mylibrary.component.utils.PreferenceUtils;
import com.example.mylibrary.domain.model.request.PassMatchedCarRequest;
import com.example.mylibrary.domain.model.request.PassPublishRequest;
import com.example.mylibrary.domain.model.response.StationEntity;
import com.example.mylibrary.domain.model.response.passMatchedCar.PassMatchedCarEntity;
import com.example.mylibrary.domain.model.response.passPublishTrip.PassPublishEntity;
import com.example.mylibrary.domain.usecase.interactor.passenger.PassMatchedCarUsecase;
import com.example.mylibrary.domain.usecase.interactor.passenger.PassPublishUsecase;
import com.example.mylibrary.http.repository.PassMatchedCarDataRepository;
import com.example.mylibrary.http.repository.PassPublishDataRepository;
import com.example.mylibrary.uiframwork.base.mvp.presenter.MvpBasePresenter;
import com.google.gson.Gson;
import com.socks.library.KLog;
import com.zt.station.AppConstants;
import com.zt.station.feature.map.location.StationInfo;
import com.zt.station.feature.map.view.IPassMapView;
import com.zt.station.util.StationUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${Mary} on 2017/7/24.
 */

public class PassMapPresenter extends MvpBasePresenter<IPassMapView> {
    private PassPublishUsecase mPassPublishUsecase;
    private PassPublishRequest mPassPublishRequest;

    private PassMatchedCarRequest mPassMatchedCarRequest;
    private PassMatchedCarUsecase mPassMatchedCarUsecase;
    Subscription subscribeQuery, subscribeLoop;

    @Override
    public void initialize() {
        mPassPublishUsecase = new PassPublishUsecase(new PassPublishDataRepository());
        mPassPublishRequest = new PassPublishRequest();
        mPassMatchedCarUsecase = new PassMatchedCarUsecase(new PassMatchedCarDataRepository());
        mPassMatchedCarRequest = new PassMatchedCarRequest();
    }

    public StationEntity convertOriginStop(StationInfo mStartStation) {
        StationEntity mStartStationEntity = new StationEntity();
        if (mStartStation != null) {
            mStartStationEntity.busStationName = mStartStation.title;
            mStartStationEntity.busStationRouteIndex = AppConstants.ROUTES_START_STATION_INDEX;
            mStartStationEntity.busStartStation = true;
            mStartStationEntity.busEndStation = false;
            mStartStationEntity.busStationLongitude = mStartStation.lon;
            mStartStationEntity.busStationLatitude = mStartStation.lat;
        }
        return mStartStationEntity;
    }

    public StationEntity convertEndStop(StationInfo mEndStation) {
        StationEntity mEndStationEntity = new StationEntity();
        if (mEndStation != null) {
            mEndStationEntity.busStationName = mEndStation.title;
            mEndStationEntity.busStationRouteIndex = AppConstants.ROUTES_END_STATION_INDEX;
            mEndStationEntity.busStartStation = false;
            mEndStationEntity.busEndStation = true;
            mEndStationEntity.busStationLongitude = mEndStation.lon;
            mEndStationEntity.busStationLatitude = mEndStation.lat;
        }
        return mEndStationEntity;
    }

    public void saveWorkTripToDB(StationEntity mStartStationEntity, StationEntity mEndStationEntity, String mMapRoutesCategory, boolean isOtherRoutesSetting) {
        RoutesLog routesLog = null;
        if (RoutesLogUtils.getInstance().getRoutesLogByRoleAndDirect(DBTags.ROUTE_TRANS_CATEGORY_PASSENGER, mMapRoutesCategory, PreferenceUtils.getString(AppConstants.SIGN_PHONE)) != null && !isOtherRoutesSetting) {
            routesLog = RoutesLogUtils.getInstance().getRoutesLogByRoleAndDirect(DBTags.ROUTE_TRANS_CATEGORY_PASSENGER, mMapRoutesCategory, PreferenceUtils.getString(AppConstants.SIGN_PHONE));
            routesLog.setStartStationInformation(GsonSerializerHelper.getInstance().serialize(mStartStationEntity, StationEntity.class));
            routesLog.setEndStationInformation(GsonSerializerHelper.getInstance().serialize(mEndStationEntity, StationEntity.class));

            RoutesLogUtils.getInstance().updateRoutesLog(routesLog);
        } else {
            routesLog = new RoutesLog.Builder()
                    .phoneNumber(PreferenceUtils.getString(AppConstants.SIGN_PHONE))
                    .routesLogRoleCategory(DBTags.ROUTE_TRANS_CATEGORY_PASSENGER)
                    .routesLogDirectionCategory(mMapRoutesCategory)
                    .isFavorites(true)
                    .startStationInformation(new Gson().toJson(mStartStationEntity))
                    .endStationInformation(new Gson().toJson(mEndStationEntity))
                    .build();

            RoutesLogUtils.getInstance().insertRoutesLog(routesLog);
        }
    }

    //乘客发布行程
    public void passPublishTrip(StationEntity mStartStationEntity, StationEntity mEndStationEntity) {
        if (mStartStationEntity != null && mEndStationEntity != null) {

            mPassPublishRequest.destination = mEndStationEntity.busStationLongitude + "," + mEndStationEntity.busStationLatitude;
            mPassPublishRequest.origin = mStartStationEntity.busStationLongitude + "," + mStartStationEntity.busStationLatitude;
            mPassPublishRequest.startStationName = mStartStationEntity.busStationName;
            mPassPublishRequest.endStationName = mEndStationEntity.busStationName;
            mPassPublishRequest.token = PreferenceUtils.getString(AppConstants.USER_TOKEN);

            mPassPublishUsecase.subscribe(new Observer<PassPublishEntity>() {
                @Override
                public void onCompleted() {
                    KLog.e("passPublishTrip---onCompleted ");
                }

                @Override
                public void onError(Throwable e) {
                    KLog.e("passPublishTrip---onError " + e.toString());
                    getView().passPublishTripFial(e.toString());
                }

                @Override
                public void onNext(PassPublishEntity passPublishEntity) {
                    KLog.e("passPublishTrip---onNext " + passPublishEntity);
                    if (passPublishEntity != null) {
                        getView().passPublishTripSuccess(passPublishEntity);

                        TransLog transLog = TransLogUtils.getInstance().getLastTransLog(); //获取DB中最后一条行程订单记录
                        if (transLog != null && DBTags.ROUTE_TRANS_STATUS_UNPUBLISHED_CODE.equals(transLog.getRouteStatusCode())) {
                            transLog.setPassengerTripId(passPublishEntity.data.passengerTripId + "");
                            transLog.setRouteStatusCode(DBTags.ROUTE_TRANS_STATUS_PUBLISHED_CODE);
                            transLog.setRouteStatusDesc(DBTags.ROUTE_TRANS_STATUS_PUBLISHED_DESC);
                        }
                        TransLogUtils.getInstance().updateTransLog(transLog);

                        requestEveryOne(passPublishEntity.data.destinationStationId, passPublishEntity.data.originStationId, passPublishEntity.data.passengerTripId, true);

                    }

                }
            }, mPassPublishRequest);
        } else {
            getView().passPublishTripFial("起始点不能为空");
        }
    }

    public void insertTrip(StationEntity mStartStationEntity, StationEntity mEndStationEntity) {
        if (mStartStationEntity != null && mEndStationEntity != null) {

            List<StationEntity> stationEntityList = new ArrayList<>();
            stationEntityList.add(mStartStationEntity);
            stationEntityList.add(mEndStationEntity);

            String stationJsonStr = GsonSerializerHelper.getInstance().convertStationListToJsaonStr(stationEntityList);

            //Todo: Step 2: 检测乘客当前的位置是否在起始站台允许发布的合理半径内, 如果不在有效半径内, 该行程订单状态为未发布, 进入起始站台有效半径内, 发起调用后台接口
            TransLog transLog = new TransLog.Builder()
                    .phoneNumber(PreferenceUtils.getString(AppConstants.SIGN_PHONE))
                    .routeStatusCode(DBTags.ROUTE_TRANS_STATUS_UNPUBLISHED_CODE)
                    .routeStatusDesc(DBTags.ROUTE_TRANS_STATUS_UNPUBLISHED_DESC)
                    .createTime(DateUtils.format(new Date(), "yyyyMMddHHmmss"))//路由订单创建时间
                    .routeTransCategory(DBTags.ROUTE_TRANS_CATEGORY_PASSENGER)//路由订单分类: 乘客发布
                    .routeInfo(stationJsonStr)
                    .transAmount(DBTags.STATION_HITCHHIKE_PASSENGER_DEFAULT_AMOUNT).build();

            TransLogUtils.getInstance().insertTransLog(transLog);
        } else {
            getView().passPublishTripFial("起始点不能为空");
        }
    }

    public void requestEveryOne(final int originStationId, final int destinationStationId, final int passengerTripId, final boolean isDemo) {
        subscribeLoop = Observable.interval(0, 60, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        passGetMatchedCar(originStationId, destinationStationId, passengerTripId, isDemo);
                    }
                });
    }

    //乘客获取匹配车辆
    private void passGetMatchedCar(int originStationId, int destinationStationId, int passengerTripId, boolean isDemo) {
        mPassMatchedCarRequest.originStationId = originStationId;
        mPassMatchedCarRequest.destinationStationId = destinationStationId;
        mPassMatchedCarRequest.passengerTripId = passengerTripId;
        mPassMatchedCarRequest.token = PreferenceUtils.getString(AppConstants.USER_TOKEN);
        mPassMatchedCarRequest.isDemo = isDemo;
        mPassMatchedCarUsecase.subscribe(new Observer<PassMatchedCarEntity>() {
            @Override
            public void onCompleted() {
                KLog.e("onCompleted--- ");
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("onError--- ");
            }

            @Override
            public void onNext(PassMatchedCarEntity passMatchedCarEntity) {
                if (passMatchedCarEntity.data != null && passMatchedCarEntity.data.carModelList != null && passMatchedCarEntity.data.carModelList.size() > 0) {
                    getView().passGetMatchedCarSuccess(passMatchedCarEntity.data.carModelList);
                } else {
                    getView().passGetMatchedCarFail();
                }
            }
        }, mPassMatchedCarRequest);
    }


    @Override
    public void destroy() {
        super.destroy();
        StationUtil.unsubscribeUseCase(mPassPublishUsecase);
        StationUtil.unsubscribeUseCase(mPassMatchedCarUsecase);
    }

    public void destoryGetMatchedCar() {
        StationUtil.unsubscribeUseCase(mPassPublishUsecase);
        StationUtil.unsubscribeUseCase(mPassMatchedCarUsecase);
        StationUtil.unsubscribeSubscribe(subscribeLoop);
    }

    public void updateOrderState(String routeStatusCode, String routeStatusDesc) {
        TransLog transLog = TransLogUtils.getInstance().getLastTransLog(); //获取DB中最后一条行程订单记录
        transLog.setRouteStatusCode(routeStatusCode);
        transLog.setRouteStatusDesc(routeStatusDesc);
        TransLogUtils.getInstance().updateTransLog(transLog);
    }

    public String getOrderState() {
        TransLog transLog = TransLogUtils.getInstance().getLastTransLog(); //获取DB中最后一条行程订单记录
        return transLog != null ? transLog.getRouteStatusCode() : null;
    }


    public void payWithWeichat(){

    }

}
