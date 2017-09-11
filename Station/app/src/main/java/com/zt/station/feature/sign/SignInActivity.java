package com.zt.station.feature.sign;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.mylibrary.component.utils.KeyboardUtils;
import com.example.mylibrary.component.utils.ToastUtils;
import com.zt.station.AppConstants;
import com.zt.station.R;
import com.zt.station.base.BaseMvpActivity;
import com.zt.station.util.CustomDialogUtils;

import butterknife.Bind;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/7/19 23:50
 * version: v1.0.0
 * description: 手机号作为登录唯一的key, 无需注册, 调用后台手机验证码登录, 一个帐号会有两个角色: 乘客和司机
 */

@Route(path = AppConstants.AROUTER_SIGN)
public class SignInActivity extends BaseMvpActivity<ISignInView, SignInPresenter> implements ISignInView {

    @Bind(R.id.sign)
    TextView mSignTextView;
    @Bind(R.id.send_verification_code)
    TextView mSendVerificationCodeTextView;
    @Bind(R.id.mobile)
    EditText mMobileEditText;
    @Bind(R.id.verification_code)
    EditText mVerificationCodeEditText;

    private Dialog mCustomDialog;

    @Override
    protected int provideLayoutResourceID() {
        return R.layout.activity_signin;
    }

    @Override
    protected void initializeViewsAndData(Bundle savedInstanceState) {
        getPresenter().initialize(this);
        provideToolbar();
        setTitle(getString(R.string.sign));
        getToolbarHelper().enableBack(this);
        setSomeOnClickListeners(mSignTextView, mSendVerificationCodeTextView);
    }

    @Override
    protected void dealClickAction(View v) {
        KeyboardUtils.hideSoftKeyBoard(this, v);
        String phone = mMobileEditText.getText().toString();
        String verificationCode = mVerificationCodeEditText.getText().toString();

        switch (v.getId()) {
            case R.id.sign:
                mCustomDialog = CustomDialogUtils.showWaitDialog(SignInActivity.this, "正在登录中...", false, true);
                getPresenter().doSignIn(phone, verificationCode);

                break;
            case R.id.send_verification_code:
                mCustomDialog = CustomDialogUtils.showWaitDialog(SignInActivity.this, "正在发送验证码...", false, true);
                getPresenter().sendVerificationCode(phone);

                break;
        }
    }

    @Override
    public boolean isNeedCheckActFinsh() {
        return false;
    }

    @Override
    public SignInPresenter createPresenter() {
        return new SignInPresenter();
    }

    @Override
    public void doSendVerifyCodeSuccess() {
        //发送验证码成功
        CustomDialogUtils.closeDialog(mCustomDialog);
        ToastUtils.showToast(this, getString(R.string.verify_code_send_success));
    }

    @Override
    public void doSendVerifyCodeFail(String errorMsg) {
        //发送验证码失败
        CustomDialogUtils.closeDialog(mCustomDialog);
        ToastUtils.showToast(this, errorMsg);
    }

    @Override
    public void doSignInSuccess() {
        //登录成功
        CustomDialogUtils.closeDialog(mCustomDialog);
        setResult(AppConstants.SIGN_PASSENGER_RESPONSE_CODE);
        finish();
        overridePendingTransition(R.anim.view_enter_from_left, R.anim.view_exit_to_right);
    }

    @Override
    public void doSignInFail(String errorMsg) {
        //登录失败
        CustomDialogUtils.closeDialog(mCustomDialog);
        ToastUtils.showToast(this, errorMsg);
    }
}