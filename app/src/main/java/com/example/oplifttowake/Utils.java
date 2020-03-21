package com.example.oplifttowake;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.os.UserHandle;
//import android.support.v7.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class Utils {
    private static final String TAG = "Utils";
    private static final boolean DEBUG = true;
    private static PowerManager.WakeLock wakeLock;

    protected static Sensor getSensor(Context context, SensorManager sm, String type) {
        for (Sensor sensor : sm.getSensorList(Sensor.TYPE_ALL)) {
            if (type.equals(sensor.getStringType())) {
                if (DEBUG) Log.d(TAG, "SENSOR FOUND");
                Toast.makeText(context,"Sensor found, service ready!",Toast.LENGTH_SHORT).show();
                return sensor;
            }
        }
        if (DEBUG) Log.d(TAG, "SENSOR NOT FOUND");
        Toast.makeText(context,"Sensor NOT found",Toast.LENGTH_SHORT).show();
        return null;
    }

    protected static Sensor findSensorWithType(SensorManager sensorManager, String type) {
        if (TextUtils.isEmpty(type)) {
            return null;
        }
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensorList) {
            if (type.equals(s.getStringType())) {
                if (DEBUG) Log.d(TAG, "SENSOR FOUND");
                return s;
            }
        }
        if (DEBUG) Log.d(TAG, "SENSOR NOT FOUND");
        return null;
    }

    protected static boolean isPickUpEnabled(Context context) {
        return true;
    }


//    https://www.jianshu.com/p/46859e268c8d
    @SuppressLint("InvalidWakeLockTag")
    public static void lightUpScreen(Context context) {
//        ScreenUtil.setScreenAlwaysOn(context,true);
//        PARTIAL_WAKE_LOCK:保持CPU 运转，屏幕和键盘灯有可能是关闭的。
//        SCREEN_DIM_WAKE_LOCK：保持CPU 运转，允许保持屏幕显示但有可能是灰的，允许关闭键盘灯
//        过期:SCREEN_BRIGHT_WAKE_LOCK：保持CPU 运转，允许保持屏幕高亮显示，允许关闭键盘灯 ,WindowManager.LayoutParams#FLAG_KEEP_SCREEN_ON
//        FULL_WAKE_LOCK：保持CPU 运转，保持屏幕高亮显示，键盘灯也保持亮度

//        下面这俩要和上面的4个配合,才能使用
//        ACQUIRE_CAUSES_WAKEUP：强制使屏幕亮起，这种锁主要针对一些必须通知用户的操作.
//        ON_AFTER_RELEASE：当锁被释放时，保持屏幕亮起一段时间
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.ON_AFTER_RELEASE, TAG);
        if (wakeLock != null) {
            wakeLock.acquire();
            wakeLock.release();
        }
    }

    public static boolean areGesturesEnabled(Context context) {
        return isPickUpEnabled(context);
    }

    protected static void checkDozeService(Context context) {
        if (DEBUG) Log.d(TAG, "Service Checker getting called");
        if (areGesturesEnabled(context)) {
            startService(context);
        } else {
            stopService(context);
        }
    }

    protected static void startService(Context context) {
        if (DEBUG) Log.d(TAG, "Starting service");
        context.startForegroundService(new Intent(context, DozeService.class));
        if (DEBUG) Log.d(TAG, "Intent for service has sent. ");
    }

    protected static void stopService(Context context) {
        if (DEBUG) Log.d(TAG, "Stopping service");
        context.stopService(new Intent(context, DozeService.class));
    }
}
