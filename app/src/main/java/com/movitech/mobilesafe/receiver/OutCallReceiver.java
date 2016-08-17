package com.movitech.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.movitech.mobilesafe.db.AddressDao;
import com.movitech.mobilesafe.util.ToastUtil;

/*
* 监听去电的广播接收者
* */
public class OutCallReceiver extends BroadcastReceiver {
    public OutCallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String number = getResultData();
        String address = AddressDao.getAddress(number);

        System.out.println(address);
        ToastUtil.showToast(context, address);
    }
}
