package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.movitech.mobilesafe.R;
import com.movitech.mobilesafe.view.SettingItemView;

public class Setup2Activity extends BaseSetupActivity {

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
                    //保存SIM信息
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//                    获取sim卡序列号
                    String simSerialNumber = telephonyManager.getSimSerialNumber();
                    mPref.edit().putString("sim", simSerialNumber).commit(); //将序列卡序列号保存在preference
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
        //        下一页
        startActivity(new Intent(this, Setup3Activity.class));

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);//进入动画和退出动画
    }

}
