package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.movitech.mobilesafe.R;

public class AToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atools);


    }

    /*
    * 电话号码查询
    * */
    public void numberAddressQuery(View view) {
        startActivity(new Intent(this, AddressActivity.class));
    }
}
