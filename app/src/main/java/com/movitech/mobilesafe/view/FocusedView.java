package com.movitech.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class FocusedView extends TextView {

    /*
    *
    * */
    public FocusedView(Context context) {
        super(context);
    }

    /*
    * 有属性的时候
    * */
    public FocusedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    * 有Style样式的时候
    * */
    public FocusedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    * 跑马灯需要获取焦点才有效果
    * */
    @Override
    public boolean isFocused() {
        return true;
    }
}
