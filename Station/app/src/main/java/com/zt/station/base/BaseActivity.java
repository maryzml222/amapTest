package com.zt.station.base;

import android.app.Dialog;
import android.view.MotionEvent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.mylibrary.component.utils.CheckUtils;
import com.example.mylibrary.component.utils.DeviceUtils;
import com.example.mylibrary.uiframwork.base.activity.BaseToolbarActivity;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class BaseActivity extends BaseToolbarActivity {

    protected Subscription mSubscriptionSale = Subscriptions.empty();

    protected void unSubscriberSale() {
        if (null != mSubscriptionSale && !mSubscriptionSale.isUnsubscribed()) {
            mSubscriptionSale.unsubscribe();
            mSubscriptionSale = null;
        }
    }

    private Subscription subscriptionCheckActFinish, subscriptionCheckNetwork;
    //最近一次的操作时间
    private long lastTouchTimeMillis = 0;
    //检测是否需要关闭界面的周期为60s
    private final int DELAY_CHECK_ACT_FINISH = 60;
    //检测网络，周期3s
    private final int DELAY_CHECK_NETWORK = 3;
    //网络监测dialog
    private Dialog netErrorDialog;
    //60s监测是否需要关闭页面
    private Subscriber subscriberCheckActFinish = new Subscriber() {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Object o) {
            if (getLastTouchTimeMillis() > 0) {
                long diffSecs = (System.currentTimeMillis() - getLastTouchTimeMillis()) / 1000;
                if (diffSecs < DELAY_CHECK_ACT_FINISH) {
                    if (!subscriptionCheckActFinish.isUnsubscribed()) {
                        subscriptionCheckActFinish.unsubscribe();
                    }
                    int delaytemp = 0;
                    if (diffSecs < 0) {
                        delaytemp = DELAY_CHECK_ACT_FINISH;
                    } else {
                        delaytemp = (int) (DELAY_CHECK_ACT_FINISH - diffSecs);
                    }
                    subscriptionCheckActFinish = CheckUtils.checkActivityNeedFinsh(subscriptionCheckActFinish, this, delaytemp);
                    return;
                }
            }
            finish();
        }

    };
    //检测网络
    private Subscriber subscriberCheckNetwork = new Subscriber() {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Object o) {
            if (!isFinishing()) {
                try {
                    //每隔两秒判断一次，网络是否连接
                    if (DeviceUtils.isNetworkAvailable(getApplicationContext())) {
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void beforeOnCreate() {
        super.beforeOnCreate();
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheckActFinsh()) {
            //检测页面是否需要关闭，如果60内无操作则关闭
            subscriptionCheckActFinish = CheckUtils.checkActivityNeedFinsh(subscriptionCheckActFinish, subscriberCheckActFinish, DELAY_CHECK_ACT_FINISH);
        }
        //检测网络是否OK
        subscriptionCheckNetwork = CheckUtils.checkNetwork(subscriptionCheckNetwork, subscriberCheckNetwork, DELAY_CHECK_NETWORK);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CheckUtils.cancelCheck(subscriptionCheckActFinish);
        CheckUtils.cancelCheck(subscriptionCheckNetwork);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscriberSale();
    }

    public long getLastTouchTimeMillis() {
        return lastTouchTimeMillis;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        lastTouchTimeMillis = System.currentTimeMillis();
        return super.onTouchEvent(event);
    }

    /**
     * 是否打开：检测页面是否需要关闭，如果60内无操作则关闭
     *
     * @return 默认打开
     */
    public boolean isNeedCheckActFinsh() {
        return false;
    }

}
