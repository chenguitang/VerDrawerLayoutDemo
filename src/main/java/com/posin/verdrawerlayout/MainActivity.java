package com.posin.verdrawerlayout;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.posin.verdrawerlayout.adapter.MyBaseAdapter;
import com.posin.verdrawerlayout.view.VerticalDrawerLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private VerticalDrawerLayout verticalDrawerLayout;
    private TextView tvFood;
    private ViewPager main_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        verticalDrawerLayout = (VerticalDrawerLayout) findViewById(R.id.scroll_down_layout);
        tvFood = (TextView) findViewById(R.id.text_foot);
        main_pager = (ViewPager) findViewById(R.id.main_pager);

        if (verticalDrawerLayout.getBackground() != null) {
            verticalDrawerLayout.getBackground().setAlpha(0);
        }

        verticalDrawerLayout.setDrawerViewHeight(300);
        verticalDrawerLayout.hideDrawerView();


        tvFood.setOnClickListener(this);
        main_pager.setAdapter(new MyBaseAdapter(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_foot:

                Log.e(TAG, "getCurrentStatus: " + verticalDrawerLayout.getCurrentStatus());

                if (verticalDrawerLayout.getCurrentStatus() ==
                        VerticalDrawerLayout.DrawerViewStatus.CLOSE) {
//                    verticalDrawerLayout.showDrawerView(200);
                    verticalDrawerLayout.scrollToOpen();

                } else if (verticalDrawerLayout.getCurrentStatus() ==
                        VerticalDrawerLayout.DrawerViewStatus.OPEN) {
                    verticalDrawerLayout.scrollToClose();

                }
                break;
            default:
                break;
        }
    }
}
