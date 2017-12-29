package com.example.gaodemapdemo.mobShare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gaodemapdemo.R;

import cn.sharesdk.dingding.utils.DingdingHandlerActivity;

public class DDShareActivity extends DingdingHandlerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddshare);
    }
}
