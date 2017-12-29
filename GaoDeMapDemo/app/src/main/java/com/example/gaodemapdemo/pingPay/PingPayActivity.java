package com.example.gaodemapdemo.pingPay;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.gaodemapdemo.R;

public class PingPayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_pay);
    }
    public void btnClick(View v){
        Class clz = null;
        switch (v.getId()) {
            case R.id.client_sdk:
                clz = ClientSDKActivity.class;
                break;
            case R.id.one_sdk:
                clz = OneSDKActivity.class;
                break;
            default:
                break;
        }
        Intent intent = new Intent(PingPayActivity.this, clz);
        startActivity(intent);
    }
}
