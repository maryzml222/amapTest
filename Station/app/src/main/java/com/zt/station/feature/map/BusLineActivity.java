package com.zt.station.feature.map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.core.AMapException;
import com.example.mylibrary.component.utils.ToastUtils;
import com.example.mylibrary.domain.model.response.StationEntity;
import com.zt.station.AppConstants;
import com.zt.station.R;
import com.zt.station.base.BaseActivity;
import com.zt.station.util.CustomDialogUtils;
import com.zt.station.util.StationUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

@Route(path = AppConstants.AROUTER_BUS_LINE)
public class BusLineActivity extends BaseActivity {
    @Autowired(name = AppConstants.MAP_TITLE)
    String mMapTitle;
    @Bind(R.id.bus_stop_lv)
    ListView mBusStopLV;
    @Bind(R.id.change_direction_iv)
    ImageView mChangeDirectionIV;
    @Bind(R.id.start_stop_tv)
    TextView mStartStopTV;
    @Bind(R.id.end_stop_tv)
    TextView mEndStopTV;
    @Bind(R.id.total_kilometer)
    TextView mTotalKilometerTV;

    Adapter mAdapter;
    List<String> mList;
    List<BusStationItem> mBusStations;
    private Dialog mCustomDialog;
    private List<BusLineItem> lineItems = null;// 公交线路搜索返回的busline
    public static final String REQUEST_STATION_LINE = "station";
    @Override
    protected int provideLayoutResourceID() {
        return R.layout.activity_bus_line;
    }

    @Override
    protected void initializeViewsAndData(Bundle savedInstanceState) {
        provideToolbar();
        setTitle(mMapTitle);
        getToolbarHelper().enableBack(this);
        setSomeOnClickListeners(mChangeDirectionIV);
        searchLine();
    }

    public void searchLine() {
        mCustomDialog = CustomDialogUtils.showWaitDialog(BusLineActivity.this, "loading...", false, true);
        mList = new ArrayList<>();
        int currentpage = 0;// 第一页默认从0开始
        final BusLineQuery busLineQuery = new BusLineQuery(mMapTitle, BusLineQuery.SearchType.BY_LINE_NAME,
                "0512");// 第一个参数表示公交线路名，第二个参数表示公交线路查询，第三个参数表示所在城市名或者城市区号
        busLineQuery.setPageSize(10);// 设置每页返回多少条数据
        busLineQuery.setPageNumber(currentpage);// 设置查询第几页，第一页从0开始算起
        BusLineSearch busLineSearch = new BusLineSearch(this, busLineQuery);// 设置条件
        busLineSearch.setOnBusLineSearchListener(new BusLineSearch.OnBusLineSearchListener() {
            @Override
            public void onBusLineSearched(BusLineResult result, int rCode) {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getQuery() != null
                            && result.getQuery().equals(busLineQuery)) {
                        if (result.getQuery().getCategory() == BusLineQuery.SearchType.BY_LINE_NAME) {
                            if (result.getPageCount() > 0
                                    && result.getBusLines() != null
                                    && result.getBusLines().size() > 0) {
                                lineItems = result.getBusLines();
                                String busname=lineItems.get(0).getBusLineName();
                                if (StationUtil.countInnerStr(busname,"\\(")>1){
                                    //190路(北线)(独墅湖邻里中心北--独墅湖邻里中心首末站) 处理这种情况
                                    mStartStopTV.setText(busname.substring(busname.lastIndexOf("(")+1,busname.indexOf("-")));
                                    mEndStopTV.setText(busname.substring(busname.lastIndexOf("-")+1,busname.lastIndexOf(")")));
                                }else {
                                    mStartStopTV.setText(busname.substring(busname.indexOf("(")+1,busname.indexOf("-")));
                                    mEndStopTV.setText(busname.substring(busname.lastIndexOf("-")+1,busname.lastIndexOf(")")));
                                }

                                getBus();

                            }
                        }
                    } else {
                        CustomDialogUtils.closeDialog(mCustomDialog);
                        ToastUtils.showToast(BusLineActivity.this, getString(R.string.no_result));
                    }
                } else {
                    CustomDialogUtils.closeDialog(mCustomDialog);
                    ToastUtils.showToast(BusLineActivity.this, rCode + "");
                }
            }
        });// 设置查询结果的监听
        busLineSearch.searchBusLineAsyn();// 异步查询公交线路名称

    }

    private void getBus() {
        String lineId = lineItems.get(0).getBusLineId();// 得到当前点击item公交线路id
        final BusLineQuery busLineQuery = new BusLineQuery(lineId, BusLineQuery.SearchType.BY_LINE_ID,
                "0512");// 第一个参数表示公交线路id，第二个参数表示公交线路id查询，第三个参数表示所在城市名或者城市区号
        BusLineSearch busLineSearch = new BusLineSearch(
                BusLineActivity.this, busLineQuery);
        busLineSearch.setOnBusLineSearchListener(new BusLineSearch.OnBusLineSearchListener() {
            @Override
            public void onBusLineSearched(BusLineResult result, int rCode) {
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getQuery() != null && result.getQuery().equals(busLineQuery)) {
                        if (result.getQuery().getCategory() == BusLineQuery.SearchType.BY_LINE_ID) {
                            List<BusLineItem> lineItems = result.getBusLines();
                            mBusStations =lineItems.get(0).getBusStations();
                            mTotalKilometerTV.setText(new DecimalFormat(".00").format(lineItems.get(0).getDistance())+" 公里");
                            mAdapter = new Adapter();
                            mAdapter.setHistoryList(mBusStations);
                            mBusStopLV.setAdapter(mAdapter);

                            CustomDialogUtils.closeDialog(mCustomDialog);
                        }
                    } else {
                        CustomDialogUtils.closeDialog(mCustomDialog);
                        ToastUtils.showToast(BusLineActivity.this, getString(R.string.no_result));
                    }
                } else {
                    CustomDialogUtils.closeDialog(mCustomDialog);
                    ToastUtils.showToast(BusLineActivity.this, rCode + "");
                }
            }
        });
        busLineSearch.searchBusLineAsyn();// 异步查询公交线路id

        mBusStopLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StationEntity stationEntity = new StationEntity();
                stationEntity.busStationLatitude = mBusStations.get(position).getLatLonPoint().getLatitude();
                stationEntity.busStationLongitude = mBusStations.get(position).getLatLonPoint().getLongitude();
                stationEntity.adCode = mBusStations.get(position).getAdCode();
                stationEntity.busStationId = mBusStations.get(position).getBusStationId();
                stationEntity.busStationName = mBusStations.get(position).getBusStationName();
                stationEntity.cityCode = mBusStations.get(position).getCityCode();

                Intent intent = new Intent();

                intent.putExtra(REQUEST_STATION_LINE, stationEntity);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void dealClickAction(View v) {
        switch (v.getId()) {
            case R.id.change_direction_iv:
                String startStop = mStartStopTV.getText().toString();
                String endStop = mEndStopTV.getText().toString();
                mStartStopTV.setText(endStop);
                mEndStopTV.setText(startStop);
                Collections.reverse(mBusStations);
                mAdapter.setHistoryList(mBusStations);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    class Adapter extends BaseAdapter {

        private List<BusStationItem> historyList;

        public Adapter() {
            historyList = new ArrayList<>();
        }

        public void setHistoryList(List<BusStationItem> historyList) {
            this.historyList = historyList;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final Adapter.ViewHolder holder;

            if (convertView == null) {
                holder = new Adapter.ViewHolder();
                convertView = LayoutInflater.from(BusLineActivity.this)
                        .inflate(R.layout.bus_line_item, null);

                holder.endStop = (TextView) convertView.findViewById(R.id.bus_stop);

                convertView.setTag(holder);
            } else {
                holder = (Adapter.ViewHolder) convertView.getTag();
            }


            holder.endStop.setText(historyList.get(position).getBusStationName());

            return convertView;
        }

        @Override
        public int getCount() {
            return historyList.size();
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
        }
    }

}
