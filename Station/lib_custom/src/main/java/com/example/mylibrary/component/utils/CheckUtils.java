package com.example.mylibrary.component.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public class CheckUtils {

    /**
     * 检测页面是否需要关闭
     *
     * @param subscription
     * @param subscriber
     * @param delay 间隔时间
     * @return
     */
    public static Subscription checkActivityNeedFinsh(Subscription subscription,  Subscriber subscriber, int delay) {
        if (subscription == null || subscription.isUnsubscribed()) {
            subscription = Observable.timer(delay, TimeUnit.SECONDS).subscribe(subscriber);
        }
        return subscription;
    }


    /**
     * 检测网络情况是否OK
     *
     * @param subscription
     * @param subscriber
     * @param delay 间隔时间
     * @return
     */
    public static Subscription checkNetwork(Subscription subscription,final Subscriber subscriber,int delay) {
        if (subscription == null || subscription.isUnsubscribed()) {
            subscription = Observable.interval(0, delay, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
        return subscription;
    }


    public static void cancelCheck(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

//    public interface TimeOutCallBack {
//        void dealSthWhenTimeOut();
//    }

//    /**
//     * 判断网络是否连接的dialog
//     */
//    public void initNetErrorDialog(Dialog netErrorDialog, final Activity mActivity) {
//        if (netErrorDialog == null) {
//            netErrorDialog = new Dialog(mActivity, R.style.alarm_alert);
//            View dialogView = LayoutInflater.from(mActivity.getApplicationContext()).inflate(R.layout.hint_dialog, null);
//            TextView hintTv = (TextView) dialogView.findViewById(R.id.hint_dialog_tv);
//            TextView trueTv = (TextView) dialogView.findViewById(R.id.hint_dialog_true_btn);
//            hintTv.setText("无可用网络，请设置网络连接！");
//
//            netErrorDialog.setCanceledOnTouchOutside(false);
//            //设置对话框在窗口中范围
//            netErrorDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            //设置对话框在窗口中显示的位置
//            netErrorDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
//
//            /*
//             * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
//             * 可以直接调用getWindow(),表示获得这个Activity的Window
//             * 对象,这样这可以以同样的方式改变这个Activity的属性.
//             */
//            Window dialogWindow = netErrorDialog.getWindow();
//            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//            dialogWindow.setContentView(dialogView);
//
//            /*
//             * lp.x与lp.y表示相对于原始位置的偏移.
//             * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
//             * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
//             * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
//             * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
//             * 当参数值包含Gravity.CENTER_HORIZONTAL时
//             * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
//             * 当参数值包含Gravity.CENTER_VERTICAL时
//             * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
//             * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
//             * Gravity.CENTER_VERTICAL.
//             *
//             * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
//             * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
//             * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
//             */
//            lp.x = 0; // 新位置X坐标（X开始位置）
//            lp.y = -20; // 新位置Y坐标（Y开始位置）   该Dialog头部出现有一个栏框（暂未消除）使弹框无法居中
//            lp.width = 660; // 宽度
//            lp.height = -2; // 高度
//            //lp.alpha = 0.7f; // 透明度  没有自定义布局背景也可以使用该方法设置背景
//            // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
//            dialogWindow.setAttributes(lp);
//            final Dialog finalDialog = netErrorDialog;
//            trueTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // 跳转到网络设置
//                    Intent intent = new Intent(mActivity, SetCommunicationActivity.class);
//                    mActivity.startActivity(intent);
//                    finalDialog.dismiss();
//                }
//            });
//        }
//    }

    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
