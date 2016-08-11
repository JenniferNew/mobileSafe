package com.movitech.mobilesafe.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.movitech.mobilesafe.R;
import com.movitech.mobilesafe.view.SettingItemView;

/*
* 设置中心
* */
public class SettingActivity extends AppCompatActivity {

    private SettingItemView sivUpdate;//设置升级
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
        sivUpdate.setTitle("自动更新设置");

        boolean auto_update = mSharedPreferences.getBoolean("auto_update", true);
        if (auto_update) {
            sivUpdate.setDesc("自动更新已开启");
            sivUpdate.setChecked(true);
        }else {
            sivUpdate.setDesc("自动更新已关闭");
            sivUpdate.setChecked(false);
        }

        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sivUpdate.isChecked()) {
                    sivUpdate.setChecked(false);
                    sivUpdate.setDesc("自动更新已关闭");

                    mSharedPreferences.edit().putBoolean("auto_update", false);
                }else {
                    sivUpdate.setChecked(true);
                    sivUpdate.setDesc("自动更新已开启");
                    mSharedPreferences.edit().putBoolean("auto_update", true);
                }
            }
        });
    }
}
