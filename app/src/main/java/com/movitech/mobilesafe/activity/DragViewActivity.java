package com.movitech.mobilesafe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.movitech.mobilesafe.R;

public class DragViewActivity extends AppCompatActivity {

    //onMeasure(测量) -> onLayout(摆放位置) -> onDraw(绘制)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);

        getWindowManager().getDefaultDisplay().getWidth();
        getWindowManager().getDefaultDisplay().getHeight();
    }
}
