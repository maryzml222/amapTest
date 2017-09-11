package com.zt.station.feature.sign;

import android.app.Activity;
import android.text.TextUtils;

import com.example.lib_db.dbutil.UserInfoLogUtils;
import com.example.lib_db.model.UserInfoLog;
import com.example.mylibrary.component.utils.PreferenceUtils;
import com.example.mylibrary.domain.exception.ApiResponseException;
import com.example.mylibrary.domain.exception.ErrorHandling;
import com.example.mylibrary.domain.model.request.SignInRequest;
import com.example.mylibrary.domain.model.request.VerifyCodeRequest;
import com.example.mylibrary.domain.model.response.VerifyCodeEntity;
import com.example.mylibrary.domain.model.response.sign.SignInEntity;
import com.example.mylibrary.domain.usecase.interactor.signin.SignInUsecase;
import com.example.mylibrary.domain.usecase.interactor.signin.VerifyCodeUsecase;
import com.example.mylibrary.http.repository.SignInDataRepository;
import com.example.mylibrary.http.repository.VerifyCodeDataRepository;
import com.example.mylibrary.uiframwork.base.mvp.presenter.MvpBasePresenter;
import com.socks.library.KLog;
import com.zt.station.AppConstants;
import com.zt.station.R;
import com.zt.station.util.StationUtil;

import rx.Subscriber;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/7/19 23:50
 * version: v1.0.0
 * description:
 */

public class SignInPresenter extends MvpBasePresenter<ISignInView> {

    private SignInUsecase mPosSignInUsecase;
    private SignInRequest mPosSignInRequest;
    private VerifyCodeUsecase mVerifyCodeUsecase;
    private VerifyCodeRequest mVerifyCodeRequest;
    Activity activity;

    @Override
    public void initialize() {
        mPosSignInUsecase = new SignInUsecase(new SignInDataRepository());
        mPosSignInRequest = new SignInRequest();
        mVerifyCodeUsecase = new VerifyCodeUsecase(new VerifyCodeDataRepository());
        mVerifyCodeRequest = new VerifyCodeRequest();
    }

    public void initialize(Activity activity) {
        initialize();
        this.activity = activity;
    }

    /**
     * 签到
     */
    public void doSignIn(final String phone, String verificationCode) {
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(verificationCode)) {
            getView().doSignInFail(activity.getString(R.string.mobile_not_empty));

            return;
        }

        if (!StationUtil.isChinaPhoneLegal(phone)) {
            getView().doSignInFail(activity.getString(R.string.invalid_mobile));

            return;
        }

        if (!StationUtil.checkNumber(verificationCode)) {
            getView().doSignInFail(activity.getString(R.string.invalid_code));

            return;
        }

        mPosSignInRequest.cellphoneNumber = phone;
        mPosSignInRequest.code = verificationCode;

        //Todo...需要对手机号和后台发送的验证码做一些基本的验证, 验证通过以后需要调用后台的登录接口
        //---> step 1 : 调用后台登录接口
        //---> step 2 : 把后台登录返回的用户的一些基本信息进行存储
        mPosSignInUsecase.subscribe(new Subscriber<SignInEntity>() {
            @Override
            public void onCompleted() {
                KLog.e("--onCompleted---");
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("--onError--" + e.getMessage());
                getView().doSignInFail(ErrorHandling.propagate(getView().context(), e));
            }

            @Override
            public void onNext(SignInEntity posSignInEntity) {
                if (posSignInEntity != null) {
                    KLog.e("--onNext---" + posSignInEntity.data);
                    PreferenceUtils.putString(AppConstants.SIGN_PHONE, phone);
                    PreferenceUtils.putString(AppConstants.USER_TOKEN, posSignInEntity.data.token);
                    PreferenceUtils.putBoolean(AppConstants.IS_LOGIN, true);
                    PreferenceUtils.putBoolean(AppConstants.DRIVER, true);//Todo: 测试阶段默认打开isCarOwner属性
                    KLog.d("Current User Token Info : -----> " + posSignInEntity.data.token);

                    //用户信息存入数据库--UserInfoLog表中
                    UserInfoLog userInfoLog=new UserInfoLog();
                    userInfoLog.setToken(posSignInEntity.data.token);
                    userInfoLog.setIsLogin(true);
                    userInfoLog.setIsDriver(posSignInEntity.data.isCarOwner);
                    //userInfoLog.setIsDriver(true);
                    userInfoLog.setPhoneNumber(phone);
                    userInfoLog.setNickname("SH-0000001");
                    userInfoLog.setInaugurationUnit("站台信息科技");
                    UserInfoLogUtils.getInstance().insertTransLog(userInfoLog);

                    getView().doSignInSuccess();
                }
            }
        }, mPosSignInRequest);
    }

    public void sendVerificationCode(String phone) {
        if (TextUtils.isEmpty(phone)) {
            getView().doSendVerifyCodeFail(activity.getString(R.string.phone_not_empty));
            return;
        }

        if (!StationUtil.isChinaPhoneLegal(phone)) {
            getView().doSendVerifyCodeFail(activity.getString(R.string.invalid_mobile));
            return;
        }

        mVerifyCodeRequest.cellphoneNumber = phone;
        mVerifyCodeRequest.type = 0;

        mVerifyCodeUsecase.subscribe(new Subscriber<VerifyCodeEntity>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                KLog.d("--onError--" + e.getMessage());
                if (e instanceof ApiResponseException) {
                    //Toast.makeText(getView().context(), ErrorHandling.propagate(getView().context(), e), Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getView().context(), ErrorHandling.propagate(getView().context(), e), Toast.LENGTH_LONG).show();
                }

                getView().doSendVerifyCodeFail(ErrorHandling.propagate(getView().context(), e));
            }

            @Override
            public void onNext(VerifyCodeEntity verifyCodeEntity) {
                KLog.d("--onNext---");
                getView().doSendVerifyCodeSuccess();
            }
        }, mVerifyCodeRequest);
    }

    @Override
    public void destroy() {
        super.destroy();
        StationUtil.unsubscribeUseCase(mPosSignInUsecase);
        StationUtil.unsubscribeUseCase(mVerifyCodeUsecase);
    }
}