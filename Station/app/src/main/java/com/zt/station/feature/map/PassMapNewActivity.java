package com.zt.station.feature.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.example.lib_db.dbutil.RoutesLogUtils;
import com.example.lib_db.model.DBTags;
import com.example.lib_db.model.RoutesLog;
import com.example.mylibrary.component.serializer.GsonSerializerHelper;
import com.example.mylibrary.component.utils.NetImageLoader;
import com.example.mylibrary.component.utils.PreferenceUtils;
import com.example.mylibrary.component.utils.ToastUtils;
import com.example.mylibrary.domain.model.request.PassMatchedCarRequest;
import com.example.mylibrary.domain.model.request.PassPublishRequest;
import com.example.mylibrary.domain.model.response.StationEntity;
import com.example.mylibrary.domain.model.response.passMatchedCar.CarEntity;
import com.example.mylibrary.domain.model.response.passPublishTrip.PassPublishEntity;
import com.example.mylibrary.domain.model.response.passPublishTrip.PassPublishResultEntity;
import com.example.mylibrary.domain.usecase.interactor.passenger.PassMatchedCarUsecase;
import com.example.mylibrary.domain.usecase.interactor.passenger.PassPublishUsecase;
import com.example.mylibrary.http.repository.PassMatchedCarDataRepository;
import com.example.mylibrary.http.repository.PassPublishDataRepository;
import com.pingplusplus.android.Pingpp;
import com.socks.library.KLog;
import com.zt.station.AppApplication;
import com.zt.station.AppConstants;
import com.zt.station.R;
import com.zt.station.base.BaseMvpActivity;
import com.zt.station.feature.map.line.DrivingRouteOverlay;
import com.zt.station.feature.map.location.Location;
import com.zt.station.feature.map.location.StationInfo;
import com.zt.station.feature.map.presenter.PassMapPresenter;
import com.zt.station.feature.map.view.IPassMapView;
import com.zt.station.feature.pingPay.ClientSDKActivity;
import com.zt.station.util.AMapUtil;
import com.zt.station.util.CircleImageView;
import com.zt.station.util.CustomDialogUtils;
import com.zt.station.util.MyAlertDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

import static com.zt.station.R.string.amount;
import static com.zt.station.feature.pingPay.ClientSDKActivity.CHANNEL_WECHAT;
import static com.zt.station.feature.pingPay.ClientSDKActivity.CHARGE_URL;
import static com.zt.station.feature.pingPay.ClientSDKActivity.LIVEMODE;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/7/29 16:47
 * version: v1.0.0
 * description: 乘客设置上下班行程、其他行程的主页面
 */

@Route(path = AppConstants.AROUTER_MAP_PASS)
public class PassMapNewActivity extends BaseMvpActivity<IPassMapView, PassMapPresenter> implements IPassMapView /*extends Activity implements View.OnClickListener*/ {
    public static final String REQUEST_START = "startStation";

    private MapView mMapView = null;
    private AMap mAMap;

    private PassPublishUsecase mPassPublishUsecase;
    private PassPublishRequest mPassPublishRequest;
    private PassMatchedCarUsecase mPassMatchedCarUsecase;
    private PassMatchedCarRequest mPassMatchedCarRequest;

    @Bind(R.id.select_location)
    RelativeLayout mSelectLocation;
    @Bind(R.id.set_loc_origin)
    TextView mSetLocStarTitle;
    @Bind(R.id.set_loc_end)
    TextView mSetLocEndTitle;

    @Bind(R.id.origin_stop)
    TextView mStartStopTextView;
    @Bind(R.id.end_stop)
    TextView mEndStopTextView;
    @Bind(R.id.btn_confirm)
    Button mConfirmBtn;

    @Bind(R.id.publish_tip_layout)
    LinearLayout mPublishTip;
    @Bind(R.id.set_location_start_stop)
    TextView mPubTripStartStop;
    @Bind(R.id.set_location_end_stop)
    TextView mPubTripEndStop;
    @Bind(R.id.set_location_bus_line)
    TextView mPubTripBusLine;
    @Bind(R.id.set_location_kilometre)
    TextView mPubTripKilometre;
    @Bind(R.id.set_location_confirm_policy)
    CheckBox mPubTripConfirm;
    @Bind(R.id.set_location_publish_tip)
    Button mPublishTripButton;

    @Bind(R.id.waite_trip)
    LinearLayout waiteTrip;
    @Bind(R.id.has_matched)
    LinearLayout hasMatched;
    @Bind(R.id.get_the_car)
    LinearLayout getTheCar;
    @Bind(R.id.finish)
    LinearLayout finish;
    @Bind(R.id.paysuccess)
    LinearLayout paysuccess;

    @Bind(R.id.toolbar_left_text_btn)
    TextView mBack;
    @Bind(R.id.toolbar_title)
    TextView mTitle;

    @Autowired(name = AppConstants.MAP_TITLE)
    String mMapTitle;
    @Autowired(name = AppConstants.MAP_PUBLISH_START)
    String mStartStop;
    @Autowired(name = AppConstants.MAP_PUBLISH_END)
    String mEndStop;
    @Autowired(name = AppConstants.MAP_ROUTES_CATEGORY)
    String mMapRoutesCategory;

    private StationInfo mStartStation, mEndStation;
    private StationEntity mStartStationEntity, mEndStationEntity;
    private PassPublishResultEntity mPassPublishResultEntity;
    private boolean isOnWorkRoutesSetting, isOffWorkRoutesSetting, isOtherRoutesSetting;
    private HashMap<String, String> mLineName = new HashMap<>();
    private List<CarEntity> carModelList = new ArrayList<>();

    private Dialog mCustomDialog;
    TripAdapter mHasMatchedAdapter;
    boolean mHasMatchedCarShowItemSize;
    ListView mHasMatchedCarLV;
    Bitmap defaultBitmap;

    @Override
    public PassMapPresenter createPresenter() {
        return new PassMapPresenter();
    }

    @Override
    protected int provideLayoutResourceID() {
        return R.layout.activity_gaode_map_pass;
    }

    @Override
    protected void initializeViewsAndData(Bundle savedInstanceState) {
        defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ff);
        getPresenter().initialize();
        initView();
        initMap(savedInstanceState);
        getMyLocation();
    }

    @Override
    protected void dealClickAction(View v) {
    }

    /**
     * 初始化地图
     *
     * @param savedInstanceState
     */
    private void initMap(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.gaode_map_view);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();//初始化地图控制器对象

//        mAMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
//            @Override
//            public void onMapLongClick(LatLng latLng) {
//                getBusStation(latLng);
//            }
//        });

        mAMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent();
                intent.putExtra(REQUEST_START, marker.getTitle());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mAMap.setOnPolylineClickListener(new AMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                startTrip(polyline.getPoints());
                Toast.makeText(PassMapNewActivity.this, mLineName.get(polyline.getId()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startTrip(List<LatLng> points) {
        // 获取轨迹坐标点
        LatLngBounds bounds = new LatLngBounds(points.get(0), points.get(points.size() - 2));
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        SmoothMoveMarker smoothMarker = new SmoothMoveMarker(mAMap);
        // 设置滑动的图标
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.drawable.bus));

        LatLng drivePoint = points.get(0);
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
        points.set(pair.first, drivePoint);
        List<LatLng> subList = points.subList(pair.first, points.size());

        // 设置滑动的轨迹左边点
        smoothMarker.setPoints(subList);
        // 设置滑动的总时间
        smoothMarker.setTotalDuration(40);
        // 开始滑动
        smoothMarker.startSmoothMove();
    }

    private void getBusStation(LatLng latLng) {
        PoiSearch.Query query = new PoiSearch.Query("公交站", "", "");
        query.setPageSize(10);//设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码

        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                mAMap.clear(true);
                for (int i1 = 0; i1 < poiResult.getPois().size(); i1++) {
                    mAMap.addMarker(new MarkerOptions()
                            .position(AMapUtil.convertToLatLng(poiResult.getPois().get(i1).getLatLonPoint()))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.map))
                            .title(poiResult.getPois().get(i1).getTitle()));
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
            }
        });
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latLng.latitude,
                latLng.longitude), 1000));//设置周边搜索的中心点以及半径
        poiSearch.searchPOIAsyn();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    private void initView() {
        mTitle.setText("");
        RoutesLog routesLogStart = null;

        showSetLocation();

       /* if (TextUtils.isEmpty(mStartStop)) {
            showSetLocation();
        } else {
            showPublishTrip();
        }

        if (DBTags.ROUTES_LOG_ON_WORK.equals(mMapRoutesCategory)) {
            //上班行程设置
            isOnWorkRoutesSetting = true;
            isOffWorkRoutesSetting = false;
            isOtherRoutesSetting = false;
            mSetLocStarTitle.setText(getString(R.string.map_view_home_title));
            mSetLocEndTitle.setText(getString(R.string.map_view_company_title));

            routesLogStart = RoutesLogUtils.getInstance().getRoutesLogByRoleAndDirect(DBTags.ROUTE_TRANS_CATEGORY_PASSENGER, DBTags.ROUTES_LOG_ON_WORK, PreferenceUtils.getString(AppConstants.SIGN_PHONE));
        } else if (DBTags.ROUTES_LOG_OFF_WORK.equals(mMapRoutesCategory)) {
            //下班行程设置
            isOffWorkRoutesSetting = true;
            isOnWorkRoutesSetting = false;
            isOtherRoutesSetting = false;
            mSetLocStarTitle.setText(getString(R.string.map_view_company_title));
            mSetLocEndTitle.setText(getString(R.string.map_view_home_title));

            routesLogStart = RoutesLogUtils.getInstance().getRoutesLogByRoleAndDirect(DBTags.ROUTE_TRANS_CATEGORY_PASSENGER, DBTags.ROUTES_LOG_OFF_WORK, PreferenceUtils.getString(AppConstants.SIGN_PHONE));
        } else if (DBTags.ROUTES_LOG_OTHER.equals(mMapRoutesCategory)) {
            //其他行程设置
            isOtherRoutesSetting = true;
            isOnWorkRoutesSetting = false;
            isOffWorkRoutesSetting = false;
            mSetLocStarTitle.setText(getString(R.string.map_view_start_title));
            mSetLocEndTitle.setText(getString(R.string.map_view_end_title));

            routesLogStart = RoutesLogUtils.getInstance().getRoutesLogByRoleAndDirect(DBTags.ROUTE_TRANS_CATEGORY_PASSENGER, DBTags.ROUTES_LOG_OTHER, PreferenceUtils.getString(AppConstants.SIGN_PHONE));
        }

        if (routesLogStart != null) {
            mStartStationEntity = GsonSerializerHelper.getInstance().deserialize(routesLogStart.getStartStationInformation(), StationEntity.class);
            mEndStationEntity = GsonSerializerHelper.getInstance().deserialize(routesLogStart.getEndStationInformation(), StationEntity.class);
            setStartAndEnd(mStartStationEntity, mEndStationEntity);

            getLineInfo(RouteSearch.DRIVING_MULTI_CHOICE_AVOID_CONGESTION_SAVE_MONEY);
            getLineInfo(RouteSearch.DRIVING_SINGLE_DEFAULT);
            getLineInfo(RouteSearch.DRIVING_SINGLE_SHORTEST);

            getBusLine(RouteSearch.BusDefault);
        }*/

        mStartStopTextView.setOnClickListener(this);
        mEndStopTextView.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mPublishTripButton.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);

        mPassPublishUsecase = new PassPublishUsecase(new PassPublishDataRepository());
        mPassPublishRequest = new PassPublishRequest();
        mPassMatchedCarUsecase = new PassMatchedCarUsecase(new PassMatchedCarDataRepository());
        mPassMatchedCarRequest = new PassMatchedCarRequest();
    }

    private void setStartAndEnd(StationEntity startStation, StationEntity endStation) {
        if (startStation != null && startStation.busStationLatitude != 0 && startStation.busStationLongitude != 0) {
            mStartStation = new StationInfo();
            mStartStation.title = startStation.busStationName;
            mStartStation.lat = startStation.busStationLatitude;
            mStartStation.lon = startStation.busStationLongitude;
        }
        if (endStation != null && endStation.busStationLatitude != 0 && endStation.busStationLongitude != 0) {
            mEndStation = new StationInfo();
            mEndStation.title = endStation.busStationName;
            mEndStation.lat = endStation.busStationLatitude;
            mEndStation.lon = endStation.busStationLongitude;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    public void getMyLocation() {
        Location.getMyLocation(this, new AMapLocationListener() {

            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (!TextUtils.isEmpty(aMapLocation.getPoiName())) {
                    MyLocationStyle myLocationStyle;
                    myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
                    myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.my_location_small));

                    myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
                    myLocationStyle.strokeWidth(0.5f);// 设置圆形的边框粗细

                    //myLocationStyle.strokeColor(getResources().getColor(R.color.blue));
                    //myLocationStyle.radiusFillColor(getResources().getColor(R.color.actionsheet_blue));
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
                    myLocationStyle.showMyLocation(true);
                    mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
                    mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 17, 0, 0));
                    mAMap.moveCamera(cameraUpdate);

                    if (mStartStation == null) {
                        mStartStation = new StationInfo();
                        mStartStation.lat = aMapLocation.getLatitude();
                        mStartStation.lon = aMapLocation.getLongitude();
                        mStartStation.title = "我的位置";

                        //将经纬度信息保存到全局变量中
                        AppApplication.getInstance().setCurrentLatitude(mStartStation.lat);
                        AppApplication.getInstance().setCurrentLongitude(mStartStation.lon);

                        mStartStopTextView.setText(mStartStation.title);
                        mStartStop = mStartStation.title;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.origin_stop:
                if (!PreferenceUtils.getBoolean(AppConstants.IS_LOGIN, false)) {
                    ARouter.getInstance().build(AppConstants.AROUTER_SIGN).withString(AppConstants.SIGN_ROLE, AppConstants.PASSENGER).
                            withTransition(R.anim.view_enter_from_right, R.anim.view_exit_to_left).navigation(this, AppConstants.SIGN_PASSENGER_REQUEST_CODE);
                } else {
                    ARouter.getInstance().build(AppConstants.AROUTER_SET_TRIP).withTransition(R.anim.view_enter_from_right, R.anim.view_exit_to_left).navigation(this, AppConstants.SELECT_START_REQUEST_CODE);
                }
                break;
            case R.id.end_stop:
                if (!PreferenceUtils.getBoolean(AppConstants.IS_LOGIN, false)) {
                    ARouter.getInstance().build(AppConstants.AROUTER_SIGN).withString(AppConstants.SIGN_ROLE, AppConstants.PASSENGER).
                            withTransition(R.anim.view_enter_from_right, R.anim.view_exit_to_left).navigation(this, AppConstants.SIGN_PASSENGER_REQUEST_CODE);
                } else {
                    ARouter.getInstance().build(AppConstants.AROUTER_SET_TRIP).withTransition(R.anim.view_enter_from_right, R.anim.view_exit_to_left).navigation(this, AppConstants.SELECT_STOP_RESPONSE_CODE);
                }
                break;
            case R.id.toolbar_left_text_btn:
                backEvent();

                break;
            case R.id.set_location_publish_tip:
                //Step 1: 检测同意三方协议的checkbox有没有勾选
                if (!mPubTripConfirm.isChecked()) {
                    ToastUtils.showToast(PassMapNewActivity.this, "请认真阅读并同意《顺风车三方服务协议》");
                    return;
                }

                mCustomDialog = CustomDialogUtils.showWaitDialog(PassMapNewActivity.this, "正在发布行程...", false, true);
                //Step 3: 调用后台发布行程的接口
                getPresenter().insertTrip(mStartStationEntity, mEndStationEntity);
                getPresenter().passPublishTrip(mStartStationEntity, mEndStationEntity);


                break;
            case R.id.has_get_the_car:
                //调用ping++支付
                new PaymentTask().execute(new PaymentRequest(CHANNEL_WECHAT, 4));
                break;
            case R.id.paying_now:
                showFinish();

                break;
            case R.id.btn_confirm:
                showPublishTrip();
                AppApplication.getInstance().isNeedReloadDataFromDB = true;//设置需要更新DB的Tag

                //step 1: 转换起始站台的信息
                mStartStationEntity = getPresenter().convertOriginStop(mStartStation);

                //step 2: 转换终点站台的信息
                mEndStationEntity = getPresenter().convertEndStop(mEndStation);

                //step 3: 将上下班的行程信息保存到数据库中
                //getPresenter().saveWorkTripToDB(mStartStationEntity, mEndStationEntity, mMapRoutesCategory, isOtherRoutesSetting);

                getLineInfo(RouteSearch.DRIVING_MULTI_CHOICE_AVOID_CONGESTION_SAVE_MONEY);
                getLineInfo(RouteSearch.DRIVING_SINGLE_DEFAULT);
                getLineInfo(RouteSearch.DRIVING_SINGLE_SHORTEST);
                mConfirmBtn.setVisibility(View.GONE);

                break;
            case R.id.pass_matched_car_icon:
                if (!mHasMatchedCarShowItemSize) {
                    mHasMatchedCarShowItemSize = true;
                    setListViewHeight(R.dimen.width129);
                    mHasMatchedAdapter.setHistoryList(carModelList);
                } else {
                    mHasMatchedCarShowItemSize = false;
                    setListViewHeight(R.dimen.width400);
                    mHasMatchedAdapter.setHistoryList(carModelList);
                }
                mHasMatchedAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void backEvent() {
        if (getPresenter().getOrderState() != null) {
            switch (getPresenter().getOrderState()) {
                case DBTags.ROUTE_TRANS_STATUS_UNPUBLISHED_CODE:
                    finish();
                    overridePendingTransition(com.example.mylibrary.R.anim.view_enter_from_left, com.example.mylibrary.R.anim.view_exit_to_right);
                    break;
                case DBTags.ROUTE_TRANS_STATUS_PUBLISHED_CODE:
                    showFinishDialog();
                    break;
                case DBTags.ROUTE_TRANS_STATUS_CANCELED_CODE:
                    finish();
                    overridePendingTransition(com.example.mylibrary.R.anim.view_enter_from_left, com.example.mylibrary.R.anim.view_exit_to_right);
                    break;
                case DBTags.ROUTE_TRANS_STATUS_PAID_CODE:
                    finish();
                    overridePendingTransition(com.example.mylibrary.R.anim.view_enter_from_left, com.example.mylibrary.R.anim.view_exit_to_right);
                    break;
                case DBTags.ROUTE_TRANS_STATUS_FINISHED_CODE:
                    finish();
                    overridePendingTransition(com.example.mylibrary.R.anim.view_enter_from_left, com.example.mylibrary.R.anim.view_exit_to_right);
                    break;

            }
        } else {
            finish();
            overridePendingTransition(com.example.mylibrary.R.anim.view_enter_from_left, com.example.mylibrary.R.anim.view_exit_to_right);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //backEvent();
    }

    private void showSetLocation() {
        mSelectLocation.setVisibility(View.VISIBLE);
        mPublishTip.setVisibility(View.GONE);
        waiteTrip.setVisibility(View.GONE);
        hasMatched.setVisibility(View.GONE);
        getTheCar.setVisibility(View.GONE);
        finish.setVisibility(View.GONE);
        paysuccess.setVisibility(View.GONE);
    }

    private void showPublishTrip() {
        mSelectLocation.setVisibility(View.GONE);
        mPublishTip.setVisibility(View.VISIBLE);
        waiteTrip.setVisibility(View.GONE);
        hasMatched.setVisibility(View.GONE);
        getTheCar.setVisibility(View.GONE);
        finish.setVisibility(View.GONE);
        paysuccess.setVisibility(View.GONE);

        mPubTripStartStop.setText(mStartStop.replace(AppConstants.ROUTES_STATION_REPLACE_VALUE, ""));
        mPubTripEndStop.setText(mEndStop.replace(AppConstants.ROUTES_STATION_REPLACE_VALUE, ""));
    }

    private void showWaite() {
        mPublishTip.setVisibility(View.GONE);
        waiteTrip.setVisibility(View.VISIBLE);
        hasMatched.setVisibility(View.GONE);
        getTheCar.setVisibility(View.GONE);
        finish.setVisibility(View.GONE);
        paysuccess.setVisibility(View.GONE);
    }

    private void showHasMatched() {
        mPublishTip.setVisibility(View.GONE);
        waiteTrip.setVisibility(View.GONE);
        hasMatched.setVisibility(View.VISIBLE);
        getTheCar.setVisibility(View.GONE);
        finish.setVisibility(View.GONE);
        paysuccess.setVisibility(View.GONE);

        mHasMatchedCarLV = (ListView) findViewById(R.id.match_car_list);
        TextView textView = (TextView) findViewById(R.id.match_car);
        LinearLayout imageView = (LinearLayout) findViewById(R.id.pass_matched_car_icon);
        imageView.setOnClickListener(this);
        //textView.setOnClickListener(this);
        textView.setText("已有 " + carModelList.size() + " 辆顺风车路程匹配");

        mHasMatchedAdapter = new TripAdapter(this, mHasMatchedCarLV);
        setListViewHeight(R.dimen.width129);
        mHasMatchedAdapter.setHistoryList(carModelList);
        mHasMatchedCarLV.setAdapter(mHasMatchedAdapter);

        mHasMatchedCarLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().destoryGetMatchedCar();
                showGetTheCar(carModelList.get(position));
            }
        });
    }

    private void setListViewHeight(int id) {
        ViewGroup.LayoutParams params = mHasMatchedCarLV.getLayoutParams();
        params.height = (int) getResources().getDimension(id);
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        mHasMatchedCarLV.setLayoutParams(params);
    }

    private void showGetTheCar(CarEntity driver) {
        mPublishTip.setVisibility(View.GONE);
        waiteTrip.setVisibility(View.GONE);
        hasMatched.setVisibility(View.GONE);
        getTheCar.setVisibility(View.VISIBLE);
        finish.setVisibility(View.GONE);
        paysuccess.setVisibility(View.GONE);
        TextView username = (TextView) findViewById(R.id.pass_get_car_username);
        TextView company = (TextView) findViewById(R.id.pass_get_car_company);
        final CircleImageView headIV = (CircleImageView) findViewById(R.id.pass_get_car_head);

        if (driver != null) {
            username.setText(driver.user.lastName + driver.user.firstName);
            company.setText(driver.user.companyShortName);

            //String url = driver.getHeadUrl();
            String url = "http://s3.lvjs.com.cn/trip/original/20140818131532_2090993967.jpg";
            if (!NetImageLoader.isNullOrEmpty(url)) {
                Bitmap headBitmap = NetImageLoader.getInstance()
                        .loadImage(url, new NetImageLoader.NetImageCallBack() {
                            @Override
                            public void onImageLoader(Bitmap bitmap, String path) {
                                if (bitmap != null && headIV != null) {
                                    headIV.setImageBitmap(bitmap);
                                }
                            }
                        }, AppApplication.CACHE_FOLDER);
                if (headBitmap != null) {
                    headIV.setImageBitmap(headBitmap);
                } else {
                    headIV.setImageBitmap(defaultBitmap);
                }
            }
        }

        TextView textView = (TextView) findViewById(R.id.has_get_the_car);
        textView.setOnClickListener(this);
    }

    private void showPaySuccess() {
        getPresenter().updateOrderState(DBTags.ROUTE_TRANS_STATUS_PAID_CODE, DBTags.ROUTE_TRANS_STATUS_PAID_DESC);
        mPublishTip.setVisibility(View.GONE);
        waiteTrip.setVisibility(View.GONE);
        hasMatched.setVisibility(View.GONE);
        getTheCar.setVisibility(View.GONE);
        finish.setVisibility(View.GONE);
        paysuccess.setVisibility(View.VISIBLE);
        TextView textView = (TextView) findViewById(R.id.paying_now);
        textView.setOnClickListener(this);
    }

    private void showFinish() {
        getPresenter().updateOrderState(DBTags.ROUTE_TRANS_STATUS_FINISHED_CODE, DBTags.ROUTE_TRANS_STATUS_FINISHED_DESC);
        mPublishTip.setVisibility(View.GONE);
        waiteTrip.setVisibility(View.GONE);
        hasMatched.setVisibility(View.GONE);
        getTheCar.setVisibility(View.GONE);
        finish.setVisibility(View.VISIBLE);
        paysuccess.setVisibility(View.GONE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == AppConstants.SELECT_START_REQUEST_CODE) {
                StationInfo station = (StationInfo) data.getSerializableExtra(MapActivity.REQUEST_STATION);
                int stationIcon = R.drawable.bus_stop;
                mStartStopTextView.setText(station.title.replace(AppConstants.ROUTES_STATION_REPLACE_VALUE, ""));
                mStartStop = station.title;
                stationIcon = R.drawable.bus_stop_brand;
                mStartStation = station;

                mAMap.addMarker(new MarkerOptions()
                        .position(new LatLng(station.lat, station.lon))
                        .icon(BitmapDescriptorFactory.fromResource(stationIcon))
                        .title(station.title));

                cheackStartAndStop();
            } else if (requestCode == AppConstants.SELECT_STOP_RESPONSE_CODE) {
                StationInfo station = (StationInfo) data.getSerializableExtra(MapActivity.REQUEST_STATION);
                int stationIcon = R.drawable.bus_stop;
                mEndStopTextView.setText(station.title.replace(AppConstants.ROUTES_STATION_REPLACE_VALUE, ""));
                mEndStop = station.title;
                stationIcon = R.drawable.bus_stop_brand_s;
                mEndStation = station;

                mAMap.addMarker(new MarkerOptions()
                        .position(new LatLng(station.lat, station.lon))
                        .icon(BitmapDescriptorFactory.fromResource(stationIcon))
                        .title(station.title));

                cheackStartAndStop();
            } else if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
                //支付页面返回处理
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                if (result != null) {
                    if (result.equals("success")) {
                        showPaySuccess();
                        return;
                    }
                }


                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                KLog.e("error_msg----- " + errorMsg);
                KLog.e("extra_msg----- " + extraMsg);
                showMsg(result, errorMsg, extraMsg);


            }


        }
    }

    private void cheackStartAndStop() {
        if (mStartStation != null && mEndStation != null) {
            mConfirmBtn.setVisibility(View.VISIBLE);
        }
    }

    public void getLineInfo(final int strategy) {
        RouteSearch routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int errorCode) {
                if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                        if (driveRouteResult.getPaths().size() > 0) {
                            final DrivePath drivePath = driveRouteResult.getPaths()
                                    .get(0);
                            DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                                    PassMapNewActivity.this, mMapView.getMap(), drivePath,
                                    driveRouteResult.getStartPos(),
                                    driveRouteResult.getTargetPos(), null);
                            drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                            drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                            drivingRouteOverlay.removeFromMap();
                            String lineId = drivingRouteOverlay.addToMap();
                            mLineName.put(lineId, strategy + "");
                            drivingRouteOverlay.zoomToSpan();
                        } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
                            Toast.makeText(PassMapNewActivity.this, "1", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(PassMapNewActivity.this, "2", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PassMapNewActivity.this, "3", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });

        LatLonPoint startPoint = new LatLonPoint(mStartStation.lat, mStartStation.lon);
        LatLonPoint endPoint = new LatLonPoint(mEndStation.lat, mEndStation.lon);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
        RouteSearch.DriveRouteQuery driveRouteQuery = new RouteSearch.DriveRouteQuery(fromAndTo, strategy, null, null, "");
        routeSearch.calculateDriveRouteAsyn(driveRouteQuery);
    }


    private void getBusLine(int strategy) {
        RouteSearch routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult result, int errorCode) {
                if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getPaths() != null) {
                        if (result.getPaths().size() > 0) {
                            List<BusPath> mBusPathList = result.getPaths();
                            for (int i = 0; i < mBusPathList.size(); i++) {
                                BusPath item = mBusPathList.get(i);
                                // KLog.e("title--- " + AMapUtil.getBusPathTitle(item));
                                //KLog.e("getBusPathDes--- " + AMapUtil.getBusPathDes(item));
                            }
                        } else {
                            ToastUtils.showToast(PassMapNewActivity.this, "距离较短建议 步行");
                        }
                    } else if (result != null && result.getPaths() == null) {
                        ToastUtils.showToast(PassMapNewActivity.this, getString(R.string.no_result));
                    } else {
                        ToastUtils.showToast(PassMapNewActivity.this, getString(R.string.no_result));
                    }
                } else {
                    ToastUtils.showToast(PassMapNewActivity.this, errorCode + "");
                }
            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
        LatLonPoint startPoint = new LatLonPoint(mStartStation.lat, mStartStation.lon);
        LatLonPoint endPoint = new LatLonPoint(mEndStation.lat, mEndStation.lon);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
        RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, RouteSearch.BusLeaseWalk,
                "0512", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
        routeSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
    }

    @Override
    public void passGetMatchedCarSuccess(List<CarEntity> list) {
        KLog.e("passGetMatchedCarSuccess--- ");
        //已匹配到车辆信息
        carModelList.removeAll(carModelList);
        carModelList.addAll(list);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showHasMatched();//延迟一秒显示Match到的车辆的信息
            }
        }, 1000);
    }

    @Override
    public void passGetMatchedCarFail() {
        KLog.e("passGetMatchedCarFail--- ");
        //当前未匹配到任何车辆的信息
        ToastUtils.showToast(PassMapNewActivity.this, "暂时未匹配到合适的车辆, 请耐心等待...");
    }

    @Override
    public void passPublishTripFial(String errMsg) {
        //ToastUtils.showToast(PassMapNewActivity.this, errMsg);
        new MyAlertDialog(this).builder().setTitle("发布失败，需要重新发布此次行程吗?").setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().updateOrderState(DBTags.ROUTE_TRANS_STATUS_CANCELED_CODE, DBTags.ROUTE_TRANS_STATUS_CANCELED_DESC);
            }
        }).setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().passPublishTrip(mStartStationEntity, mEndStationEntity);
            }
        }).show();
    }

    @Override
    public void passPublishTripSuccess(PassPublishEntity passPublishEntity) {
        CustomDialogUtils.closeDialog(mCustomDialog);
        showWaite();

    }

    private void showFinishDialog() {
        new MyAlertDialog(this).builder().setTitle("是否需要取消本次行程").setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().updateOrderState(DBTags.ROUTE_TRANS_STATUS_CANCELED_CODE, DBTags.ROUTE_TRANS_STATUS_CANCELED_DESC);
                getPresenter().destoryGetMatchedCar();
                finish();
                overridePendingTransition(com.example.mylibrary.R.anim.view_enter_from_left, com.example.mylibrary.R.anim.view_exit_to_right);
            }
        }).show();
    }

    class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {

        @Override
        protected void onPreExecute() {
            KLog.e("onPreExecute-----");
        }

        @Override
        protected String doInBackground(PaymentRequest... pr) {
            KLog.e("doInBackground-----");
            PaymentRequest paymentRequest = pr[0];
            String data = null;
            try {
                JSONObject object = new JSONObject();
                object.put("channel", paymentRequest.channel);
                object.put("amount", paymentRequest.amount);
                object.put("livemode", paymentRequest.livemode);
                object.put("app", "app_TC0Oq1bHKO08GeLK");
                String json = object.toString();
                //向Your Ping++ Server SDK请求数据
                data = postJson(CHARGE_URL, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        /**
         * 获得服务端的charge，调用ping++ sdk。
         */
        @Override
        protected void onPostExecute(String data) {
            KLog.e("onPostExecute-----");
            if (null == data) {
                showMsg("请求出错", "请检查URL", "URL无法获取charge");
                return;
            }
            Log.e("charge", data);

            //除QQ钱包外，其他渠道调起支付方式：
            //参数一：Activity  当前调起支付的Activity
            //参数二：data  获取到的charge或order的JSON字符串
            Pingpp.createPayment(PassMapNewActivity.this, data);

            //QQ钱包调用方式
            //参数一：Activity  当前调起支付的Activity
            //参数二：data  获取到的charge或order的JSON字符串
            //参数三：“qwalletXXXXXXX”需与AndroidManifest.xml中的scheme值一致
            //Pingpp.createPayment(ClientSDKActivity.this, data, "qwalletXXXXXXX");
        }

    }

    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        if (null != msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null != msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(PassMapNewActivity.this);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    /**
     * 获取charge
     *
     * @param urlStr charge_url
     * @param json   获取charge的传参
     * @return charge
     * @throws IOException
     */
    private static String postJson(String urlStr, String json) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.getOutputStream().write(json.getBytes());

        if (conn.getResponseCode() == 200) {
            BufferedReader
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
        return null;
    }

    class PaymentRequest {
        String channel;
        int amount;
        boolean livemode;

        public PaymentRequest(String channel, int amount) {
            this.channel = channel;
            this.amount = amount;
            this.livemode = LIVEMODE;
        }
    }
    /**
     * 监测返回键是否需要退出当前的应用
     */
    long DOUBLE_CLICK_TIME = 0L;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 500) {//修复连续点击时间控制from 2000ms --> 500ms
                new MyAlertDialog(this).builder().setTitle("您确定要退出站台应用吗？")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();

                DOUBLE_CLICK_TIME = System.currentTimeMillis();
            } else {
                finish();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
