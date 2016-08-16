package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.movitech.mobilesafe.R;

public class LostFindActivity extends AppCompatActivity {

    private SharedPreferences mPref;
    private TextView tv_safe_phone;
    private ImageView iv_protect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        boolean configed = mPref.getBoolean("configed", false);

        if (configed) {
            setContentView(R.layout.activity_lost_find);

            tv_safe_phone = (TextView) findViewById(R.id.tv_safe_phone);
            iv_protect = (ImageView) findViewById(R.id.iv_protect);


            String safe_phone = mPref.getString("safe_phone", "");
            tv_safe_phone.setText(safe_phone);

            boolean protect = mPref.getBoolean("protect", false);
            if (protect) {
                iv_protect.setImageResource(R.drawable.lock);
            }else {
                iv_protect.setImageResource(R.drawable.unlock);
            }
        }else {
            //跳转至设置向导页
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);
            finish();
        }
    }

    public void reEnter(View view) {
//        重新进入设置向导
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
    }
}
