package com.zt.station.feature.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.zt.station.R;
import com.zt.station.feature.map.location.Location;
import com.zt.station.feature.map.location.StationInfo;
import com.zt.station.util.AMapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/7/29 16:47
 * version: v1.0.0
 * description:
 */

public class MapActivity extends Activity {

    public static final String REQUEST_STATION = "station";

    public static final String REQUEST_DRIVER = "driver";

    public AMapLocation mAMapLocation;

    private MapView mMapView = null;
    private AMap mAMap;

    private ListView mSearchLv;

    private boolean mIsAddIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaode_map);

        if(getIntent() != null){
            mIsAddIcon = getIntent().getBooleanExtra(REQUEST_DRIVER, false);
        }
        initMap(savedInstanceState);
        Toast.makeText(this, "长按地图选择目的地", Toast.LENGTH_SHORT).show();
        getMyLocation();

        initSearch();
    }

    private void initSearch() {
        mSearchLv = (ListView) findViewById(R.id.lv_list);
        EditText editText = (EditText) findViewById(R.id.product_search_edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getPoi(s.toString());
            }
        });
    }

    private void getPoi(String keyWord) {
        if(mAMapLocation != null){
            PoiSearch.Query query = new PoiSearch.Query(keyWord, "", mAMapLocation.getCityCode());
            query.setPageSize(10);// 设置每页最多返回多少条poiitem
            query.setPageNum(1);//设置查询页码

            PoiSearch poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(final PoiResult poiResult, int i) {
                    if(poiResult != null && poiResult.getPois().size() > 0){
                        mSearchLv.setVisibility(View.VISIBLE);
                        List<String> searchList = new ArrayList<>();
                        for (PoiItem poiItem : poiResult.getPois()) {
                            searchList.add(poiItem.getTitle());
                        }

                        mSearchLv.setAdapter(new ArrayAdapter(MapActivity.this, R.layout.textview_item, searchList));

                        mSearchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                LatLonPoint latLonPoint = poiResult.getPois().get(position).getLatLonPoint();
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()),17,0,0));
                                mAMap.moveCamera(cameraUpdate);
                                mSearchLv.setVisibility(View.GONE);

                                if(mIsAddIcon){
                                    mAMap.addMarker(new MarkerOptions()
                                            .position(AMapUtil.convertToLatLng(poiResult.getPois().get(position).getLatLonPoint()))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.map))
                                            .title(poiResult.getPois().get(position).getTitle()));
                                }

                                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(MapActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        });
                    }else{
                        mSearchLv.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();
        }
    }

    /*初始化地图*/
    private void initMap(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.gaode_map_view);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();//初始化地图控制器对象
        mAMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                getBusStation(latLng);
            }
        });

        mAMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent();
                StationInfo stationInfo = new StationInfo();
                stationInfo.title = marker.getTitle();
                stationInfo.lat = marker.getPosition().latitude;
                stationInfo.lon = marker.getPosition().longitude;

                intent.putExtra(REQUEST_STATION, stationInfo);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void getBusStation(LatLng latLng) {
        PoiSearch.Query query = new PoiSearch.Query("公交站", "", "");
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
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
        Location.getMyLocation(this, new AMapLocationListener(){

            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(!TextUtils.isEmpty(aMapLocation.getPoiName())){
//                    mAMap.addMarker(new MarkerOptions()
//                            .position(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()))
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location_small))
//                            .title(aMapLocation.getPoiName()));
                    MyLocationStyle myLocationStyle;
                    myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
                    myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.my_location_small));
                    myLocationStyle.strokeColor(getResources().getColor(R.color.blue));
                    myLocationStyle.radiusFillColor(getResources().getColor(R.color.actionsheet_blue));
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
                    myLocationStyle.showMyLocation(true);
                    mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
                    mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style

                    mAMapLocation = aMapLocation;
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()),17,0,0));
                    mAMap.moveCamera(cameraUpdate);
                }
            }
        });
    }
}
