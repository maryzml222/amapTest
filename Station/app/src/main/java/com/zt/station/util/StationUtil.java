package com.zt.station.util;

import android.content.Context;

import com.example.mylibrary.domain.usecase.UseCase;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import rx.Subscription;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/7/19 23:50
 * version: v1.0.0
 * description:
 */

public class StationUtil {
    Context mContext;

    public StationUtil(Context context) {
        this.mContext = context;
    }

    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean checkNumber(String str) throws PatternSyntaxException {
        String regExp = "^\\d{4}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static void unsubscribeSubscribe(Subscription subscribe) {
        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }

    public static void unsubscribeUseCase(UseCase useCase) {
        if (useCase != null) {
            useCase.unsubscribe();
        }
    }

    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /**
     * 判断一个字符串是否含有数字
     *
     * @param content
     * @return
     */
    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }

        return flag;
    }

    /**
     *
     * @param str
     * @param patternStr
     * @return
     */
    public static int countInnerStr(final String str, final String patternStr) {
        int count = 0;
        final Pattern r = Pattern.compile(patternStr);
        final Matcher m = r.matcher(str);
        while (m.find()) {
            count++;
        }

        return count;
    }

    /**
     * 距离单位转换
     *
     * @param meterDistance
     * @return
     */
    public static double convertDistance(float meterDistance) {
        double kilometreDistance = Math.round(meterDistance/100d)/10d;

        return kilometreDistance;
    }

    /**
     * 时间转换
     *
     * @param second
     * @return
     */
    public static String convertSecondsTime(long second) {
        long hour = 0;
        long minutes = 0;
        long seconds = 0;

        seconds = second % 60;
        second = second / 60;
        minutes = second % 60;
        hour = second / 60;

        if(hour == 0) {
            if(minutes == 0) {
                return seconds + "秒";
            }else {
                return minutes + "分" + seconds + "秒";
            }
        } else {
            return hour + "时" + minutes + "分" + seconds + "秒";
        }
    }
}
