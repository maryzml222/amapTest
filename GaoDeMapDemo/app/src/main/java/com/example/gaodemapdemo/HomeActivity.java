package com.example.gaodemapdemo;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.amap.api.maps.MapsInitializer;
import com.example.gaodemapdemo.aidlDemo.MaryAidlActivity;
import com.example.gaodemapdemo.animate.AnimateActivity;
import com.example.gaodemapdemo.dbUtil.UserUtils;
import com.example.gaodemapdemo.dbUtil.model.User;
import com.example.gaodemapdemo.html.HtmlActivity;
import com.example.gaodemapdemo.mobShare.MobShareActivity;
import com.example.gaodemapdemo.pingPay.PingPayActivity;
import com.example.gaodemapdemo.view.FeatureView;
import com.example.gaodemapdemo.zxing.CaptureActivity;

public class HomeActivity extends ListActivity {
    private static class DemoDetails {
        private final int titleId;
        private final int descriptionId;
        private final Class<? extends android.app.Activity> activityClass;

        public DemoDetails(int titleId, int descriptionId,
                           Class<? extends android.app.Activity> activityClass) {
            super();
            this.titleId = titleId;
            this.descriptionId = descriptionId;
            this.activityClass = activityClass;
        }
    }

    private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
        public CustomArrayAdapter(Context context, DemoDetails[] demos) {
            super(context, R.layout.feature, R.id.title, demos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FeatureView featureView;
            if (convertView instanceof FeatureView) {
                featureView = (FeatureView) convertView;
            } else {
                featureView = new FeatureView(getContext());
            }
            DemoDetails demo = getItem(position);
            featureView.setTitleId(demo.titleId, demo.activityClass != null);
            return featureView;
        }
    }

    private static final DemoDetails[] demos = new DemoDetails[]{
            new DemoDetails(R.string.amap, R.string.amap_description,
                    MainActivity.class),
            new DemoDetails(R.string.aidl, R.string.aidl_description,
                    MaryAidlActivity.class),
            new DemoDetails(R.string.zxing, R.string.zxing_description,
                    CaptureActivity.class),
            new DemoDetails(R.string.mob, R.string.mob_description,
                    MobShareActivity.class),
            new DemoDetails(R.string.ping, R.string.ping_description,
                    PingPayActivity.class),
            new DemoDetails(R.string.html, R.string.html_description,
                    HtmlActivity.class),
            new DemoDetails(R.string.animate_one, R.string.animate_one_description,
                    AnimateActivity.class),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("3D地图Demo" + MapsInitializer.getVersion());
        ListAdapter adapter = new CustomArrayAdapter(
                this.getApplicationContext(), demos);
        setListAdapter(adapter);
        //UserUtils.getInstance().insertUser(new User());
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
        if (demo.activityClass != null) {
            Log.i("MY", "demo!=null");
            startActivity(new Intent(this.getApplicationContext(),
                    demo.activityClass));
        }
    }
}
