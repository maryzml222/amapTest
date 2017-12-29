package com.example.gaodemapdemo.aidlDemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by ${Mary} on 2017/12/18.
 */

public class AdditionService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return new IMyAidlInterface.Stub() {
            /*
             * Implement com.android.hellosumaidl.IAdditionService.add(int, int)
             */
            @Override
            public int add(int value1, int value2) throws RemoteException {
                intent.putExtra("result", value1 + value2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(getApplicationContext(), MaryAidlActivity.class);
                startActivity(intent);
                return value1 + value2;
            }

            @Override
            public void changeView(String str) throws RemoteException {
                intent.putExtra("string", str);
                intent.setClass(getApplicationContext(), MaryAidlActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}