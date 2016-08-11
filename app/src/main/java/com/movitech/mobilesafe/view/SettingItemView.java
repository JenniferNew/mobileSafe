package com.movitech.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.mobilesafe.R;

/**
 * Created by Jennifer on 8/11/16.
 */
public class SettingItemView extends RelativeLayout{

    private TextView tv_titile;
    private TextView tv_description;
    private CheckBox cb_status;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /*
    * 初始化布局
    * */
    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_setting_item, this);
        tv_titile = (TextView) findViewById(R.id.tv_title);
        tv_description = (TextView) findViewById(R.id.tv_description);
        cb_status = (CheckBox) findViewById(R.id.cb_status);
    }

    public void setTitle(String title) {
        tv_titile.setText(title);
    }

    public void setDesc(String desc) {
        tv_description.setText(desc);
    }

    /*
    *  @return
    * */
    public boolean isChecked() {
        return cb_status.isChecked();
    }

    public void setChecked(boolean checked) {
        cb_status.setChecked(checked);
    }
}
