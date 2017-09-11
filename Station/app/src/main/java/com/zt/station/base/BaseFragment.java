package com.zt.station.base;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.mylibrary.uiframwork.base.fragment.BaseToolbarFragment;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class BaseFragment extends BaseToolbarFragment {
    protected Subscription mSubscriptionSale = Subscriptions.empty();

    protected void unSubscriberSale() {
        if (null != mSubscriptionSale && !mSubscriptionSale.isUnsubscribed()) {
            mSubscriptionSale.unsubscribe();
            mSubscriptionSale = null;
        }
    }

    @Override
    protected void beforeInitialize() {
        super.beforeInitialize();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscriberSale();
    }
}
