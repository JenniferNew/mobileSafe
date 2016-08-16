package com.movitech.mobilesafe.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhangjingjing on 8/16/16.
 */
public class ToastUtil {
    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
