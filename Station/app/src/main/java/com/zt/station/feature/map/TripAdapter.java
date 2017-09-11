package com.zt.station.feature.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mylibrary.component.utils.NetImageLoader;
import com.example.mylibrary.domain.model.response.passMatchedCar.CarEntity;
import com.zt.station.AppApplication;
import com.zt.station.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Mary} on 2017/7/31.
 */

public class TripAdapter extends BaseAdapter {
    private List<CarEntity> historyList;
    private Activity activity;
    private Bitmap defaultBitmap;
    private ListView listView;

    public TripAdapter(Activity activity, ListView listView) {
        historyList = new ArrayList<>();
        this.activity = activity;
        this.listView = listView;
        defaultBitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ff);
    }

    public void setHistoryList(List<CarEntity> historyList) {
        this.historyList = historyList;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final TripAdapter.ViewHolder holder;

        if (convertView == null) {
            holder = new TripAdapter.ViewHolder();
            convertView = LayoutInflater.from(activity.getApplicationContext())
                    .inflate(R.layout.pass_has_pay, null);

            holder.name = (TextView) convertView.findViewById(R.id.username);
            holder.company = (TextView) convertView.findViewById(R.id.company);
            holder.carNumber = (TextView) convertView.findViewById(R.id.license_plate_text);
            holder.carBrand = (TextView) convertView.findViewById(R.id.model_text);
            holder.distinct = (TextView) convertView.findViewById(R.id.distant);
            holder.reachTime = (TextView) convertView.findViewById(R.id.coming_time);
            holder.layout = convertView.findViewById(R.id.line);
            holder.head = (ImageView) convertView.findViewById(R.id.head);

            convertView.setTag(holder);
        } else {
            holder = (TripAdapter.ViewHolder) convertView.getTag();
        }

        holder.name.setText(historyList.get(position).user.lastName + historyList.get(position).user.firstName);
        holder.company.setText(historyList.get(position).user.companyShortName);
        holder.carNumber.setText(historyList.get(position).licensePlateNumber);
        holder.carBrand.setText(historyList.get(position).brand);

        if (historyList.get(position).isInComing) {
            holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.yellow));
            holder.distinct.setTextColor(activity.getResources().getColor(R.color.yellow));
            holder.reachTime.setVisibility(View.GONE);
            holder.distinct.setText(activity.getString(R.string.coming_stop));
        } else{
            holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.white));
            holder.distinct.setTextColor(activity.getResources().getColor(R.color.grey));
            holder.reachTime.setTextColor(activity.getResources().getColor(R.color.grey));
            holder.reachTime.setVisibility(View.VISIBLE);

            holder.distinct.setText("离站台" + historyList.get(position).distance + "米");
            holder.reachTime.setText("预计" + historyList.get(position).arrivalTime + "分钟到达");
        }

        //String url = historyList.get(position).getHeadUrl();
        String url = "http://s3.lvjs.com.cn/trip/original/20140818131532_2090993967.jpg";
        holder.head.setTag(url);
        holder.head.setImageBitmap(defaultBitmap);
        if (!NetImageLoader.isNullOrEmpty(url)) {
            Bitmap headBitmap = NetImageLoader.getInstance()
                    .loadImage(url, new NetImageLoader.NetImageCallBack() {
                        @Override
                        public void onImageLoader(Bitmap bitmap, String path) {
                            ImageView head = (ImageView) listView.findViewWithTag(path);
                            if (bitmap != null && head != null) {
                                head.setImageBitmap(bitmap);
                            }
                        }
                    }, AppApplication.CACHE_FOLDER);
            if (headBitmap != null) {
                holder.head.setImageBitmap(headBitmap);
            } else {
                holder.head.setImageBitmap(defaultBitmap);
            }
        }

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
        public TextView name;
        public TextView company;
        public TextView carNumber;
        public TextView carBrand;
        public TextView reachTime;
        public TextView distinct;
        ImageView head;
        View layout;

    }
}
