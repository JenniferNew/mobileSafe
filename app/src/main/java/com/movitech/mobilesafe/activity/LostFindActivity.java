package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.movitech.mobilesafe.R;

public class LostFindActivity extends AppCompatActivity {

    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mPref = getSharedPreferences("config", MODE_PRIVATE);
        boolean configed = mPref.getBoolean("configed", false);
        if (configed) {
            setContentView(R.layout.activity_lost_find);
        }else {
            //跳转至设置向导页
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);
            finish();
        }
    }

    public void reEnter(View view) {
//        重新进入设置向导
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
    }
}
