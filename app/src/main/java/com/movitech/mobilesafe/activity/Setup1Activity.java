package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;

import com.movitech.mobilesafe.R;

public class Setup1Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    @Override
    public void showPreviousPage() {

    }

    @Override
    public void showNextPage() {
        //        下一页
        startActivity(new Intent(this, Setup2Activity.class));

        finish();

//        两个页面切换动画
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);//进入动画和退出动画
    }
}
