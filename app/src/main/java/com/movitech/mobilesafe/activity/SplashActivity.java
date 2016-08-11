package com.movitech.mobilesafe.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.movitech.mobilesafe.R;
import com.movitech.mobilesafe.util.StreamUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_URL_ERROR = 1;
    private static final int CODE_NET_ERROR = 2;
    private static final int CODE_JSON_ERROR = 3;
    private static final int CODE_ENTER_HOME = 4;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private TextView tv_version;
    private TextView tv_progress;

    private String mVersionName;//版本名称
    private int mVersionCode;//版本号
    private String mDesc;//版本描述
    private String mDownloadUrl;//下载地址

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_URL_ERROR:
                    Toast.makeText(SplashActivity.this, "URL错误", Toast.LENGTH_LONG).show();
                    enterHome();
                    break;
                case CODE_NET_ERROR:
                    Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                    enterHome();
                    break;
                case CODE_JSON_ERROR:
                    Toast.makeText(SplashActivity.this, "数据解析错误", Toast.LENGTH_LONG).show();
                    enterHome();
                    break;
                case CODE_ENTER_HOME:
                    enterHome();
                    break;
            }

        }
    };
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_progress = (TextView) findViewById(R.id.tv_progress);

        String versionName = getVersionName();
        tv_version.setText("版本号:" + versionName);

        mSharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        if (mSharedPreferences.getBoolean("auto_update", true)) {
            checkVersion();
        }else {
            mHandler.sendEmptyMessageDelayed(CODE_ENTER_HOME, 2000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                download();
            } else {
                // Permission Denied
            }
        }
    }

    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            String versionName =  packageInfo.versionName;

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            int versionCode =  packageInfo.versionCode;

            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /* *
    * 从服务器端获取版本信息进行校验
    *
    */
    private void checkVersion() {
        final long startTime = System.currentTimeMillis();

        new Thread() {
            @Override
            public void run() {
                super.run();
                Message message = Message.obtain();
                HttpURLConnection connection = null;

                try {
                    URL url = new URL("http://10.0.2.2:8080/update.json");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();

                        String result = StreamUtil.readFromStream(inputStream);
                        System.out.println(result);

                        JSONObject jsonObject = new JSONObject(result);
                        mVersionName = jsonObject.getString("versionName");
                        mVersionCode = jsonObject.getInt("versionCode");
                        mDesc = jsonObject.getString("description");
                        mDownloadUrl = jsonObject.getString("downloadUrl");

                        if (mVersionCode > getVersionCode()) {
                            message.what = CODE_UPDATE_DIALOG;
                        }else {
                            message.what = CODE_ENTER_HOME;
                        }
                    }

                } catch (MalformedURLException e) {
                    message.what = CODE_URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    message.what = CODE_NET_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    message.what = CODE_JSON_ERROR;
                    e.printStackTrace();
                }finally {
                    long endTime = System.currentTimeMillis();
                    long timeUsed = endTime - startTime;
                    if (timeUsed < 2000) {
                        //强制休眠2秒
                        try {
                            Thread.sleep(2000 - timeUsed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    mHandler.sendMessage(message);
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }.start();
    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本:" + mVersionName);
        builder.setMessage(mDesc);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                }else {
                    download();
                }
            }
        });

        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });

        builder.show();
    }

    private void download() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            tv_progress.setVisibility(View.VISIBLE);

            String target = Environment.getExternalStorageDirectory() + "/update.apk";

            HttpUtils http = new HttpUtils();

            http.download(mDownloadUrl, target, new RequestCallBack<File>() {

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    tv_progress.setText("下载进度" + current * 100.0/total + "%");
                    System.out.println("total: " + total + "current:" + current);
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Toast.makeText(SplashActivity.this, "下载成功", Toast.LENGTH_LONG);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
//                    startActivity(intent);
                    startActivityForResult(intent, 0);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    System.out.println("e:" + e.getMessage());
                    Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_LONG);
                }
            });
        }else {
            Toast.makeText(SplashActivity.this, "没有SD卡", Toast.LENGTH_LONG);
        }
    }

    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }
}
