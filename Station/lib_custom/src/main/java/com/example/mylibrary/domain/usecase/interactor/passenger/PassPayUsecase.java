package com.example.mylibrary.domain.usecase.interactor.passenger;

import com.example.mylibrary.component.rx.SchedulersCompat;
import com.example.mylibrary.domain.DomainConstants;
import com.example.mylibrary.domain.model.request.PassPayRequest;
import com.example.mylibrary.domain.model.response.PassPayEntity;
import com.example.mylibrary.domain.repository.PassPayRepository;
import com.example.mylibrary.domain.usecase.UseCase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class PassPayUsecase extends UseCase<PassPayEntity, PassPayRequest> {

    private PassPayRepository mPosSignInRepository;

    public PassPayUsecase(PassPayRepository posSignInRepository) {
        mPosSignInRepository = posSignInRepository;
    }

    @Override
    protected Observable<PassPayEntity> interactor(PassPayRequest params) {
        return mPosSignInRepository.passPay(params)
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
                .concatMap(new Func1<PassPayEntity, Observable<PassPayEntity>>() {
                    @Override
                    public Observable<PassPayEntity> call(PassPayEntity response) {
                        return response.filterWebService();
                    }
                })
                // 应用线程变换
                .compose(SchedulersCompat.<PassPayEntity>applyExecutorSchedulers());
    }
}
