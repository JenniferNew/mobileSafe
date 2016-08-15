package com.movitech.mobilesafe.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jennifer on 8/15/16.
 */
public abstract class BaseSetupActivity extends AppCompatActivity{
    private GestureDetector mDetector;
    public SharedPreferences mPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPref = getSharedPreferences("config", MODE_PRIVATE);
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                /*
                * 判断纵向滑动是否幅度过大,过大的话不允许切换界面
                * */
                if (Math.abs(e2.getRawY() - e1.getRawY()) > 100) {
                    return true;
                }

                if (Math.abs(velocityX) < 100) {
                    return true;
                }

                //向右滑动,到上一页
                if (e2.getRawX() - e1.getRawX() > 200) {
                    showPreviousPage();
                    return true;
                }

                //向左滑动,到下一页
                if (e1.getRawX() - e2.getRawX() > 200) {
                    showNextPage();
                    return true;
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    public abstract void showPreviousPage();

    public abstract void showNextPage();

    public void previous(View view) {
        showPreviousPage();
    }

    public void next(View view) {
        showNextPage();
    }

    /*
    * 滑动翻页
    * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

}
