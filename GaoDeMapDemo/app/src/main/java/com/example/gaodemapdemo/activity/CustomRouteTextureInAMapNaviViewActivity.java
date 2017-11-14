package com.example.gaodemapdemo.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.RouteOverlayOptions;
import com.example.gaodemapdemo.R;

public class CustomRouteTextureInAMapNaviViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);

        //设置布局完全不可见
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();

        RouteOverlayOptions routeOverlayOptions = new RouteOverlayOptions();

        routeOverlayOptions.setArrowOnTrafficRoute(BitmapFactory.decodeResource(getResources(),R.drawable.custtexture_aolr));
        routeOverlayOptions.setNormalRoute(BitmapFactory.decodeResource(getResources(), R.drawable.custtexture));

        routeOverlayOptions.setUnknownTraffic(BitmapFactory.decodeResource(getResources(),R.drawable.custtexture_no));
        routeOverlayOptions.setSmoothTraffic(BitmapFactory.decodeResource(getResources(), R.drawable.custtexture_green));
        routeOverlayOptions.setSlowTraffic(BitmapFactory.decodeResource(getResources(), R.drawable.custtexture_slow));
        routeOverlayOptions.setJamTraffic(BitmapFactory.decodeResource(getResources(), R.drawable.custtexture_bad));
        routeOverlayOptions.setVeryJamTraffic(BitmapFactory.decodeResource(getResources(), R.drawable.custtexture_grayred));
        options.setRouteOverlayOptions(routeOverlayOptions);

        mAMapNaviView.setViewOptions(options);

    }
    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);

    }

    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        super.onCalculateRouteSuccess(ids);
        mAMapNavi.startNavi(NaviType.EMULATOR);
    }
}
