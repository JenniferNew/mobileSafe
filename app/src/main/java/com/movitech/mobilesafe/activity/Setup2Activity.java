package com.movitech.mobilesafe.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.movitech.mobilesafe.R;
import com.movitech.mobilesafe.util.ToastUtil;
import com.movitech.mobilesafe.view.SettingItemView;

public class Setup2Activity extends BaseSetupActivity {

    private static final int READ_PHONE_STATE_CODE = 0;
    private SettingItemView siv_sim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        siv_sim = (SettingItemView) findViewById(R.id.siv_sim);
        String sim = mPref.getString("sim", null);
        if (!TextUtils.isEmpty(sim)) {
            siv_sim.setChecked(true);
        }else {
            siv_sim.setChecked(false);
        }

        siv_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siv_sim.isChecked()) {
                    siv_sim.setChecked(false);
                    mPref.edit().remove("sim").commit();
                }else {
                    siv_sim.setChecked(true);
                    if (ContextCompat.checkSelfPermission(Setup2Activity. this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Setup2Activity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_CODE);
                    }else {
                        //保存SIM信息
                        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//                    获取sim卡序列号
                        String simSerialNumber = telephonyManager.getSimSerialNumber();
                        mPref.edit().putString("sim", simSerialNumber).commit(); //将序列卡序列号保存在preference
                    }

                }
            }
        });
    }

    public void showPreviousPage() {
        //        上一页
        startActivity(new Intent(this, Setup1Activity.class));

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_previous_in, R.anim.trans_previous_out);//进入动画和退出动画
    }

    public void showNextPage() {
        String sim = mPref.getString("sim", null);

        if (TextUtils.isEmpty(sim)) {
            ToastUtil.showToast(this, "必须绑定SIM卡");
            return;
        }

        //        下一页
        startActivity(new Intent(this, Setup3Activity.class));

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);//进入动画和退出动画
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_PHONE_STATE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                //保存SIM信息
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//                    获取sim卡序列号
                String simSerialNumber = telephonyManager.getSimSerialNumber();
                mPref.edit().putString("sim", simSerialNumber).commit(); //将序列卡序列号保存在preference
            } else {
                // Permission Denied
            }
        }
    }
}
