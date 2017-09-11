package com.example.mylibrary.uiframwork.base.mvp;

import android.content.Context;
import android.os.Bundle;

import com.example.mylibrary.uiframwork.base.activity.BaseToolbarActivity;
import com.example.mylibrary.uiframwork.base.mvp.presenter.MvpPresenter;
import com.example.mylibrary.uiframwork.base.mvp.view.MvpView;


public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>> extends BaseToolbarActivity implements MvpView {

    private P presenter;

    public P getPresenter() {
        if (presenter == null) {
            throw new IllegalArgumentException("Presenter must be initialized");
        }
        return presenter;
    }

    /**
     * 直接返回P的实例化方法
     *
     * @return
     */
    public abstract P createPresenter();

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = createPresenter();
        getPresenter().attachView((V) this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
        getPresenter().destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().resume();
    }

    @Override
    public Context context() {
        return getApplicationContext();
    }

}
