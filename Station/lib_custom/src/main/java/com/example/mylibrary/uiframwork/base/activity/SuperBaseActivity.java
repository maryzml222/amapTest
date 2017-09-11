package com.example.mylibrary.uiframwork.base.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;


import com.example.mylibrary.uiframwork.helpers.ActivityManager;

import java.lang.ref.WeakReference;


public abstract class SuperBaseActivity extends AppCompatActivity {

    protected UnLeakHandler mHandler = null;

    public static class UnLeakHandler extends Handler {

        WeakReference<SuperBaseActivity> mWeakReferenceActivity;

        public UnLeakHandler(SuperBaseActivity activity) {
            mWeakReferenceActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != mWeakReferenceActivity) {
                SuperBaseActivity activity = mWeakReferenceActivity.get();
                activity.handleMessage(msg);
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

    protected int mDensity = 0;
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityManager.getInstance().addActivity(this);
        mHandler = new UnLeakHandler(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mDensity = displayMetrics.densityDpi;
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
