package com.movitech.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.movitech.mobilesafe.R;
import com.movitech.mobilesafe.service.LocationService;

public class SmsReceiver extends BroadcastReceiver {
    private SharedPreferences mPref;

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mPref = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        Object[] objects = (Object[]) intent.getExtras().get("pdus");
        for (Object object : objects) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
            String address = message.getOriginatingAddress();//短信的来源号码
            String msgBody = message.getMessageBody();

            if ("#*alarm*#".equals(msgBody)) {
                MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                player.setVolume(1f, 1f);
                player.setLooping(true);
                player.start();

                abortBroadcast();
            }else if ("#*location*#".equals(msgBody)) {
                context.startService(new Intent(context, LocationService.class));

                String location = mPref.getString("location", "");

                abortBroadcast();
            }else if ("#*wipedata*#".equals(msgBody)) {

            }else if ("#*lockscreen*#".equals(msgBody)) {

            }

        }
    }
}
