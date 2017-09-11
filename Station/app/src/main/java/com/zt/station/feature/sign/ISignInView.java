package com.zt.station.feature.sign;

import com.example.mylibrary.uiframwork.base.mvp.view.MvpView;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/06 23:50
 * version: v1.0.0
 * description:
 */

public interface ISignInView extends MvpView {

    void doSendVerifyCodeSuccess();

    void doSendVerifyCodeFail(String errorMsg);

    void doSignInSuccess();

    void doSignInFail(String errorMsg);

}

