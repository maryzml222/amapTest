package com.example.mylibrary.component.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    public static void hideSoftKeyBoard(Activity context, View view) {
        if ((null != context) && (null != view)) {
            InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void toggleInputMethods(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showInputMethods(Activity context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public static KeyboardState createKeyboardState(View parentLayout, KeyboardListener keyboardListener) {
        KeyboardState state = new KeyboardState(parentLayout, keyboardListener);
        state.initKeyboardState();
        return state;
    }

    public static void destoryKeyboardState(KeyboardState state) {
        if (null != state) {
            state.unInitKeyboardState();
            state = null;
        }
    }

    public static abstract interface KeyboardListener {
        public abstract void onKeyboardOpen();

        public abstract void onKeyboardClose();
    }

    public static class KeyboardState {
        View mParentLayout;
        KeyboardUtils.KeyboardListener mKeyboardListener;

        private KeyboardState(View parentLayout, KeyboardUtils.KeyboardListener keyboardListener) {
            this.mParentLayout = parentLayout;
            this.mKeyboardListener = keyboardListener;
        }

        private int mPreviousHeightDifference = 0;
        private boolean mIsKeyBoardVisible = false;
        private ViewTreeObserver.OnGlobalLayoutListener mLayoutLIstener;

        private void initKeyboardState() {
            this.mLayoutLIstener = new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    Rect r = new Rect();
                    KeyboardUtils.KeyboardState.this.mParentLayout.getWindowVisibleDisplayFrame(r);
                    int screenHeight = KeyboardUtils.KeyboardState.this.mParentLayout.getRootView().getHeight();
                    int heightDifference = screenHeight - r.bottom;
                    if (KeyboardUtils.KeyboardState.this.mPreviousHeightDifference - heightDifference > 50) {
                        KeyboardUtils.KeyboardState.this.mIsKeyBoardVisible = false;
                        if (null != KeyboardUtils.KeyboardState.this.mKeyboardListener) {
                            KeyboardUtils.KeyboardState.this.mKeyboardListener.onKeyboardClose();
                        }
                    }
                    KeyboardUtils.KeyboardState.this.mPreviousHeightDifference = heightDifference;
                    if (heightDifference > 100) {
                        if (null != KeyboardUtils.KeyboardState.this.mKeyboardListener) {
                            KeyboardUtils.KeyboardState.this.mKeyboardListener.onKeyboardOpen();
                        }
                        KeyboardUtils.KeyboardState.this.mIsKeyBoardVisible = true;
                    } else {
                        KeyboardUtils.KeyboardState.this.mIsKeyBoardVisible = false;
                    }
                }
            };
            this.mParentLayout.getViewTreeObserver().addOnGlobalLayoutListener(this.mLayoutLIstener);
        }

        private void unInitKeyboardState() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.mParentLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this.mLayoutLIstener);
            } else {
                this.mParentLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this.mLayoutLIstener);
            }
        }

        public boolean isKeyBoardVisible() {
            return this.mIsKeyBoardVisible;
        }

        public void hideInputMethods(Activity context) {
            if (this.mIsKeyBoardVisible) {
                KeyboardUtils.toggleInputMethods(context);
            }
        }

        public void showInputMethods(Activity context) {
            if (!this.mIsKeyBoardVisible) {
                KeyboardUtils.toggleInputMethods(context);
            }
        }

        public void hideInputMethods() {
            if ((this.mParentLayout.getContext() instanceof Activity)) {
                hideInputMethods((Activity) this.mParentLayout.getContext());
            }
        }

        public void showInputMethods() {
            if ((this.mParentLayout.getContext() instanceof Activity)) {
                showInputMethods((Activity) this.mParentLayout.getContext());
            }
        }

        public int getKeyboardHeight() {
            return this.mPreviousHeightDifference;
        }

        public void setKeyboardListener(KeyboardUtils.KeyboardListener listener) {
            this.mKeyboardListener = listener;
        }
    }
}