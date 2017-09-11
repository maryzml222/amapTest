package com.example.mylibrary.domain.usecase.interactor.driver;

import com.example.mylibrary.component.rx.SchedulersCompat;
import com.example.mylibrary.domain.DomainConstants;
import com.example.mylibrary.domain.model.request.DriverPublishTripRequest;
import com.example.mylibrary.domain.model.request.DriverPullInRequest;
import com.example.mylibrary.domain.model.response.DriverPublishTripEntity;
import com.example.mylibrary.domain.model.response.DriverPullInEntity;
import com.example.mylibrary.domain.repository.DriverPublishTripRepository;
import com.example.mylibrary.domain.repository.DriverPullInRepository;
import com.example.mylibrary.domain.usecase.UseCase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class DrivePullInUsecase extends UseCase<DriverPullInEntity, DriverPullInRequest> {

    private DriverPullInRepository mPosSignInRepository;

    public DrivePullInUsecase(DriverPullInRepository posSignInRepository) {
        mPosSignInRepository = posSignInRepository;
    }

    @Override
    protected Observable<DriverPullInEntity> interactor(DriverPullInRequest params) {
        return mPosSignInRepository.driverPullIn(params)
                // 超时时间
                .timeout(DomainConstants.RESPONSE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                // 超时重试1次
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer integer, Throwable throwable) {
                        return throwable instanceof TimeoutException && integer < 1;
                    }
                })
                // 解析基础错误信息，统一处理
                .concatMap(new Func1<DriverPullInEntity, Observable<DriverPullInEntity>>() {
                    @Override
                    public Observable<DriverPullInEntity> call(DriverPullInEntity response) {
                        return response.filterWebService();
                    }
                })
                // 应用线程变换
                .compose(SchedulersCompat.<DriverPullInEntity>applyExecutorSchedulers());
    }

}
