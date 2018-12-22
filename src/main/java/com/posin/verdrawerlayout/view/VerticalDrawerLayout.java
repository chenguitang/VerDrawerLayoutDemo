package com.posin.verdrawerlayout.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
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
    private DrawerViewStatus drawerViewStatus = DrawerViewStatus.CLOSE;

    private View actionView;  //顶部操作操作隐藏或显示页面
    private View contentView;  //抽屉页面内容

    private Context context;
    private boolean hasMeasureHeight = false;  // 是否已测量完毕
    private Scroller scroller;
    private int screenHeight = 0; //屏幕显示内容高度
    private int actionViewHeight; //顶部操作View高度
    private int contentViewHeight; //内容View高度
    private int drawerViewHeight;  //抽屉页面总布局高度


    public enum DrawerViewStatus {
        CLOSE,
        OPEN,
        SCROLLING,
        MOVE
    }

    public VerticalDrawerLayout(Context context) {
        super(context, null);
        this.context = context;
        Log.e(TAG, "00000000000000000000000000000");
        initData();
    }

    public VerticalDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
        initData();
    }

    public VerticalDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        Log.e(TAG, "=================================================");
        Log.e(TAG, "*******888***********************************");
        Log.e(TAG, "*******888***********************************");
        Log.e(TAG, "*******888***********************************");
        initData();
    }

    {
        scroller = new Scroller(getContext(), null, true);
        screenHeight = getScreenHeight();
    }

    private void initData() {

    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        if (y == -(screenHeight - contentViewHeight)) {
            //Y轴结束坐标等于抽屉总高度时为打开状态
            //Log.d(TAG, "更新VerticalDrawerLayout状态: " + DrawerViewStatus.OPEN);
            if (drawerViewStatus != DrawerViewStatus.OPEN) {
                drawerViewStatus = DrawerViewStatus.OPEN;
            }
        } else if (y == -screenHeight + actionViewHeight) {
            //Y轴结束坐标等于屏幕高度减去操作View高度时时为关闭状态
            //Log.d(TAG, "更新VerticalDrawerLayout状态: " + DrawerViewStatus.CLOSE);
            if (drawerViewStatus != DrawerViewStatus.CLOSE) {
                drawerViewStatus = DrawerViewStatus.CLOSE;
            }
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new IllegalArgumentException("There must be 2 child in VerticalDrawerLayout");
        }
        contentView = getChildAt(0);
        actionView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.d(TAG, "onLayout");
        if (!hasMeasureHeight) {
            contentViewHeight = contentView.getHeight();
            actionViewHeight = actionView.getHeight();
            drawerViewHeight = this.getHeight();
            Log.d(TAG, "contentViewHeight: " + contentViewHeight);
            Log.d(TAG, "actionViewHeight: " + actionViewHeight);
            Log.d(TAG, "drawerViewHeight: " + drawerViewHeight);
            closeDrawerView();
            hasMeasureHeight = true;
        }
    }

    /**
     * 显示抽屉页面，没动画效果
     */
    public void showDrawerView() {
        drawerViewStatus = DrawerViewStatus.OPEN;
        scrollTo(0, -(screenHeight - contentViewHeight));
    }


    /**
     * 隐藏抽屉页面，没动画效果
     */
    public void closeDrawerView() {
        drawerViewStatus = DrawerViewStatus.CLOSE;
        scrollTo(0, -(screenHeight - actionViewHeight));
    }

    /**
     * 滚动布局退出抽屉页面
     */
    public void scrollToClose() {

        if (drawerViewStatus == DrawerViewStatus.CLOSE) {
            return;
        }

        int dy = -getScrollY() - (screenHeight - actionViewHeight);
        Log.d(TAG, "dy: " + dy);
        if (dy == 0) {
            return;
        }
        drawerViewStatus = DrawerViewStatus.SCROLLING;
        int duration = MIN_SCROLL_DURATION
                + Math.abs((MAX_SCROLL_DURATION - MIN_SCROLL_DURATION) *
                dy / contentViewHeight);
        scroller.startScroll(0, getScrollY(), 0, dy, duration);
        invalidate();

    }

    /**
     * 滚动布局打开抽屉页面
     */
    public void scrollToOpen() {
        if (drawerViewStatus == DrawerViewStatus.OPEN) {
            return;
        }

        int dy = -getScrollY() - (screenHeight - contentViewHeight);
        Log.d(TAG, "dy: " + dy);
        if (dy == 0) {
            return;
        }
        drawerViewStatus = DrawerViewStatus.SCROLLING;
        int duration = MIN_SCROLL_DURATION
                + Math.abs((MAX_SCROLL_DURATION - MIN_SCROLL_DURATION) * dy / contentViewHeight);
        scroller.startScroll(0, getScrollY(), 0, dy, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (!scroller.isFinished() && scroller.computeScrollOffset()) {
            int currY = scroller.getCurrY();
            scrollTo(0, currY);
//            Log.d(TAG, "currY: " + currY);
            if ((drawerViewStatus == DrawerViewStatus.OPEN &&
                    currY == -(screenHeight - actionViewHeight)) ||
                    (drawerViewStatus == DrawerViewStatus.CLOSE &&
                            currY == -(screenHeight - contentViewHeight))) {
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

        //dm.heightPixels  该方法获取的屏幕高度为去除状态栏的高度
        int screenHeight;
        //判断当前页面是否已全屏显示（状态栏是否已隐藏）,如果已隐藏，屏幕高度加上状态栏高度
        if ((((Activity) getContext()).getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN) ==
                WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            screenHeight = dm.heightPixels + result;
        } else {
            screenHeight = dm.heightPixels;
        }
        return screenHeight;
    }
}
