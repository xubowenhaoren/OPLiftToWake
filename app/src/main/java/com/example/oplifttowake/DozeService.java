package com.example.oplifttowake;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class DozeService extends Service {
    private static final String TAG = "DozeService";
    private static final boolean DEBUG = true;

    private static final String CHANNEL_ID = "OPLiftToWakeChannel";

    private static final int NOTIFICATION_ID = 10388;

    private PickupSensor mPickupSensor;
//    private PocketSensor mPocketSensor;

    @Override
    public void onCreate() {
        if (DEBUG) Log.d(TAG, "Creating service");
        mPickupSensor = new PickupSensor(this);
//        mPocketSensor = new PocketSensor(this);

        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenStateReceiver, screenStateFilter);

        //如果API在26以上即版本为O则调用startForefround()方法启动服务
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (DEBUG) Log.d(TAG, "Android O detected, starting foreground");
            setForegroundService("LiftToWake", "Lift to Wake Active",
                    "Lift to Wake Active Channel");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) Log.d(TAG, "Starting service");
        //如果API在26以上即版本为O则调用startForefround()方法启动服务
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            if (DEBUG) Log.d(TAG, "Android O detected, starting foreground");
//            setForegroundService("LiftToWake", "Lift to Wake Active",
//                    "Lift to Wake Active Channel");
//        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (DEBUG) Log.d(TAG, "Destroying service");
        super.onDestroy();
        this.unregisterReceiver(mScreenStateReceiver);
        mPickupSensor.disable();
//        mPocketSensor.disable();
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
        // super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void onDisplayOn() {
        if (DEBUG) Log.d(TAG, "Display on");
        if (Utils.isPickUpEnabled(this)) {
//            SystemClock.sleep(5000);
            mPickupSensor.disable();
            if (DEBUG) Log.d(TAG, "Sensor disabled");
        }
//        if (Utils.isPocketEnabled(this)) {
//            mPocketSensor.disable();
//        }
    }

    private void onDisplayOff() {
        if (DEBUG) Log.d(TAG, "Display off");
        if (Utils.isPickUpEnabled(this)) {
            mPickupSensor.enable();
        }
//        if (Utils.isPocketEnabled(this)) {
//            mPocketSensor.enable();
//        }
    }

    private BroadcastReceiver mScreenStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                onDisplayOn();
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                onDisplayOff();
            }
        }
    };

//    private void createNotificationChannel(String description) {
//        // 在API>=26的时候创建通知渠道
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //设定的通知渠道名称
//            String channelName = getString(R.string.channel_name);
//            //设置通知的重要程度
//            int importance = NotificationManager.IMPORTANCE_LOW;
//            //构建通知渠道
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
//            channel.setDescription(description);
//            //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }


    public void  setForegroundService(String notificationTitle, String notificationContent,
                                      String description)
    {
        if (DEBUG) Log.d(TAG, "ForegroundService helper called");
        //设定的通知渠道名称
        String channelName = getString(R.string.channel_name);
        //设置通知的重要程度
        int importance = NotificationManager.IMPORTANCE_LOW;
        //构建通知渠道
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
        channel.setDescription(description);
        //在创建的通知渠道上发送通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background) //设置通知图标
                .setContentTitle(notificationTitle)//设置通知标题  //
                .setContentText(notificationContent)//设置通知内容
                .setAutoCancel(true) //用户触摸时，自动关闭
                .setOngoing(true);//设置处于运行状态
        //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
        NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        //将服务置于启动状态 NOTIFICATION_ID指的是创建的通知的ID
        startForeground(NOTIFICATION_ID, builder.build());
    }
}



