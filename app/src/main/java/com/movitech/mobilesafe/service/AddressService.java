package com.movitech.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.movitech.mobilesafe.db.AddressDao;
import com.movitech.mobilesafe.receiver.OutCallReceiver;
import com.movitech.mobilesafe.util.ToastUtil;

public class AddressService extends Service {

    private TelephonyManager tm;
    private PhoneStateListener listener;
    private OutCallReceiver receiver;
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private WindowManager mWM;
    private TextView view;
    private SharedPreferences mPref;
    private int startX;
    private int startY;
    private int endX;
    private int endY;


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
        mPref = getSharedPreferences("config", MODE_PRIVATE);

        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new MyListener();

        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);//监听来电话的状态

        receiver = new OutCallReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);

        registerReceiver(receiver, filter);
    }

    class MyListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                {
                    System.out.println("响铃.....");
                    String address = AddressDao.getAddress(incomingNumber);

                    ToastUtil.showToast(AddressService.this, address);
                }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                {
                    if (mWM != null && view != null) {
                        mWM.removeView(view);
                        view = null;
                    }
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
        unregisterReceiver(receiver);
    }

    /*
    * 自定义浮窗
    * */
    private void showToast(String text){
        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.windowAnimations = Resources.getSystem().getIdentifier("Animation_Toast", "style", "android");
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.LEFT + Gravity.TOP;//中心位置设成左上方

        view = new TextView(this);
        view.setText(text);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("on touch");
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    {
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                    }
                    break;
                    case MotionEvent.ACTION_MOVE:
                    {
                        endX = (int) event.getRawX();
                        endY = (int) event.getRawY();

                        int dX = endX - startX;
                        int dY = endY - startY;

                        params.x = startX + dX;
                        params.y = startY + dY;

                        mWM.updateViewLayout(view, params);

                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                    }
                    break;
                    case MotionEvent.ACTION_UP:
                    {

                    }
                    break;

                }
                return false;
            }
        });

        mWM.addView(view, params);
    }
}
