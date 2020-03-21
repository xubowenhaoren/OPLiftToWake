package com.example.oplifttowake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {

    private static final boolean DEBUG = false;
    private static final String TAG = "LGEDoze";

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (DEBUG) Log.d(TAG, "Received boot completed intent");
        Utils.checkDozeService(context);
    }
}
