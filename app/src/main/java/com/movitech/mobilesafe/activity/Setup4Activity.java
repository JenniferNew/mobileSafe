package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.movitech.mobilesafe.R;

public class Setup4Activity extends AppCompatActivity {

    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        mPref = getSharedPreferences("config", MODE_PRIVATE);
    }

    public void previous(View view) {
//        上一页
        startActivity(new Intent(this, Setup3Activity.class));

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_previous_in, R.anim.trans_previous_out);//进入动画和退出动画
    }

    public void next(View view) {
//        下一页
        startActivity(new Intent(this, LostFindActivity.class));

        mPref.edit().putBoolean("configed", true).commit();

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);//进入动画和退出动画
    }
}
