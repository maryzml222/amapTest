package com.zt.station.feature.road;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zt.station.AppConstants;
import com.zt.station.R;
import com.zt.station.base.BaseMvpActivity;

@Route(path = AppConstants.AROUTER_ROADS)
public class RoadsActivity extends BaseMvpActivity<IroadsView,RoadsPresenter> implements IroadsView{

    @Override
    public RoadsPresenter createPresenter() {
        return new RoadsPresenter();
    }

    @Override
    protected int provideLayoutResourceID() {
        return R.layout.activity_roads;
    }

    @Override
    protected void initializeViewsAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void dealClickAction(View v) {

    }
}
