package com.example.mylibrary.domain.repository;

import com.example.mylibrary.domain.model.request.VerifyCodeRequest;
import com.example.mylibrary.domain.model.response.VerifyCodeEntity;

import rx.Observable;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/3
 * Description:
 */

public interface VerifyCodeRepository {

    /**
     * 获取验证码
     * @param request
     * @return
     */
    Observable<VerifyCodeEntity> getVerifyCode(VerifyCodeRequest request);

}
