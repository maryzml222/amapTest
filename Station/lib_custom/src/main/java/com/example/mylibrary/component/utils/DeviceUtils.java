/**
 * Copyright (c) 2014 CoderKiss
 * <p>
 * CoderKiss[AT]gmail.com
 */

package com.example.mylibrary.component.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

//hardware related functions
public class DeviceUtils {
    public static final String TAG = "DeviceUtils";

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static String getBuildInfo() {
        StringBuilder sb = new StringBuilder();
        // alpha sort
        sb.append("board: " + Build.BOARD).append("\nbrand: " + Build.BRAND)
                .append("\ncpu_abi: " + Build.CPU_ABI)
                .append("\ncpu_abi2: " + Build.CPU_ABI2)
                .append("\ndevice: " + Build.DEVICE)
                .append("\ndisplay: " + Build.DISPLAY)
                .append("\nfingerprint: " + Build.FINGERPRINT)
                .append("\nhardware: " + Build.HARDWARE)
                .append("\nid: " + Build.ID)
                .append("\nmanufacture: " + Build.MANUFACTURER)
                .append("\nmodel: " + Build.MODEL)
                .append("\nproduct: " + Build.PRODUCT)
                .append("\nradio: " + Build.RADIO)
                .append("\nsdk_int: " + Build.VERSION.SDK_INT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sb.append("\nserial: " + Build.SERIAL);
        }
        sb.append("\ntype: " + Build.TYPE).append("\ntags: " + Build.TAGS);

        return sb.toString();
    }

    public static String getProductInfo() {
        String brand = Build.BRAND;
        String model = Build.MODEL;
        String manufacture = Build.MANUFACTURER;
        String finalInfo = brand + " " + model + "/" + manufacture;
        return finalInfo;
    }

    public static final Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // since SDK_INT = 1;
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        // includes window decorations (status bar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth")
                        .invoke(display);
                heightPixels = (Integer) Display.class
                        .getMethod("getRawHeight").invoke(display);
            } catch (Exception ignored) {
            }
        }
        // includes window decorations (status bar bar/menu bar)
        else if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(
                        display, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {
            }
        }
        return new Point(widthPixels, heightPixels);
    }

    public static final float getScreenDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float screenDensity = dm.density;
        return screenDensity;
    }

    public static int getScreenDensityDpi(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int screenDensityDpi = dm.densityDpi;
        return screenDensityDpi;
    }

    public static final String getDeviceId(Context context) {
        String deviceIMEI = null;
        try {
            TelephonyManager teleManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceIMEI = teleManager.getDeviceId();
        } catch (Exception e) {

        }
        return deviceIMEI;
    }

    /**
     * UUID
     *
     * @param context
     * @return
     */
    public static final String getUUID(Context context) {
        String uuid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (StringUtils.isEmpty(uuid) || uuid.length() < 15) {
            SecureRandom random = new SecureRandom();
            uuid = new BigInteger(64, random).toString(16);
        }

        if (StringUtils.isEmpty(uuid)) {
            uuid = "";
        }

        return uuid;
    }

    public static double getScreenInches(Context context) {
        double screenInches = -1;
        try {
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Point point = getScreenSize(context);
            double width = Math.pow(point.x / dm.xdpi, 2);
            double height = Math.pow(point.y / dm.ydpi, 2);
            screenInches = Math.sqrt(width + height);
        } catch (Exception e) {
        }
        return screenInches;
    }

    public static int dp2px(Context context, int dip) {
        Resources resources = context.getResources();
        int px = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, resources.getDisplayMetrics()));
        return px;
    }

    public static int px2dp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        int px = Math.round(sp * scale);
        return px;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static final boolean hasFrontCamera() {
        int number = Camera.getNumberOfCameras();
        for (int index = 0; index < number; index++) {
            CameraInfo ci = new CameraInfo();
            Camera.getCameraInfo(index, ci);
            if (ci.facing == CameraInfo.CAMERA_FACING_FRONT) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static final boolean hasBackCamera() {
        int number = Camera.getNumberOfCameras();
        for (int index = 0; index < number; index++) {
            CameraInfo ci = new CameraInfo();
            Camera.getCameraInfo(index, ci);
            if (ci.facing == CameraInfo.CAMERA_FACING_BACK) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSensor(Context context, int type) {
        SensorManager manager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        return manager.getDefaultSensor(type) != null;
    }

    public static final long getTotalMemory() {
        String memInfoPath = "/proc/meminfo";
        String str2;
        long initial_memory = 0;
        try {
            FileReader fr = new FileReader(memInfoPath);
            BufferedReader bf = new BufferedReader(fr, 8192);
            str2 = bf.readLine();// total memory size
            String[] as = str2.split("\\s+");
            initial_memory = Integer.valueOf(as[1]).intValue() * 1024;
            bf.close();
            return initial_memory;
        } catch (IOException e) {
            return -1;
        }
    }


    /**
     * 检查网络是否可用
     *
     * @param ctx
     * @return
     */
    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String getImei(Context context,String service){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(service);
        return tm.getDeviceId();
    }

}
