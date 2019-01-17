package com.posin.verdrawerlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.posin.verdrawerlayout.util.DensityUtil;


/**
 * FileName: VerticalDrawerLayout
 * Author: Administrators
 * Time: 2018/12/21 9:48
 * Desc: TODO
 */
public class VerticalDrawerLayout extends LinearLayout {

    private static final String TAG = "VerticalDrawerLayout";
    private static final int MAX_SCROLL_DURATION = 400;  //滑动动画最大时间
    private static final int MIN_SCROLL_DURATION = 100;  //滑动动画最小时间
    private DrawerViewStatus drawerViewStatus = DrawerViewStatus.CLOSE;
    private VisibleChangeListener visibleChangeListener;

    private View actionView;  //顶部操作操作隐藏或显示页面
    private View contentView;  //抽屉页面内容

    private Context context;
    private boolean hasMeasureHeight = false;  // 是否已测量完毕
    private Scroller scroller;
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
        this(context, null);
    }

    public VerticalDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initData();
    }

    {
        scroller = new Scroller(getContext(), null, true);
    }

    private void initData() {

    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        if (y == -(drawerViewHeight - contentViewHeight - actionViewHeight)) {
            //Y轴结束坐标等于抽屉总高度时为打开状态
            //Log.d(TAG, "更新VerticalDrawerLayout状态: " + DrawerViewStatus.OPEN);
            if (drawerViewStatus != DrawerViewStatus.OPEN) {
                drawerViewStatus = DrawerViewStatus.OPEN;
            }
            if (visibleChangeListener != null) {
                visibleChangeListener.visibleChange(true);
            }
        } else if (y == -(drawerViewHeight - actionViewHeight)) {
            //Y轴结束坐标等于屏幕高度减去操作View高度时时为关闭状态
            //Log.d(TAG, "更新VerticalDrawerLayout状态: " + DrawerViewStatus.CLOSE);
            if (drawerViewStatus != DrawerViewStatus.CLOSE) {
                drawerViewStatus = DrawerViewStatus.CLOSE;
            }
            if (visibleChangeListener != null) {
                visibleChangeListener.visibleChange(false);
            }
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() < 2) {
            throw new IllegalArgumentException("There must be 2 child in VerticalDrawerLayout");
        }
//        contentView = getChildAt(0);
//        actionView = getChildAt(1);
        contentView = getChildAt(1);
        actionView = getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.e(TAG, "onLayout .....");
        Log.e(TAG, "left: " + left + " top: " + top + " right: " + right + "  bottom: " + bottom);
        if (!hasMeasureHeight) {
            contentViewHeight = contentView.getHeight();
            actionViewHeight = actionView.getHeight();

            drawerViewHeight = this.getHeight();
            Log.d(TAG, "contentViewHeight: " + contentViewHeight);
            Log.d(TAG, "actionViewHeight: " + actionViewHeight);
            Log.d(TAG, "drawerViewHeight: " + drawerViewHeight);
            showDrawerView();
//            closeDrawerView();
            hasMeasureHeight = true;
        }
    }

    /**
     * 显示抽屉页面，没动画效果
     */
    public void showDrawerView() {
        drawerViewStatus = DrawerViewStatus.OPEN;
        int showHeight = -(drawerViewHeight - contentViewHeight - actionViewHeight);
        scrollTo(0, showHeight);
        if (visibleChangeListener != null) {
            visibleChangeListener.visibleChange(true);
        }
    }

    /**
     * 隐藏抽屉页面，没动画效果
     */
    public void closeDrawerView() {
        drawerViewStatus = DrawerViewStatus.CLOSE;
        scrollTo(0, -(drawerViewHeight - actionViewHeight));
        if (visibleChangeListener != null) {
            visibleChangeListener.visibleChange(false);
        }
    }

    /**
     * 滚动布局退出抽屉页面
     */
    public void scrollToClose() {

        if (drawerViewStatus == DrawerViewStatus.CLOSE) {
            return;
        }
        int dy = -getScrollY() - (drawerViewHeight - actionViewHeight);
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

        int dy = -getScrollY() - (drawerViewHeight - contentViewHeight - actionViewHeight);
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
            if ((drawerViewStatus == DrawerViewStatus.OPEN &&
                    currY == -drawerViewHeight - actionViewHeight) ||
                    (drawerViewStatus == DrawerViewStatus.CLOSE &&
                            currY == -(drawerViewHeight - contentViewHeight - actionViewHeight))) {
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
     * 状态改变回调接口
     */
    public interface VisibleChangeListener {
        void visibleChange(boolean isVisible);
    }

    /**
     * 设置监听状态改变
     *
     * @param visibleChangeListener 监听方法
     */
    public void setOnVisibleChangeListener(VisibleChangeListener visibleChangeListener) {
        this.visibleChangeListener = visibleChangeListener;
    }
}
