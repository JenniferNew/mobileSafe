package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;

import com.movitech.mobilesafe.R;

public class Setup4Activity extends BaseSetupActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
    }

    @Override
    public void showPreviousPage() {
        //        上一页
        startActivity(new Intent(this, Setup3Activity.class));

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_previous_in, R.anim.trans_previous_out);//进入动画和退出动画
    }

    @Override
    public void showNextPage() {
        //        下一页
        startActivity(new Intent(this, LostFindActivity.class));

        mPref.edit().putBoolean("configed", true).commit();

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);//进入动画和退出动画
    }
}
