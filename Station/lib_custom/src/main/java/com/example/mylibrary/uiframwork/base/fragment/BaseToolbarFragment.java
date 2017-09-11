package com.example.mylibrary.uiframwork.base.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mylibrary.R;
import com.example.mylibrary.component.utils.ClickUtils;
import com.example.mylibrary.component.utils.KeyboardUtils;
import com.example.mylibrary.uiframwork.helpers.ToolbarHelper;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;


public abstract class BaseToolbarFragment extends BaseStatisticsFragment implements ToolbarHelper.ToolbarProvider , View.OnClickListener{

    private ToolbarHelper mToolbarHelper;

    protected UnLeakHandler mHandler = null;

    public static class UnLeakHandler extends Handler {

        WeakReference<BaseToolbarFragment> mWeakReferenceFragment;

        public UnLeakHandler(BaseToolbarFragment fragment) {
            mWeakReferenceFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != mWeakReferenceFragment) {
                BaseToolbarFragment fragment = mWeakReferenceFragment.get();
                fragment.handleMessage(msg);
            }
        }
    }

    /**
     * handle messages
     *
     * @param msg
     */
    protected void handleMessage(Message msg) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mHandler = new UnLeakHandler(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return provideLayoutResourceID() != 0 ? inflater.inflate(provideLayoutResourceID(), container, false) : super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        beforeInitialize();

        // 初始化视图和数据
        initialize();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {
        if (!isVisibleToUser && null != getView()) {
            KeyboardUtils.hideSoftKeyBoard(getActivity(), getView());
        }
        super.onVisibleToUserChanged(isVisibleToUser, invokeInResumeOrPause);
    }

    /**
     * Get the ToolbarHelper to control the toolbar's properties
     *
     * @return {@link ToolbarHelper}
     */
    public ToolbarHelper getToolbarHelper() {
        return mToolbarHelper;
    }

    @Override
    public void provideToolbar() {
        this.mToolbarHelper = new ToolbarHelper((Toolbar) getView().findViewById(R.id.toolbar));

        if (this.mToolbarHelper.getToolbarTitle() == null) {
            throw new IllegalStateException("Layout file is required to include a TextView with id: toolbar_title");
        }
    }

    @Override
    public void onClick(View v) {
        boolean isSupportFastClick= ClickUtils.getSupportFastClick(v);
        if (!isSupportFastClick && ClickUtils.isFastRepeatClick(800)){
            return;
        }
        dealClickAction(v);
    }

    /**
     * 封装给多个控件设置点击事件
     * @param views
     */
    public void setSomeOnClickListeners(View... views){
        ClickUtils.setSomeOnClickListeners(this,views);
    }


    /**
     * 提供布局
     *
     * @return
     */
    protected abstract int provideLayoutResourceID();

    /**
     * 初始化控件和数据
     * @return
     */
    protected abstract void initialize();

    /**
     * 点击事件的处理
     * @param v
     * @return
     */
    protected abstract void dealClickAction(View v);

    /**
     * 初始化之前的操作
     */
    protected void beforeInitialize(){}
}
