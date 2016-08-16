package com.movitech.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movitech.mobilesafe.R;
import com.movitech.mobilesafe.util.Md5Util;

public class HomeActivity extends AppCompatActivity {

    private GridView gv_home;
    private String[] mItems = new String[]{"手机防盗", "通讯卫视", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
    private int[] mPics = new int[]{R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps, R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable.home_trojan, R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings};
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter());

        mPref = getSharedPreferences("config", MODE_PRIVATE);

        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        showPasswordDialog();
                        break;
                    case 7:
                    {
                        Intent intent = new Intent(HomeActivity.this, AToolsActivity.class);
                        startActivity(intent);
                    }
                        break;
                    case 8:
                    {
                        //设置中心
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                    }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /*
    * 显示输入密码弹窗
    * */
    private void showPasswordInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_input_password, null);

        dialog.setView(view, 0, 0, 0, 0);

        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        final EditText et_password = (EditText) view.findViewById(R.id.et_password);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString();
                String savedPassword = mPref.getString("password", null);

                if (!TextUtils.isEmpty(password)) {
                    if (Md5Util.encode(password).equals(savedPassword)) {
                        Toast.makeText(HomeActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        Intent intent = new Intent(HomeActivity.this, LostFindActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(HomeActivity.this, "请输入正确的密码！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(HomeActivity.this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /*
    * 显示密码弹框
    * */
    private void showPasswordDialog() {
        //判断是否设置过密码
        String savedPassword = mPref.getString("password", null);
        if (!TextUtils.isEmpty(savedPassword)) {
            showPasswordInputDialog();
        }else {
            showPasswordSetDialog();
        }
    }

    private void showPasswordSetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_set_password, null);

//        dialog.setView(view);
        dialog.setView(view, 0, 0, 0, 0);

        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        final EditText et_password = (EditText) view.findViewById(R.id.et_password);
        final EditText et_password_confirm = (EditText) view.findViewById(R.id.et_password_confirm);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString();
                String password_confirm = et_password_confirm.getText().toString();

                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(password_confirm)) {
                    if (password.equals(password_confirm)) {
                        Toast.makeText(HomeActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        mPref.edit().putString("password", Md5Util.encode(password)).commit();
                        dialog.dismiss();

                        Intent intent = new Intent(HomeActivity.this, LostFindActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(HomeActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(HomeActivity.this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(HomeActivity.this, R.layout.home_list_item, null);
            ImageView iv_item = (ImageView) view.findViewById(R.id.iv_item);
            TextView tv_item = (TextView) view.findViewById(R.id.tv_item);

            iv_item.setImageResource(mPics[position]);
            tv_item.setText(mItems[position]);
            return view;
        }
    }
}
