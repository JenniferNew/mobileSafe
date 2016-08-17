package com.movitech.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.mobilesafe.R;

/**
 * Created by Jennifer on 8/11/16.
 */
public class SettingClickView extends RelativeLayout{

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private TextView tv_titile;
    private TextView tv_description;

    public SettingClickView(Context context) {
        super(context);
        initView();
    }

    public SettingClickView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();

    }

    public SettingClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /*
    * 初始化布局
    * */
    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_setting_click, this);
        tv_titile = (TextView) findViewById(R.id.tv_title);
        tv_description = (TextView) findViewById(R.id.tv_description);
    }

    public void setTitle(String title) {
        tv_titile.setText(title);
    }

    public void setDesc(String desc) {
        tv_description.setText(desc);
    }
}
