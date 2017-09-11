package com.example.mylibrary.domain.usecase.interactor.passenger;

import com.example.mylibrary.component.rx.SchedulersCompat;
import com.example.mylibrary.domain.DomainConstants;
import com.example.mylibrary.domain.model.request.PassPositionRequest;
import com.example.mylibrary.domain.model.response.PassPositionEntity;
import com.example.mylibrary.domain.repository.PassPositionRepository;
import com.example.mylibrary.domain.usecase.UseCase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class PassPositionUsecase extends UseCase<PassPositionEntity, PassPositionRequest> {

    private PassPositionRepository mPosSignInRepository;

    public PassPositionUsecase(PassPositionRepository posSignInRepository) {
        mPosSignInRepository = posSignInRepository;
    }

    @Override
    protected Observable<PassPositionEntity> interactor(PassPositionRequest params) {
        return mPosSignInRepository.passPostion(params)
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
                .concatMap(new Func1<PassPositionEntity, Observable<PassPositionEntity>>() {
                    @Override
                    public Observable<PassPositionEntity> call(PassPositionEntity response) {
                        return response.filterWebService();
                    }
                })
                // 应用线程变换
                .compose(SchedulersCompat.<PassPositionEntity>applyExecutorSchedulers());
    }
}
