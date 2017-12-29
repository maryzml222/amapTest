package com.example.gaodemapdemo.aidlDemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gaodemapdemo.R;

public class MaryAidlActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mary_aidl);
        TextView textView = (TextView) findViewById(R.id.result_a);
        textView.setText("fffffffffffff");

        if (getIntent() != null) {
            textView.setText(getIntent().getStringExtra("result"));
        } else {
            textView.setText("result");
        }
    }
}
