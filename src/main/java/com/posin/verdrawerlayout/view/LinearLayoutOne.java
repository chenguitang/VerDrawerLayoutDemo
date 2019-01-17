package com.posin.verdrawerlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * FileName: LinearLayoutOne
 * Author: Greetty
 * Time: 2019/1/16 14:58
 * Desc: TODO
 */
public class LinearLayoutOne extends LinearLayout {

    private static final String TAG = "LinearLayoutOne";

    public LinearLayoutOne(Context context) {
        super(context);
    }

    public LinearLayoutOne(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }
}
