package com.example.mylibrary.domain.usecase.interactor.signin;

import com.example.mylibrary.component.rx.SchedulersCompat;
import com.example.mylibrary.domain.DomainConstants;
import com.example.mylibrary.domain.model.request.SignInRequest;
import com.example.mylibrary.domain.model.response.sign.SignInEntity;
import com.example.mylibrary.domain.repository.SignInRepository;
import com.example.mylibrary.domain.usecase.UseCase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

public class SignInUsecase extends UseCase<SignInEntity, SignInRequest> {

    private SignInRepository mPosSignInRepository;

    public SignInUsecase(SignInRepository posSignInRepository) {
        mPosSignInRepository = posSignInRepository;
    }

    @Override
    protected Observable<SignInEntity> interactor(SignInRequest params) {
        return mPosSignInRepository.posSignIn(params)
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
                .concatMap(new Func1<SignInEntity, Observable<SignInEntity>>() {
                    @Override
                    public Observable<SignInEntity> call(SignInEntity response) {
                        return response.filterWebService();
                    }
                })
                // 应用线程变换
                .compose(SchedulersCompat.<SignInEntity>applyExecutorSchedulers());
    }
}
