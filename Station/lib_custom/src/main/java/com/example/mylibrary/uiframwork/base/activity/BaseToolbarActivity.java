package com.example.mylibrary.uiframwork.base.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mylibrary.R;
import com.example.mylibrary.component.utils.ClickUtils;
import com.example.mylibrary.uiframwork.helpers.ToolbarHelper;
import com.flyco.systembar.SystemBarHelper;

import butterknife.ButterKnife;


/**
 * base activity with toolbarHelper
 */
public abstract class BaseToolbarActivity extends SuperBaseActivity implements ToolbarHelper.ToolbarProvider, View.OnClickListener {

    private ToolbarHelper mToolbarHelper;


    /**
     * Get the ToolbarHelper to control the toolbar's properties
     *
     * @return {@link ToolbarHelper}
     */
    public ToolbarHelper getToolbarHelper() {
        return mToolbarHelper;
    }

    /**
     * 是否需要状态栏着色
     *
     * @return
     */
    public boolean isNeedTintStatusBar() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeOnCreate();
        super.onCreate(savedInstanceState);
        if (provideLayoutResourceID() != 0) {
            setContentView(provideLayoutResourceID());
        }

        // 设置状态栏颜色(必须在调用了setContentView布局之后调用，否则可能出现偏移现象)
        if (isNeedTintStatusBar()) {
            SystemBarHelper.tintStatusBar(this, getResources().getColor(R.color.status_bar_bg), 0.0f);
        }

        // 初始化视图和数据
        initializeViewsAndData(savedInstanceState);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (!isChild()) {
            onTitleChanged(getTitle(), getTitleColor());
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (null != mToolbarHelper) {
            mToolbarHelper.setTitle(title);
        }
    }

    @Override
    public void provideToolbar() {
        this.mToolbarHelper = new ToolbarHelper((Toolbar) findViewById(R.id.toolbar));
        if (null != this.mToolbarHelper.getToolbar()) {
            setSupportActionBar(this.mToolbarHelper.getToolbar());
        }

        if (this.mToolbarHelper.getToolbarTitle() == null) {
            throw new IllegalStateException("Layout file is required to include a TextView with id: toolbar_title");
        }
    }

    @Override
    public void onClick(View v) {
        boolean isSupportFastClick = ClickUtils.getSupportFastClick(v);
        if (!isSupportFastClick && ClickUtils.isFastRepeatClick(800)) {
            return;
        }
        dealClickAction(v);
    }

    /**
     * 封装给多个控件设置点击事件
     *
     * @param views
     */
    public void setSomeOnClickListeners(View... views) {
        ClickUtils.setSomeOnClickListeners(this, views);
    }

    /**
     * 提供布局
     *
     * @return
     */
    protected abstract int provideLayoutResourceID();

    /**
     * 初始化控件和数据
     *
     * @return
     */
    protected abstract void initializeViewsAndData(Bundle savedInstanceState);

    /**
     * 点击事件的处理
     *
     * @param v
     */
    protected abstract void dealClickAction(View v);

    /**
     * onCreate之前做的初始化动作
     */
    protected void beforeOnCreate(){}

}
