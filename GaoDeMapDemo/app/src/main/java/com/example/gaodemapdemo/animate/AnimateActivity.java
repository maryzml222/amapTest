package com.example.gaodemapdemo.animate;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.gaodemapdemo.R;

public class AnimateActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);
        imageView = (ImageView) findViewById(R.id.imageview);
        button = (Button) findViewById(R.id.btton);


        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
    }

    public void btnAlphaClick(View view) {
        /*
          // 以下参数是透明度动画特有的属性
    android:fromAlpha="1.0" // 动画开始时视图的透明度(取值范围: -1 ~ 1)
    android:toAlpha="0.0"// 动画结束时视图的透明度(取值范围: -1 ~ 1)
         */
        //imageView.animate().alpha((float) 0.5);
        //AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation.setDuration(5000);
        animation.setRepeatCount(-1);
        //animation.setRepeatMode(Animation.RESTART);
        imageView.startAnimation(animation);
    }

    public void btnTransitionClick(View view) {
        // 以下参数是4种动画效果的公共属性,即都有的属性
       /* android:duration="3000" // 动画持续时间（ms），必须设置，动画才有效果
        android:startOffset ="1000" // 动画延迟开始时间（ms）
        android:fillBefore = “true” // 动画播放完后，视图是否会停留在动画开始的状态，默认为true
        android:fillAfter = “false” // 动画播放完后，视图是否会停留在动画结束的状态，优先于fillBefore值，默认为false
        android:fillEnabled= “true” // 是否应用fillBefore值，对fillAfter值无影响，默认为true
        android:repeatMode= “restart” // 选择重复播放动画模式，restart代表正序重放，reverse代表倒序回放，默认为restart|
        android:repeatCount = “0” // 重放次数（所以动画的播放次数=重放次数+1），为infinite时无限重复
        android:interpolator = @[package:]anim/interpolator_resource // 插值器，即影响动画的播放速度,下面会详细讲
        // 以下参数是平移动画特有的属性
        android:fromXDelta="0" // 视图在水平方向x 移动的起始值
        android:toXDelta="500" // 视图在水平方向x 移动的结束值

        android:fromYDelta="0" // 视图在竖直方向y 移动的起始值
        android:toYDelta="500" // 视图在竖直方向y 移动的结束值*/


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        animation.setDuration(5000);
        animation.setRepeatCount(-1);
        imageView.startAnimation(animation);
    }

    public void btnScaleClick(View view) {
        /*
    // 以下参数是缩放动画特有的属性
    android:fromXScale="0.0"
    // 动画在水平方向X的起始缩放倍数
    // 0.0表示收缩到没有；1.0表示正常无伸缩
    // 值小于1.0表示收缩；值大于1.0表示放大
    android:toXScale="2"  //动画在水平方向X的结束缩放倍数
    android:fromYScale="0.0" //动画开始前在竖直方向Y的起始缩放倍数
    android:toYScale="2" //动画在竖直方向Y的结束缩放倍数
    android:pivotX="50%" // 缩放轴点的x坐标
    android:pivotY="50%" // 缩放轴点的y坐标
    // 轴点 = 视图缩放的中心点
    // pivotX pivotY,可取值为数字，百分比，或者百分比p
    // 设置为数字时（如50），轴点为View的左上角的原点在x方向和y方向加上50px的点。在Java代码里面设置这个参数的对应参数是Animation.ABSOLUTE。
    // 设置为百分比时（如50%），轴点为View的左上角的原点在x方向加上自身宽度50%和y方向自身高度50%的点。在Java代码里面设置这个参数的对应参数是Animation.RELATIVE_TO_SELF。
    // 设置为百分比p时（如50%p），轴点为View的左上角的原点在x方向加上父控件宽度50%和y方向父控件高度50%的点。在Java代码里面设置这个参数的对应参数是Animation.RELATIVE_TO_PARENT

    // 两个50%表示动画从自身中间开始，具体如下图


         */

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        animation.setDuration(5000);
        animation.setRepeatCount(-1);
        imageView.startAnimation(animation);
    }

    public void btnRoationClick(View view) {
        /*
        // 以下参数是旋转动画特有的属性
    android:duration="1000"
    android:fromDegrees="0" // 动画开始时 视图的旋转角度(正数 = 顺时针，负数 = 逆时针)
    android:toDegrees="270" // 动画结束时 视图的旋转角度(正数 = 顺时针，负数 = 逆时针)
    android:pivotX="50%" // 旋转轴点的x坐标
    android:pivotY="0" // 旋转轴点的y坐标
    // 轴点 = 视图缩放的中心点

// pivotX pivotY,可取值为数字，百分比，或者百分比p
    // 设置为数字时（如50），轴点为View的左上角的原点在x方向和y方向加上50px的点。在Java代码里面设置这个参数的对应参数是Animation.ABSOLUTE。
    // 设置为百分比时（如50%），轴点为View的左上角的原点在x方向加上自身宽度50%和y方向自身高度50%的点。在Java代码里面设置这个参数的对应参数是Animation.RELATIVE_TO_SELF。
    // 设置为百分比p时（如50%p），轴点为View的左上角的原点在x方向加上父控件宽度50%和y方向父控件高度50%的点。在Java代码里面设置这个参数的对应参数是Animation.RELATIVE_TO_PARENT
    // 两个50%表示动画从自身中间开始，具体如下图
         */
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        animation.setDuration(5000);
        animation.setRepeatCount(-1);
        imageView.startAnimation(animation);
    }

    public void btnSetClick(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_set);
        //animation.setDuration(5000);
        //animation.setRepeatCount(-1);
        imageView.startAnimation(animation);
    }

    public void btnPropertyScaleClick(View view) {
        imageView.animate().scaleX((float) 2.0);
        imageView.animate().scaleY((float) 1.0);
        imageView.animate().scaleXBy((float) 1.0);
        imageView.animate().scaleYBy((float) 2.0);

    }

    public void btnPropertyRoationClick(View view) {
        imageView.animate().rotation((float) 360.0);
    }

    public void btnPropertyTransitionClick(View view) {
        imageView.animate().translationX((float) 2.0);
    }

    public void btnPropertyAlphaClick(View view) {
        // imageView.animate().alpha((float) 1.0);
       /* // 载入XML动画
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.set_animation);
        // 设置动画对象
        animator.setTarget(imageView);
        // 启动动画
        animator.start();*/

        createPropertyByJava();
    }

    private void createPropertyByJava() {
        // 步骤1：设置属性数值的初始值 & 结束值
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(button.getLayoutParams().width, 500);
        // 初始值 = 当前按钮的宽度，此处在xml文件中设置为150
        // 结束值 = 500
        // ValueAnimator.ofInt()内置了整型估值器,直接采用默认的.不需要设置
        // 即默认设置了如何从初始值150 过渡到 结束值500

        // 步骤2：设置动画的播放各种属性
        valueAnimator.setDuration(2000);
        // 设置动画运行时长:1s

        // 步骤3：将属性数值手动赋值给对象的属性:此处是将 值 赋给 按钮的宽度
        // 设置更新监听器：即数值每次变化更新都会调用该方法
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int currentValue = (Integer) animator.getAnimatedValue();
                // 获得每次变化后的属性值
                Log.e("mary", currentValue + "");
                // 输出每次变化后的属性值进行查看

                button.getLayoutParams().width = currentValue;
                // 每次值变化时，将值手动赋值给对象的属性
                // 即将每次变化后的值 赋 给按钮的宽度，这样就实现了按钮宽度属性的动态变化

                // 步骤4：刷新视图，即重新绘制，从而实现动画效果
                button.requestLayout();

            }
        });

        valueAnimator.start();
        // 启动动画


    }

}
