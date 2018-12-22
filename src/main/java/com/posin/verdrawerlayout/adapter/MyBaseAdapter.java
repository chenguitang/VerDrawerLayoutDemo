package com.posin.verdrawerlayout.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.posin.verdrawerlayout.R;

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

        if (position == 0) {

            View view = LayoutInflater.from(context).inflate(R.layout.layout_viewpager, null);
            container.addView(view);

        } else {
            TextView textView = new TextView(context);
            textView.setText("ViewPages: " + position +
                    "\n" + "ViewPages aaaa: " + position +
                    "\n" + "ViewPages bbbb: " + position +
                    "\n" + "ViewPages cccc: " + position +
                    "\n" + "ViewPages dddd: " + position +
                    "\n" + "ViewPages eeee: " + position +
                    "\n" + "ViewPages ffff: " + position
            );
            textView.setTextSize(48);
            textView.setTextColor(Color.parseColor("#FF0000"));
            textView.setBackgroundColor(Color.parseColor("#FFCD43"));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 100);

            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.TOP);


            container.addView(textView);
        }
        return container.getChildAt(position);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
