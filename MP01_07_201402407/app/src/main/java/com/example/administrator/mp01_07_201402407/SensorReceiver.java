package com.example.administrator.mp01_07_201402407;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SensorReceiver extends BroadcastReceiver {
    static final String LOG_TAG = "SensorReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String result = intent.getStringExtra("result");

        Log.d("service", result);
    }
}
