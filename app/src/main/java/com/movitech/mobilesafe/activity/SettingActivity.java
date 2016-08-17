package com.movitech.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.movitech.mobilesafe.R;
import com.movitech.mobilesafe.service.AddressService;
import com.movitech.mobilesafe.util.ServiceStatusUtil;
import com.movitech.mobilesafe.view.SettingClickView;
import com.movitech.mobilesafe.view.SettingItemView;
/*
* 设置中心
* */
public class SettingActivity extends AppCompatActivity {

    private SettingItemView sivUpdate;//设置升级
    private SharedPreferences mSharedPreferences;
    private SettingItemView siv_address;
    private SettingClickView scvStyle;
    private SettingClickView scvLocation;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        initUpdateView();
        initAddressView();
        initAddressStyle();
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

    private void initAddressStyle() {
        items = new String[]{"半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿"};
        scvStyle = (SettingClickView) findViewById(R.id.scv_style);

        int style = mSharedPreferences.getInt("address_style", 0);
        scvStyle.setTitle("归属地提示框风格");
        scvStyle.setDesc(items[style]);

        scvStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChooseDialog();
            }
        });
    }

    private void showSingleChooseDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("归属地提示框风格");

        builder.setIcon(Resources.getSystem().getIdentifier("star_big_on", "drawable", "android"));

        //读取默认保存的style
        int style = mSharedPreferences.getInt("address_style", 0);

        builder.setSingleChoiceItems(items, style, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSharedPreferences.edit().putInt("address_style", which).commit();
                scvStyle.setDesc(items[which]);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", null);

        builder.show();
    }

    private void initAddressLocation() {
        scvLocation = (SettingClickView) findViewById(R.id.scv_location);
        scvLocation.setTitle("设置归属地提示框");
        scvLocation.setDesc("设置归属地提示框显示位置");

        scvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
