package com.movitech.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/*
* 注册开机重启的广播
* */
public class BootCompleteReceiver extends BroadcastReceiver {

    private SharedPreferences mPref;

    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mPref = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean protect = mPref.getBoolean("protect", false);

        //只有在防盗保护开启的情况才发送报警短信
        if (protect) {
            String sim = mPref.getString("sim", null);

            if (!TextUtils.isEmpty(sim)) {
                //获取当前手机的SIM卡
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String current_sim = telephonyManager.getSimSerialNumber();
                if (current_sim.equals(sim)) {

                }else {
                    // TODO: 8/15/16 发送报警短信
                    String phone = mPref.getString("safe_phone", "");
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, "SIM Card Changed", null, null);
                }
            }
        }

    }
}
