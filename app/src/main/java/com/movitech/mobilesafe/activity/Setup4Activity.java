package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.movitech.mobilesafe.R;

public class Setup4Activity extends BaseSetupActivity {


    private CheckBox cb_protected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);

        cb_protected = (CheckBox) findViewById(R.id.cb_protected);
        boolean protect = mPref.getBoolean("protect", false);
        if (protect) {
            cb_protected.setText("防盗保护已经开启");
            cb_protected.setChecked(true);
        }else {
            cb_protected.setText("防盗保护没有开启");
            cb_protected.setChecked(false);
        }

        cb_protected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_protected.setText("防盗保护已经开启");
                    mPref.edit().putBoolean("protect", true).commit();
                }else {
                    cb_protected.setText("防盗保护没有开启");
                    mPref.edit().putBoolean("protect", false).commit();
                }
            }
        });
    }

    @Override
    public void showPreviousPage() {
        //        上一页
        startActivity(new Intent(this, Setup3Activity.class));

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_previous_in, R.anim.trans_previous_out);//进入动画和退出动画
    }

    @Override
    public void showNextPage() {
        //        下一页
        startActivity(new Intent(this, LostFindActivity.class));

        mPref.edit().putBoolean("configed", true).commit();

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);//进入动画和退出动画
    }
}
