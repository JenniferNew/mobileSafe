package com.movitech.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.movitech.mobilesafe.R;
import com.movitech.mobilesafe.util.ToastUtil;

public class Setup3Activity extends BaseSetupActivity {

    private static final int CODE_START_CONTACT = 0;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        etPhone = (EditText) findViewById(R.id.et_phone);

        String phone = mPref.getString("safe_phone", "");
        etPhone.setText(phone);
    }

    @Override
    public void showPreviousPage() {
        //        上一页
        startActivity(new Intent(this, Setup2Activity.class));

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_previous_in, R.anim.trans_previous_out);//进入动画和退出动画
    }

    @Override
    public void showNextPage() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone.trim())) {
//            Toast.makeText(this, "安全号码不能为空", Toast.LENGTH_SHORT).show();
            ToastUtil.showToast(this, "安全号码不能为空");
            return;
        }

        mPref.edit().putString("safe_phone", phone).commit();//保存安全号码

        //        下一页
        startActivity(new Intent(this, Setup4Activity.class));

        finish();
        //        两个页面切换动画
        overridePendingTransition(R.anim.trans_in, R.anim.trans_out);//进入动画和退出动画
    }


    public void selectContact(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivityForResult(intent, CODE_START_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String phone = data.getStringExtra("phone");

            etPhone.setText(phone.replaceAll("-", "").replaceAll(" ", ""));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
