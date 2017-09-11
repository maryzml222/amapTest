package com.example.mylibrary.uiframwork.helpers;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mylibrary.R;


public class ToolbarHelper {

    protected Toolbar mToolbar = null;

    protected View mToolbarBack = null;

    protected ImageButton mToolbarLeftIb = null;
    protected ImageButton mToolbarRightIb = null;

    protected TextView mToolbarLeftTextBtn = null;
    protected TextView mToolbarRightTextBtn = null;

    protected TextView mToolbarTitle = null;

    public ToolbarHelper(Toolbar toolbar) {
        this.mToolbar = toolbar;
        if (this.mToolbar == null) {
            throw new IllegalStateException("Layout file is required to include a Toolbar with id: toolbar");
        }

        this.mToolbarTitle = (TextView) this.mToolbar.findViewById(R.id.toolbar_title);
    }

    public void enableBack(final Activity activity) {
       /* if (this.mToolbar != null) {
            this.mToolbarBack = this.mToolbar.findViewById(R.id.toolbar_back);
        }

        if (this.mToolbarBack != null) {
            this.mToolbarBack.setVisibility(View.VISIBLE);
            this.mToolbarBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onBackPressed();
                }
            });*/

        if (this.mToolbar != null) {
            this.mToolbarLeftTextBtn = (TextView) this.mToolbar.findViewById(R.id.toolbar_left_text_btn);
        }

        if (this.mToolbarLeftTextBtn != null) {
            this.mToolbarLeftTextBtn.setVisibility(View.VISIBLE);
            this.mToolbarLeftTextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onBackPressed();
                    activity.overridePendingTransition(R.anim.view_enter_from_left, R.anim.view_exit_to_right);
                }
            });


        }
    }

    public void enableTransparentToolbar() {
        if (this.mToolbar != null) {
            this.mToolbar.setBackgroundResource(R.color.transparent);
        }
    }

    public Toolbar getToolbar() {
        return this.mToolbar;
    }

    public TextView getToolbarTitle() {
        return this.mToolbarTitle;
    }

    public ImageButton getToolbarLeftIb() {
        return mToolbarLeftIb;
    }

    public ImageButton getToolbarRightIb() {
        return mToolbarRightIb;
    }

    public TextView getToolbarLeftTextBtn() {
        return mToolbarLeftTextBtn;
    }

    public TextView getToolbarRightTextBtn() {
        return mToolbarRightTextBtn;
    }

    public void initToolbarLeftIb(@DrawableRes int resId, View.OnClickListener clickListener) {
        if (this.mToolbar != null) {
            this.mToolbarLeftIb = (ImageButton) this.mToolbar.findViewById(R.id.toolbar_left_image_btn);
        }

        if (this.mToolbarLeftIb != null) {
            this.mToolbarLeftIb.setImageResource(resId);
            this.mToolbarLeftIb.setVisibility(View.VISIBLE);
            this.mToolbarLeftIb.setOnClickListener(clickListener);
        }
    }


    public void initToolbarRightIb(@DrawableRes int resId, View.OnClickListener clickListener) {
        if (this.mToolbar != null) {
            this.mToolbarRightIb = (ImageButton) this.mToolbar.findViewById(R.id.toolbar_right_image_btn);
        }

        if (this.mToolbarRightIb != null) {
            this.mToolbarRightIb.setImageResource(resId);
            this.mToolbarRightIb.setVisibility(View.VISIBLE);
            this.mToolbarRightIb.setOnClickListener(clickListener);
        }
    }

    public void initToolbarLeftTextBtn(@StringRes int resId, View.OnClickListener clickListener) {
        if (this.mToolbar != null) {
            this.mToolbarLeftTextBtn = (TextView) this.mToolbar.findViewById(R.id.toolbar_left_text_btn);
        }

        if (this.mToolbarLeftTextBtn != null) {
            this.mToolbarLeftTextBtn.setText(resId);
            this.mToolbarLeftTextBtn.setVisibility(View.VISIBLE);
            this.mToolbarLeftTextBtn.setOnClickListener(clickListener);
        }
    }

    public void initToolbarRightTextBtn(@StringRes int resId, View.OnClickListener clickListener) {
        if (this.mToolbar != null) {
            this.mToolbarRightTextBtn = (TextView) this.mToolbar.findViewById(R.id.toolbar_right_text_btn);
        }

        if (this.mToolbarRightTextBtn != null) {
            this.mToolbarRightTextBtn.setText(resId);
            this.mToolbarRightTextBtn.setVisibility(View.VISIBLE);
            this.mToolbarRightTextBtn.setOnClickListener(clickListener);
        }
    }


    public void hideTitle() {
        if (this.mToolbarTitle != null) {
            this.mToolbarTitle.setVisibility(View.GONE);
        }
    }

    public void showTitle() {
        if (this.mToolbarTitle != null) {
            this.mToolbarTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(@StringRes int resId) {
        if (this.mToolbarTitle != null) {
            this.mToolbarTitle.setText(resId);
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (this.mToolbarTitle != null) {
            this.mToolbarTitle.setText(charSequence);
        }
    }

    public void hideRightTextBtn() {
        if (this.mToolbarRightTextBtn != null) {
            this.mToolbarRightTextBtn.setVisibility(View.GONE);
        }
    }

    public void showRightTextBtn() {
        if (this.mToolbarRightTextBtn != null) {
            this.mToolbarRightTextBtn.setVisibility(View.VISIBLE);
        }
    }

    public void setRightTextBtnText(@StringRes int resId) {
        if (this.mToolbarRightTextBtn != null) {
            this.mToolbarRightTextBtn.setText(resId);
        }
    }

    public void hideLeftTextBtn() {
        if (this.mToolbarLeftTextBtn != null) {
            this.mToolbarLeftTextBtn.setVisibility(View.GONE);
        }
    }

    public void showLeftTextBtn() {
        if (this.mToolbarLeftTextBtn != null) {
            this.mToolbarLeftTextBtn.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftTextBtnText(@StringRes int resId) {
        if (this.mToolbarLeftTextBtn != null) {
            this.mToolbarLeftTextBtn.setText(resId);
        }
    }

    public void hideRightIb() {
        if (this.mToolbarRightIb != null) {
            this.mToolbarRightIb.setVisibility(View.GONE);
        }
    }

    public void showRightIb() {
        if (this.mToolbarRightIb != null) {
            this.mToolbarRightIb.setVisibility(View.VISIBLE);
        }
    }

    public void setRightIbImage(@DrawableRes int resId) {
        if (this.mToolbarRightIb != null) {
            this.mToolbarRightIb.setImageResource(resId);
        }
    }

    public void hideLeftIb() {
        if (this.mToolbarLeftIb != null) {
            this.mToolbarLeftIb.setVisibility(View.GONE);
        }
    }

    public void showLeftIb() {
        if (this.mToolbarLeftIb != null) {
            this.mToolbarLeftIb.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftIbImage(@DrawableRes int resId) {
        if (this.mToolbarLeftIb != null) {
            this.mToolbarLeftIb.setImageResource(resId);
        }
    }

    public interface ToolbarProvider {
        void provideToolbar();
    }
}
