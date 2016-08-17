package com.movitech.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.movitech.mobilesafe.db.AddressDao;
import com.movitech.mobilesafe.util.ToastUtil;

public class AddressService extends Service {

    private TelephonyManager tm;
    private PhoneStateListener listener;

    public AddressService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new MyListener();

        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);//监听来电话的状态
    }

    class MyListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING: {
                    System.out.println("响铃.....");
                    String address = AddressDao.getAddress(incomingNumber);

                    ToastUtil.showToast(AddressService.this, address);
                }
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tm.listen(listener, PhoneStateListener.LISTEN_NONE); //停止监听
    }
}
