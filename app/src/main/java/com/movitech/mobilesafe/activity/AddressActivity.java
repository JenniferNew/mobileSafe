package com.movitech.mobilesafe.activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.movitech.mobilesafe.R;
import com.movitech.mobilesafe.db.AddressDao;

public class AddressActivity extends AppCompatActivity {

    private EditText et_number;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        et_number = (EditText) findViewById(R.id.et_number);
        tv_result = (TextView) findViewById(R.id.tv_result);

        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String address = AddressDao.getAddress(s.toString());
                tv_result.setText(address);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void query(View view) {
        String number = et_number.getText().toString().trim();
        if (!TextUtils.isEmpty(number)) {
            String address = AddressDao.getAddress(number);
            tv_result.setText(address);
        }else {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

           /* //自己的插补器
            shake.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float x) {
                    float y = 0;

                    return y;
                }
            });
           */

            et_number.startAnimation(shake);
            vibrate();
        }
    }

    /*
    * Vibrate
    * */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
    }
}
