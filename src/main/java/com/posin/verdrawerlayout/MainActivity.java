package com.posin.verdrawerlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.posin.verdrawerlayout.adapter.MyBaseAdapter;
import com.posin.verdrawerlayout.util.ScreenUtil;
import com.posin.verdrawerlayout.view.VerticalDrawerLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, VerticalDrawerLayout.VisibleChangeListener {

    private static final String TAG = "MainActivity";

    private VerticalDrawerLayout verticalDrawerLayout;
    private ImageView tvFood;
    private ViewPager main_pager;
    private Button btn_main;
    private TextView tv_scroll;
    final int moveHeight = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verticalDrawerLayout = (VerticalDrawerLayout) findViewById(R.id.scroll_down_layout);
        tvFood = (ImageView) findViewById(R.id.text_foot);
        main_pager = (ViewPager) findViewById(R.id.main_pager);
        btn_main = (Button) findViewById(R.id.btn_main);
        tv_scroll = (TextView) findViewById(R.id.tv_scroll);

        if (verticalDrawerLayout.getBackground() != null) {
            verticalDrawerLayout.getBackground().setAlpha(0);
        }

        if (tvFood.getBackground() != null) {
            tvFood.getBackground().setAlpha(0);
        }

        Log.e(TAG, "getScreenHeight: " + ScreenUtil.getScreenHeight(this));
        tvFood.setOnClickListener(this);
        btn_main.setOnClickListener(this);
        verticalDrawerLayout.setOnVisibleChangeListener(this);
        main_pager.setAdapter(new MyBaseAdapter(this));

        tv_scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_scroll.scrollTo(0, moveHeight);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_foot:
                Log.e(TAG, "getCurrentStatus: " + verticalDrawerLayout.getCurrentStatus());
                if (verticalDrawerLayout.getCurrentStatus() ==
                        VerticalDrawerLayout.DrawerViewStatus.CLOSE) {
//                    verticalDrawerLayout.showDrawerView();
                    verticalDrawerLayout.scrollToOpen();
                } else if (verticalDrawerLayout.getCurrentStatus() ==
                        VerticalDrawerLayout.DrawerViewStatus.OPEN) {
//                    verticalDrawerLayout.closeDrawerView();
                    verticalDrawerLayout.scrollToClose();
                }
                break;
            case R.id.btn_main:
//                Toast.makeText(this, "响应主页面功能", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, TestViewActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void visibleChange(boolean isVisible) {
        if (isVisible) {
            tvFood.setImageDrawable(getResources().getDrawable(R.mipmap.ic_print_down));
        } else {
            tvFood.setImageDrawable(getResources().getDrawable(R.mipmap.ic_print_up));
//            tvFood.setBackgroundResource(R.mipmap.ic_launcher);
        }
    }
}
