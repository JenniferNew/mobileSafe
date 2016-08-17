package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.movitech.mobilesafe.R;
import com.movitech.mobilesafe.service.AddressService;
import com.movitech.mobilesafe.util.ServiceStatusUtil;
import com.movitech.mobilesafe.view.SettingItemView;

/*
* 设置中心
* */
public class SettingActivity extends AppCompatActivity {

    private SettingItemView sivUpdate;//设置升级
    private SharedPreferences mSharedPreferences;
    private SettingItemView siv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        initUpdateView();
        initAddressView();
    }

    private void initUpdateView() {
        sivUpdate = (SettingItemView) findViewById(R.id.siv_update);


        boolean auto_update = mSharedPreferences.getBoolean("auto_update", true);
        if (auto_update) {
            sivUpdate.setChecked(true);
        }else {
            sivUpdate.setChecked(false);
        }

        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sivUpdate.isChecked()) {
                    sivUpdate.setChecked(false);

                    mSharedPreferences.edit().putBoolean("auto_update", false).commit();
                }else {
                    sivUpdate.setChecked(true);
                    mSharedPreferences.edit().putBoolean("auto_update", true).commit();
                }
            }
        });
    }

    private void initAddressView() {
        boolean isRunning = ServiceStatusUtil.isServiceRunning(this, "com.movitech.mobilesafe.service.AddressService");

        siv_address = (SettingItemView) findViewById(R.id.siv_address);

        if (isRunning) {
            siv_address.setChecked(true);
        }else {
            siv_address.setChecked(false);
        }

        siv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siv_address.isChecked()) {
                    siv_address.setChecked(false);
                    stopService(new Intent(SettingActivity.this, AddressService.class));
                }else {
                    siv_address.setChecked(true);
                    startService(new Intent(SettingActivity.this, AddressService.class));
                }
            }
        });
    }
}
