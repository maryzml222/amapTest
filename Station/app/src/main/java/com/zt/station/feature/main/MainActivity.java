package com.zt.station.feature.main;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.zt.station.AppConstants;
import com.zt.station.R;
import com.zt.station.base.BaseMvpActivity;
import com.zt.station.feature.map.location.Location;

import butterknife.Bind;

public class MainActivity extends BaseMvpActivity<IMainView, MainPresenter> implements IMainView {
    //地图相关的View
    private MapView mMapView = null;
    private AMap mAMap;
    //地图元素样式设置参数
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);


    @Bind(R.id.near)
    TextView near;
    @Bind(R.id.route)
    TextView route;
    @Bind(R.id.myinfo)
    TextView myinfo;

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    protected int provideLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeViewsAndData(Bundle savedInstanceState) {
        getPresenter().initialize();
        setSomeOnClickListeners(near,route,myinfo);
        initMap(savedInstanceState);
        displayUserCurrentLocation();
    }

    @Override
    protected void dealClickAction(View v) {
        switch (v.getId()){
            case R.id.near:
                break;
            case R.id.route:
                ARouter.getInstance().build(AppConstants.AROUTER_ROADS).withString(AppConstants.SIGN_ROLE, AppConstants.PASSENGER).
                        withTransition(R.anim.view_enter_from_right, R.anim.view_exit_to_left).navigation(this);
                break;
            case R.id.myinfo:
                break;
        }

    }

    private void initMap(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.gaode_map_view);
        mMapView.onCreate(savedInstanceState);

        mAMap = mMapView.getMap();//初始化地图控制器对象
        mAMap.getUiSettings().setRotateGesturesEnabled(false);//关闭旋转手势
    }

    public void displayUserCurrentLocation() {
        Location.getMyLocation(this, new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (!TextUtils.isEmpty(aMapLocation.getPoiName())) {
                    MyLocationStyle myLocationStyle;
                    myLocationStyle = new MyLocationStyle();
                    myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.current_location_mine));
                    myLocationStyle.strokeColor(STROKE_COLOR);
                    myLocationStyle.radiusFillColor(FILL_COLOR);
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
                    myLocationStyle.showMyLocation(true);
                    mAMap.setMyLocationEnabled(true);
                    mAMap.setMyLocationStyle(myLocationStyle);

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 17, 0, 0));
                    mAMap.moveCamera(cameraUpdate);


                }
            }
        });
    }
}
