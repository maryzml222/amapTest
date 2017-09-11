package com.example.mylibrary.component.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

public class ActivityUtils {

    /**
     * 判断某个界面是否在前台
     *
     * @param activity 当前Activity
     */
    public static boolean isForeground(Activity activity) {
        String className = activity.getComponentName().getClassName();
        if (TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) activity.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个服务是否可用
     *
     * @param context
     * @param serviceName
     * @return
     */
    public static boolean isServiceWork(Context context, String serviceName) {

        boolean isWork = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(40);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }

        for (int i = 0; i < runningServiceInfos.size(); i++) {
            String mName = runningServiceInfos.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public static void toast(Context ctx, String text) {
        Toast.makeText(ctx.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    public static void toast(Context ctx, int resID) {
        toast(ctx, ctx.getResources().getString(resID));
    }

}
