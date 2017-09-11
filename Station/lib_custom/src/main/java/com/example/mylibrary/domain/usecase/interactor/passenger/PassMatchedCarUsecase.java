package com.example.mylibrary.domain.usecase.interactor.passenger;

import com.example.mylibrary.component.rx.SchedulersCompat;
import com.example.mylibrary.domain.DomainConstants;
import com.example.mylibrary.domain.model.request.PassMatchedCarRequest;
import com.example.mylibrary.domain.model.response.passMatchedCar.PassMatchedCarEntity;
import com.example.mylibrary.domain.repository.PassMatchedCarRepository;
import com.example.mylibrary.domain.usecase.UseCase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class PassMatchedCarUsecase extends UseCase<PassMatchedCarEntity, PassMatchedCarRequest> {

    private PassMatchedCarRepository mPosSignInRepository;

    public PassMatchedCarUsecase(PassMatchedCarRepository posSignInRepository) {
        mPosSignInRepository = posSignInRepository;
    }

    @Override
    protected Observable<PassMatchedCarEntity> interactor(PassMatchedCarRequest params) {
        return mPosSignInRepository.passMatchedCar(params)
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
                .concatMap(new Func1<PassMatchedCarEntity, Observable<PassMatchedCarEntity>>() {
                    @Override
                    public Observable<PassMatchedCarEntity> call(PassMatchedCarEntity response) {
                        return response.filterWebService();
                    }
                })
                // 应用线程变换
                .compose(SchedulersCompat.<PassMatchedCarEntity>applyExecutorSchedulers());
    }
}
