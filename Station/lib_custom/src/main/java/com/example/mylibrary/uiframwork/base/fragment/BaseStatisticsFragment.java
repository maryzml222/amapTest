package com.example.mylibrary.uiframwork.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.socks.library.KLog;

public abstract class BaseStatisticsFragment extends Fragment implements BaseStatisticsFragmentVisibleController.UserVisibleCallback {

    private String mFragmentName = null;
    private BaseStatisticsFragmentVisibleController mFragmentVisibleController;

    protected boolean isFetchDataOnlyOnce = true;

    public BaseStatisticsFragment() {
        mFragmentName = getClass().getSimpleName();
        mFragmentVisibleController = new BaseStatisticsFragmentVisibleController(this, this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentVisibleController.activityCreated();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentVisibleController.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFragmentVisibleController.pause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mFragmentVisibleController.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void setWaitingShowToUser(boolean waitingShowToUser) {
        mFragmentVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    @Override
    public boolean isWaitingShowToUser() {
        return mFragmentVisibleController.isWaitingShowToUser();
    }

    @Override
    public boolean isVisibleToUser() {
        return mFragmentVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        KLog.d(mFragmentName + "##onVisibleToUserChanged: isVisibleToUser -- " + isVisibleToUser + " invokeInResumeOrPause -- " + invokeInResumeOrPause);
        if (isVisibleToUser && isFetchDataOnlyOnce) {
            // 可见时才加载数据
            isFetchDataOnlyOnce = forceFetchData();
            fetchData();
        }
    }

    /**
     * 界面可见时是否加载数据(如果没有setOffscreenPageLimit，则被回收后的页面还是会加载数据)
     *
     * @return true: 每次都加载数据, false: 只在第一次加载数据
     */
    protected abstract boolean forceFetchData();

    /**
     * 加载数据
     */
    protected abstract void fetchData();
}