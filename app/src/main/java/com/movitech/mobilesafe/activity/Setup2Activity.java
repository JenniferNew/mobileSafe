package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.movitech.mobilesafe.R;

public class Setup2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
    }

    public void previous(View view) {
//        上一页
        startActivity(new Intent(this, Setup1Activity.class));

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_previous_in, R.anim.trans_previous_out);//进入动画和退出动画
    }

    public void next(View view) {
//        下一页
        startActivity(new Intent(this, Setup3Activity.class));

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);//进入动画和退出动画
    }
}
