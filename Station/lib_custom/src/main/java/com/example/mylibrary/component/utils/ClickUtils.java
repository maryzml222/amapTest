package com.example.mylibrary.component.utils;

import android.view.View;

import com.example.mylibrary.R;


public class ClickUtils {
    private static long lastClickTime = 0L;

    /**
     * 判断是否快速点击
     * @param duration
     * @return
     */
    public static boolean isFastRepeatClick(long duration) {
        long time = System.currentTimeMillis();
        long deltaTime = time - lastClickTime;
        if (0 < deltaTime && deltaTime < duration) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 设置允许快速点击
     *
     * @param v
     */
    public static void setSupportFastClick(View v){
        if (v!=null){
            v.setTag(R.id.is_support_fast_click,true);
        }
    }

    /**
     * 当前控件是否支持用户快速点击
     * @param v
     * @return
     */
    public static boolean getSupportFastClick(View v){
        boolean isSupportFastClick = false;
        try{
            Object temp = v.getTag(R.id.is_support_fast_click);
            if (null!=temp){
                isSupportFastClick = (boolean) temp;
            }
        }catch (Exception e){

        }
        return isSupportFastClick;
    }


    /**
     * 封装给多个控件设置点击事件
     *
     * @param listener
     * @param views
     */
    public static void setSomeOnClickListeners(View.OnClickListener listener,View... views){
        if (views==null){
            return;
        }
        for (View view : views){
            if (view!=null){
                view.setOnClickListener(listener);
            }
        }
    }
}
