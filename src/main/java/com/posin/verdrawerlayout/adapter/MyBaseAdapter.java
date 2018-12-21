package com.posin.verdrawerlayout.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * FileName: MyBaseAdapter
 * Author: Greetty
 * Time: 2018/12/21 11:53
 * Desc: TODO
 */
public class MyBaseAdapter extends PagerAdapter {

    private Context context;

    public MyBaseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        TextView textView = new TextView(context);
        textView.setText("ViewPages: " + position);
        textView.setTextSize(32);
        textView.setTextColor(Color.parseColor("#FF0000"));
        textView.setBackgroundColor(Color.parseColor("#18b767"));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        container.addView(textView);
        return textView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
