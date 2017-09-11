package com.zt.station.feature.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.busline.BusStationQuery;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.busline.BusStationSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.mylibrary.component.serializer.GsonSerializerHelper;
import com.example.mylibrary.component.utils.HorizontalListView;
import com.example.mylibrary.component.utils.PreferenceUtils;
import com.example.mylibrary.component.utils.ToastUtils;
import com.example.mylibrary.domain.model.response.StationEntity;
import com.zt.station.AppApplication;
import com.zt.station.AppConstants;
import com.zt.station.R;
import com.zt.station.base.BaseMvpActivity;
import com.zt.station.feature.map.location.StationInfo;
import com.zt.station.feature.map.presenter.SetTripPresenter;
import com.zt.station.feature.map.view.ISetTripView;
import com.zt.station.util.CustomDialogUtils;
import com.zt.station.util.StationUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;

@Route(path = AppConstants.AROUTER_SET_TRIP)
public class SetEndStationActivity extends BaseMvpActivity<ISetTripView, SetTripPresenter> implements ISetTripView {

    @Bind(R.id.search_history)
    ListView mSearchHistoryView;
    @Bind(R.id.search_list)
    ListView mSearchListView;

    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.bus_station)
    LinearLayout mBusStation;
    @Bind(R.id.layout)
    LinearLayout mLayoutMain;

    @Bind(R.id.product_search_edit_text)
    EditText mSearchText;

    @Bind(R.id.select_from_map)
    LinearLayout mSelectFromMap;

    @Bind(R.id.near_bus_line)
    HorizontalListView mNearBusLine;
    @Bind(R.id.near_bus_stop)
    HorizontalListView mNearBusStop;

    Adapter mAdapter;
    List<StationEntity> mListData;
    public double currentLongitude = 0.0;
    public double currentLatitude = 0.0;

    private Dialog mCustomDialog;
    private List<StationEntity> mNearbyStationLists = new ArrayList<>();//附近的站台信息列表
    List<String> mNearByLineLists = new ArrayList<>();
    List<BusStationItem> mSearchBusStation = new ArrayList<>();

    @Override
    protected int provideLayoutResourceID() {
        return R.layout.activity_set_end_station;
    }

    public static void startActivity(Activity activity, Bundle bundle) {
        Intent intent = new Intent(activity, SetEndStationActivity.class);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    protected void initializeViewsAndData(Bundle savedInstanceState) {
        getPresenter().initialize();
        setSomeOnClickListeners(back, mBusStation, mSelectFromMap);
        mLayoutMain.setVisibility(View.VISIBLE);
        mSearchListView.setVisibility(View.GONE);

        initData();
        initNearBus();

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (StationUtil.hasDigit(mSearchText.getText().toString())) {
                        //包含数字，即为查询公交路线
                        ARouter.getInstance().build(AppConstants.AROUTER_BUS_LINE)
                                .withString(AppConstants.MAP_TITLE, mSearchText.getText().toString())
                                .withTransition(R.anim.view_enter_from_right, R.anim.view_exit_to_left)
                                .navigation(SetEndStationActivity.this, AppConstants.BUS_LINE_STATION_REQUEST);
                    } else {
                        //不包含数字即为查询公交站台
                        getBusStation(mSearchText.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });

        /*mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/


    }

    //通过公交站台搜索公交路线
    private void getBusLine(String name) {
        BusStationQuery busStationQuery = new BusStationQuery(name, "0512");
        BusStationSearch busStationSearch = new BusStationSearch(this, busStationQuery);
        busStationSearch.setOnBusStationSearchListener(new BusStationSearch.OnBusStationSearchListener() {
            @Override
            public void onBusStationSearched(BusStationResult result, int rCode) {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getPageCount() > 0 && result.getBusStations() != null && result.getBusStations().size() > 0) {

                        ArrayList<BusStationItem> item = (ArrayList<BusStationItem>) result.getBusStations();
                        for (int i = 0; i < item.size(); i++) {
                            List<BusLineItem> lineItems = item.get(i).getBusLineItems();
                            for (int k = 0; k < lineItems.size(); k++) {
                                if (StationUtil.countInnerStr(lineItems.get(k).getBusLineName(), "\\(") > 1) {
                                    //190路(北线)(独墅湖邻里中心北--独墅湖邻里中心首末站) 处理这种情况
                                    mNearByLineLists.add(lineItems.get(k).getBusLineName().substring(0, lineItems.get(k).getBusLineName().indexOf(")") + 1));
                                } else {
                                    mNearByLineLists.add(lineItems.get(k).getBusLineName().substring(0, lineItems.get(k).getBusLineName().indexOf("(")));
                                }


                                //KLog.e("mary", "BusLineName " + lineItems.get(k).getBusLineName()+" i "+i);
                            }
                        }
                        //设置附近的公交路线adapter
                        setmNearBusLineAdapter();

                    } else {
                        ToastUtils.showToast(SetEndStationActivity.this, getString(R.string.no_result));
                    }
                } else {
                    ToastUtils.showToast(SetEndStationActivity.this, rCode + "");
                }
            }
        });// 设置查询结果的监听
        busStationSearch.searchBusStationAsyn();
    }

    //通过相关文本搜索公交站台
    private void getBusStation(String name) {
        BusStationQuery busStationQuery = new BusStationQuery(name, "0512");
        BusStationSearch busStationSearch = new BusStationSearch(this, busStationQuery);
        busStationSearch.setOnBusStationSearchListener(new BusStationSearch.OnBusStationSearchListener() {
            @Override
            public void onBusStationSearched(BusStationResult result, int rCode) {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getPageCount() > 0 && result.getBusStations() != null && result.getBusStations().size() > 0) {

                        ArrayList<BusStationItem> item = (ArrayList<BusStationItem>) result.getBusStations();
                        for (int i = 0; i < item.size(); i++) {
                            mSearchBusStation.add(item.get(i));
                        }
                        //设置搜索结果listview
                        setSearchListView();

                    } else {
                        ToastUtils.showToast(SetEndStationActivity.this, getString(R.string.no_result));
                    }
                } else {
                    ToastUtils.showToast(SetEndStationActivity.this, rCode + "");
                }
            }
        });// 设置查询结果的监听
        busStationSearch.searchBusStationAsyn();


    }

    private void setSearchListView() {
        mLayoutMain.setVisibility(View.GONE);
        mSearchListView.setVisibility(View.VISIBLE);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mSearchBusStation.size(); i++) {
            list.add(mSearchBusStation.get(i).getBusStationName());
        }
        mSearchListView.setAdapter(new ArrayAdapter<>(SetEndStationActivity.this, R.layout.textview_item, list));

        mSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLayoutMain.setVisibility(View.VISIBLE);
                mSearchListView.setVisibility(View.GONE);
                mSearchText.setText(mSearchBusStation.get(position).getBusStationName());
                //从搜索到的结果选择

                //保存到sp
                StationEntity stationEntity = new StationEntity();
                stationEntity.busStationLatitude = mSearchBusStation.get(position).getLatLonPoint().getLatitude();
                stationEntity.busStationLongitude = mSearchBusStation.get(position).getLatLonPoint().getLongitude();
                stationEntity.adCode = mSearchBusStation.get(position).getAdCode();
                stationEntity.busStationId = mSearchBusStation.get(position).getBusStationId();
                stationEntity.busStationName = mSearchBusStation.get(position).getBusStationName();
                stationEntity.cityCode = mSearchBusStation.get(position).getCityCode();

                saveSearchHistory(stationEntity);

                //回传到乘客或者车主界面
                goBack(stationEntity.busStationName,
                        stationEntity.busStationLatitude, stationEntity.busStationLongitude);

            }
        });
    }

    private void initData() {
        mListData = new ArrayList<>();
        String str = PreferenceUtils.getString(AppConstants.SEARCH_HISTORY);
        if (!TextUtils.isEmpty(str)) {
            mListData = GsonSerializerHelper.getInstance().convertJsaonStrToStationList(str);
            removeMuti(mListData);
            mAdapter = new Adapter();
            mAdapter.setHistoryList(mListData);
            mSearchHistoryView.setAdapter(mAdapter);
        }

        mSearchHistoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mAdapter.getCount() - 1) {
                    //从搜索历史选择
                    PreferenceUtils.putString(AppConstants.SEARCH_HISTORY, "");
                    mListData.clear();
                    mAdapter.setHistoryList(mListData);
                    mAdapter.notifyDataSetChanged();
                } else {
                    goBack(mListData.get(position).busStationName, mListData.get(position).busStationLatitude, mListData.get(position).busStationLongitude);
                }
            }
        });

    }

    private void initNearBus() {
        //根据当前位置，获取附近的公交路线和站台信息
        mCustomDialog = CustomDialogUtils.showWaitDialog(SetEndStationActivity.this, "正在获取附近的公交站台信息...", false, true);

        currentLongitude = AppApplication.getInstance().getCurrentLongitude();
        currentLatitude = AppApplication.getInstance().getCurrentLatitude();

        getBusStationByCurrentPosition(currentLatitude, currentLongitude);

    }

    private void setmNearBusLineAdapter() {
        mNearByLineLists = StationUtil.removeDuplicate(mNearByLineLists);
        mNearBusLine.setAdapter(new ArrayAdapter<>(SetEndStationActivity.this, R.layout.textview_item, mNearByLineLists));
        mNearBusLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ARouter.getInstance().build(AppConstants.AROUTER_BUS_LINE)
                        .withString(AppConstants.MAP_TITLE, mNearByLineLists.get(position))
                        .withTransition(R.anim.view_enter_from_right, R.anim.view_exit_to_left)
                        .navigation(SetEndStationActivity.this, AppConstants.BUS_LINE_STATION_REQUEST);
            }
        });

        CustomDialogUtils.closeDialog(mCustomDialog);
    }

    private void setmNearBusStopAdapter() {
        List<String> busStationNameLists = new ArrayList<>();
        if (mNearbyStationLists.size() > 0) {
            for (int i = 0; i < mNearbyStationLists.size(); i++) {
                busStationNameLists.add(mNearbyStationLists.get(i).busStationName.substring(0, mNearbyStationLists.get(i).busStationName.indexOf("(")));
            }
        }

        mNearBusStop.setAdapter(new ArrayAdapter<>(this, R.layout.textview_item, busStationNameLists));
        mNearBusStop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //从附近的公交站台选择

                //保存到sp
                saveSearchHistory(mNearbyStationLists.get(position));
                //回传到乘客或者车主界面
                goBack(mNearbyStationLists.get(position).busStationName.substring(0, mNearbyStationLists.get(position).busStationName.indexOf("(")),
                        mNearbyStationLists.get(position).busStationLatitude, mNearbyStationLists.get(position).busStationLongitude);

            }
        });
    }


    @Override
    protected void dealClickAction(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.view_enter_from_left, R.anim.view_exit_to_right);

                break;
            case R.id.bus_station:
                Intent i = new Intent(SetEndStationActivity.this, MapActivity.class);
                i.putExtra(MapActivity.REQUEST_DRIVER, true);
                startActivityForResult(i, AppConstants.MAP_REQUEST);

                break;
            case R.id.select_from_map:
                Intent intent = new Intent(SetEndStationActivity.this, MapActivity.class);
                intent.putExtra(MapActivity.REQUEST_DRIVER, true);
                startActivityForResult(intent, AppConstants.MAP_REQUEST);

                break;
        }
    }


    @Override
    public SetTripPresenter createPresenter() {
        return new SetTripPresenter();
    }

    class Adapter extends BaseAdapter {

        private List<StationEntity> historyList;

        public Adapter() {
            historyList = new ArrayList<>();
        }

        public void setHistoryList(List<StationEntity> historyList) {
            this.historyList = historyList;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final Adapter.ViewHolder holder;

            if (convertView == null) {
                holder = new Adapter.ViewHolder();
                convertView = LayoutInflater.from(SetEndStationActivity.this)
                        .inflate(R.layout.pass_set_station_item, null);

                holder.endStop = (TextView) convertView.findViewById(R.id.bus_stop);
                holder.kilometre = (TextView) convertView.findViewById(R.id.kilometre);
                holder.busLine = (TextView) convertView.findViewById(R.id.bus_line);
                holder.clearHistory = (TextView) convertView.findViewById(R.id.clear_history);
                holder.layout = convertView.findViewById(R.id.layout);
                holder.line = convertView.findViewById(R.id.line);

                convertView.setTag(holder);
            } else {
                holder = (Adapter.ViewHolder) convertView.getTag();
            }

            if (position == historyList.size()) {
                holder.layout.setVisibility(View.GONE);
                holder.clearHistory.setVisibility(View.VISIBLE);
                holder.line.setVisibility(View.GONE);

            } else if (position < historyList.size()) {
                holder.layout.setVisibility(View.VISIBLE);
                holder.clearHistory.setVisibility(View.GONE);
                holder.line.setVisibility(View.VISIBLE);
                holder.endStop.setText(historyList.get(position).busStationName.replace(AppConstants.ROUTES_STATION_REPLACE_VALUE, ""));
                //holder.kilometre.setText(historyList.get(position).getKilometre());
                //holder.busLine.setTag(historyList.get(position));

               /* String[] array = historyList.get(position).getBusLine();
                SpannableStringBuilder multiWord = new SpannableStringBuilder();
                for (int i = 0; i < array.length; i++) {
                    SpannableString spannableString = new SpannableString(array[i] + "  ");
                    BackgroundColorSpan colorSpan = new BackgroundColorSpan(getResources().getColor(R.color.bg_color));
                    ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(getResources().getColor(R.color.white));
                    spannableString.setSpan(colorSpan1, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(colorSpan, 0, spannableString.length() - 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    multiWord.append(spannableString);

                }
                holder.busLine.setText(multiWord);*/


            }

            return convertView;
        }

        @Override
        public int getCount() {
            if (historyList.size() > 0) {
                return historyList.size() + 1;
            } else {
                return 0;
            }

        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public final class ViewHolder {
            public TextView endStop;
            public TextView kilometre;
            public TextView busLine;
            public TextView clearHistory;
            public View layout, line;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.MAP_REQUEST && data != null) {
                //从地图选择,目前未保存到搜索历史中

                //回传到乘客或者车主界面
                StationInfo stationInfo = (StationInfo) data.getSerializableExtra(MapActivity.REQUEST_STATION);
                mSearchText.setText(stationInfo.title);
                PreferenceUtils.putString(AppConstants.END_STATION, stationInfo.title);

                Intent intent = new Intent();
                intent.putExtra(MapActivity.REQUEST_STATION, stationInfo);
                setResult(RESULT_OK, intent);
                finish();
            } else if (requestCode == AppConstants.BUS_LINE_STATION_REQUEST && data != null) {
                //选择公交线路，选择某一站台
                StationEntity stationEntity = (StationEntity) data.getSerializableExtra(BusLineActivity.REQUEST_STATION_LINE);

                //保存选择记录
                saveSearchHistory(stationEntity);

                //回传到乘客或者车主界面
                goBack(stationEntity.busStationName, stationEntity.busStationLatitude, stationEntity.busStationLongitude);


            }

        }
    }

    private void saveSearchHistory(StationEntity stationEntity) {
        String str = PreferenceUtils.getString(AppConstants.SEARCH_HISTORY);
        List<StationEntity> list = new ArrayList<>();
        if (!TextUtils.isEmpty(str)) {
            list = GsonSerializerHelper.getInstance().convertJsaonStrToStationList(str);
        }
        list.add(stationEntity);
        String stationJsonStr = GsonSerializerHelper.getInstance().convertStationListToJsaonStr(list);
        PreferenceUtils.putString(AppConstants.SEARCH_HISTORY, stationJsonStr);

    }


    private void goBack(String title, double lat, double lon) {
        StationInfo stationInfo = new StationInfo();
        stationInfo.title = title;
        stationInfo.lat = lat;
        stationInfo.lon = lon;

        mSearchText.setText(stationInfo.title);

        Intent intent = new Intent();
        intent.putExtra(MapActivity.REQUEST_STATION, stationInfo);
        setResult(RESULT_OK, intent);
        finish();
    }


    /**
     * 根据当前的定位信息, 获取周边的公交站台信息
     *
     * @param lat
     * @param lon
     */
    private void getBusStationByCurrentPosition(double lat, double lon) {
        PoiSearch.Query query = new PoiSearch.Query("公交站", "", "0512");
        query.setPageSize(15);//设置每页最多返回多少条poi item
        query.setPageNum(1);//设置查询页码

        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                StationEntity stationEntity = null;
                for (int j = 0; j < poiResult.getPois().size(); j++) {
                    stationEntity = new StationEntity();
                    stationEntity.busStationLatitude = poiResult.getPois().get(j).getLatLonPoint().getLatitude();
                    stationEntity.busStationLongitude = poiResult.getPois().get(j).getLatLonPoint().getLongitude();
                    stationEntity.adCode = poiResult.getPois().get(j).getAdCode();
                    stationEntity.busStationId = poiResult.getPois().get(j).getPoiId();
                    stationEntity.busStationName = poiResult.getPois().get(j).getTitle();
                    stationEntity.cityCode = poiResult.getPois().get(j).getCityCode();
                    stationEntity.distance = poiResult.getPois().get(j).getDistance();

                    mNearbyStationLists.add(stationEntity);
                }

                //对Lists进行排序
                Collections.sort(mNearbyStationLists, new Comparator<StationEntity>() {
                    @Override
                    public int compare(StationEntity o1, StationEntity o2) {
                        int i = o1.distance - o2.distance;

                        return i;
                    }
                });

                List<String> busStationNameLists = new ArrayList<>();
                if (mNearbyStationLists.size() > 0) {
                    for (int j = 0; j < mNearbyStationLists.size(); j++) {
                        busStationNameLists.add(mNearbyStationLists.get(j).busStationName.replace(AppConstants.ROUTES_STATION_REPLACE_VALUE, ""));
                    }
                }

                mNearBusStop.setAdapter(new ArrayAdapter<>(SetEndStationActivity.this, R.layout.textview_item, busStationNameLists));
                mNearBusStop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });

                getBusLine(busStationNameLists.get(0));

                setmNearBusStopAdapter();
                //CustomDialogUtils.closeDialog(mCustomDialog);
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
            }
        });
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lon), 1000));//设置周边搜索的中心点以及半径
        poiSearch.searchPOIAsyn();
    }

    private void removeMuti(List<StationEntity> list) {
        for (int i = 0; i < list.size(); i++)  //外循环是循环的次数
        {
            for (int j = list.size() - 1; j > i; j--)  //内循环是 外循环一次比较的次数
            {
                if (list.get(i).busStationName.replace(AppConstants.ROUTES_STATION_REPLACE_VALUE, "").equals(list.get(j).busStationName.replace(AppConstants.ROUTES_STATION_REPLACE_VALUE, ""))) {
                    list.remove(j);
                }
            }
        }
    }
}
