package com.example.mylibrary.domain.usecase.interactor.signin;

import com.example.mylibrary.component.rx.SchedulersCompat;
import com.example.mylibrary.domain.DomainConstants;
import com.example.mylibrary.domain.model.request.VerifyCodeRequest;
import com.example.mylibrary.domain.model.response.VerifyCodeEntity;
import com.example.mylibrary.domain.repository.VerifyCodeRepository;
import com.example.mylibrary.domain.usecase.UseCase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/3
 * Description:
 */

public class VerifyCodeUsecase extends UseCase<VerifyCodeEntity, VerifyCodeRequest> {

    private VerifyCodeRepository mVerifyCodeRepository;

    public VerifyCodeUsecase(VerifyCodeRepository verifyCodeRepository) {
        mVerifyCodeRepository = verifyCodeRepository;
    }

    @Override
    protected Observable<VerifyCodeEntity> interactor(VerifyCodeRequest params) {
        return mVerifyCodeRepository.getVerifyCode(params)
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
                .concatMap(new Func1<VerifyCodeEntity, Observable<VerifyCodeEntity>>() {
                    @Override
                    public Observable<VerifyCodeEntity> call(VerifyCodeEntity response) {
                        return response.filterWebService();
                    }
                })
                // 应用线程变换
                .compose(SchedulersCompat.<VerifyCodeEntity>applyExecutorSchedulers());
    }
}
