package com.movitech.mobilesafe.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Jennifer on 8/17/16.
 */
public class ServiceStatusUtil {
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : runningServices) {
            String className = info.service.getClassName();
            if (serviceName.equals(className)) {
                return true;
            }
        }

        return false;
    }
}
