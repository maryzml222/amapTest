package com.example.mylibrary.uiframwork.base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.mylibrary.uiframwork.base.fragment.BaseToolbarFragment;
import com.example.mylibrary.uiframwork.base.mvp.presenter.MvpPresenter;
import com.example.mylibrary.uiframwork.base.mvp.view.MvpView;


public abstract class MvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends BaseToolbarFragment implements MvpView {

    public abstract P getPresenter();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().attachView((V) this);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        super.onVisibleToUserChanged(isVisibleToUser, invokeInResumeOrPause);
        if (isVisibleToUser) {
            getPresenter().resume();
        } else {
            getPresenter().pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
        getPresenter().destroy();
    }

    @Override
    public Context context() {
        return getContext();
    }
}
