package com.posin.verdrawerlayout.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Gprinter.
 */
public class DensityUtil {

    public static float convertDpToPixel(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }


    public static float convertPixelsToDp(Context context, float px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static int convertPixelsToSP(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int convertSpToPx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float convertPtToPx(Context context, float ptValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return ptValue * metrics.xdpi * (1.0f / 72);
    }

    public static float convertPixelsToMM(Context context, float pixels) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return pixels / (metrics.xdpi * (1.0f / 24.5f));
    }

    public static float getWidth(Activity context) {
        //1、通过WindowManager获取
        DisplayMetrics outMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        return widthPixels;
    }

    public static float getHeight(Activity context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        return heightPixels;
    }

    /**
     * Author: 猿史森林
     * Date：2017/8/16 15:47
     * 修改日期：2017/8/16 15:47
     * <p>
     * 方法修改说明：
     * <p>
     * 方法说明：将毫米转换成像素
     *
     * @see android.util.TypedValue
     */
    public static float convertMmToPixels(Context context, float mm) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return mm * metrics.xdpi * (1.0f / 24.5f);
    }
}
