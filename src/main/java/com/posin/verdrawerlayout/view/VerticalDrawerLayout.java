package com.posin.verdrawerlayout.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Scroller;


/**
 * FileName: VerticalDrawerLayout
 * Author: Greetty
 * Time: 2018/12/21 9:48
 * Desc: TODO
 */
public class VerticalDrawerLayout extends FrameLayout {

    private static final String TAG = "VerticalDrawerLayout";
    private static final int MAX_SCROLL_DURATION = 400;  //滑动动画最大时间
    private static final int MIN_SCROLL_DURATION = 100;  //滑动动画最小时间
    private DrawerViewStatus drawerViewStatus = DrawerViewStatus.OPEN;

    private Context context;
    private Scroller scroller;
    private int screenHeight = 0; //屏幕显示内容高度
    private int showHeight = 200; //底部抽屉页面显示高度,默认高度为200
    private int closeOffsetHeight = 0; //关闭状态底部显示滑动页面高度


    public enum DrawerViewStatus {
        CLOSE,
        OPEN,
        SCROLLING,
        MOVE
    }

    public VerticalDrawerLayout(Context context) {
        super(context, null);
    }

    public VerticalDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public VerticalDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initData();
    }

    {
        scroller = new Scroller(getContext(), null, true);
        closeOffsetHeight = getScreenHeight();
        screenHeight = getScreenHeight();
    }

    private void initData() {
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);

//        Log.e(TAG, "scrollTo y: " + y);
//        Log.e(TAG, "-showHeight + screenHeight: " + (showHeight - screenHeight));
        if (y == (showHeight - closeOffsetHeight)) {
            // opened
            Log.e(TAG, "更改View状态: " + DrawerViewStatus.OPEN);
            if (drawerViewStatus != DrawerViewStatus.OPEN) {
                drawerViewStatus = DrawerViewStatus.OPEN;
            }
        } else if (y == -closeOffsetHeight) {
            // exited
            Log.e(TAG, "更改View状态: " + DrawerViewStatus.CLOSE);
            if (drawerViewStatus != DrawerViewStatus.CLOSE) {
                drawerViewStatus = DrawerViewStatus.CLOSE;
            }
        }
    }

    /**
     * 显示抽屉页面，没动画效果
     */
    public void showDrawerView() {
        drawerViewStatus = DrawerViewStatus.OPEN;
        showHeight = getScreenHeight() - showHeight;
        Log.e(TAG, "showHeight: " + showHeight);
        scrollTo(0, -showHeight);
    }

    /**
     * 设置抽屉页面高度
     *
     * @param height 抽屉页面显示高度
     */
    public void setDrawerViewHeight(int height) {
        this.showHeight = height;
    }

    /**
     * 隐藏抽屉页面，没动画效果
     */
    public void hideDrawerView() {
        drawerViewStatus = DrawerViewStatus.CLOSE;
        scrollTo(0, -closeOffsetHeight);
//        scrollTo(0, -(500));
    }

    /**
     * 滚动布局退出
     */
    public void scrollToClose() {

        if (drawerViewStatus == DrawerViewStatus.CLOSE) {
            return;
        }

        int dy = -getScrollY() - closeOffsetHeight;
        if (dy == 0) {
            return;
        }

        drawerViewStatus = DrawerViewStatus.SCROLLING;
        int duration = MIN_SCROLL_DURATION
                + Math.abs((MAX_SCROLL_DURATION - MIN_SCROLL_DURATION) *
                dy / (closeOffsetHeight - showHeight));
        scroller.startScroll(0, getScrollY(), 0, dy, duration);
        Log.e(TAG, "dy: " + dy);
        Log.e(TAG, "duration: " + duration);
        invalidate();
    }

    /**
     * 滚动布局开放,maxOffset之后向下滚动.
     */
    public void scrollToOpen() {
        if (drawerViewStatus == DrawerViewStatus.OPEN) {
            return;
        }

//        Log.e(TAG, "getScrollY(): " + getScrollY());
//        Log.e(TAG, "getScreenHeight(): " + closeOffsetHeight);
//        Log.e(TAG, "showHeight(): " + showHeight);

        int dy = -getScrollY() - closeOffsetHeight + showHeight;
        Log.e(TAG, "dy: " + dy);
        if (dy == 0) {
            return;
        }

        drawerViewStatus = DrawerViewStatus.SCROLLING;
        int duration = MIN_SCROLL_DURATION
                + Math.abs((MAX_SCROLL_DURATION - MIN_SCROLL_DURATION) * dy / closeOffsetHeight);
        Log.e(TAG, "duration: " + duration);

        scroller.startScroll(0, getScrollY(), 0, dy, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (!scroller.isFinished() && scroller.computeScrollOffset()) {
            int currY = scroller.getCurrY();
            scrollTo(0, currY);
            if (currY == -showHeight || currY == -closeOffsetHeight) {
                scroller.abortAnimation();
            } else {
                invalidate();
            }
        }
    }

    /**
     * 获取抽屉页面的状态
     *
     * @return DrawerViewStatus
     */
    public DrawerViewStatus getCurrentStatus() {
        return drawerViewStatus;
    }

    /**
     * 获取屏幕内容高度
     *
     * @return int
     */
    public int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int result = 0;
        int resourceId = getContext().getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        Log.e(TAG, "result: " + result);
        int screenHeight = dm.heightPixels - result;
        //return screenHeight;
        return dm.heightPixels;
    }
}
