package com.example.mylibrary.domain.usecase.interactor.driver;

import com.example.mylibrary.component.rx.SchedulersCompat;
import com.example.mylibrary.domain.DomainConstants;
import com.example.mylibrary.domain.model.request.DriverPullInRequest;
import com.example.mylibrary.domain.model.request.DriverSubmitPositionRequest;
import com.example.mylibrary.domain.model.response.DriverPullInEntity;
import com.example.mylibrary.domain.model.response.DriverSubmitPositionEntity;
import com.example.mylibrary.domain.repository.DriverPullInRepository;
import com.example.mylibrary.domain.repository.DriverSubmitPositionRepository;
import com.example.mylibrary.domain.usecase.UseCase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class DriverSubmitPositionUsecase extends UseCase<DriverSubmitPositionEntity, DriverSubmitPositionRequest> {

    private DriverSubmitPositionRepository mPosSignInRepository;

    public DriverSubmitPositionUsecase(DriverSubmitPositionRepository posSignInRepository) {
        mPosSignInRepository = posSignInRepository;
    }

    @Override
    protected Observable<DriverSubmitPositionEntity> interactor(DriverSubmitPositionRequest params) {
        return mPosSignInRepository.driverSubmitPosition(params)
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
                .concatMap(new Func1<DriverSubmitPositionEntity, Observable<DriverSubmitPositionEntity>>() {
                    @Override
                    public Observable<DriverSubmitPositionEntity> call(DriverSubmitPositionEntity response) {
                        return response.filterWebService();
                    }
                })
                // 应用线程变换
                .compose(SchedulersCompat.<DriverSubmitPositionEntity>applyExecutorSchedulers());
    }

}
