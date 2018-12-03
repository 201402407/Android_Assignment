package com.example.administrator.mp01_07_201402407;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
// 201402407 이해원
public class MainActivity extends AppCompatActivity {
    Intent intent;
    SensorReceiver mySensorReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 중력 가속도 센서 서비스 시작
        intent = new Intent(this, SensorService.class);
        startService(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        mySensorReceiver = new SensorReceiver();

        // Resume시 다시 센서 값을 받을 수 있도록 BroadCaster 등록.
        IntentFilter filter = new IntentFilter();
        filter.addAction(SensorService.MY_ACTION);
        registerReceiver(mySensorReceiver, filter);
    }

    @Override
    public void onPause(){
        super.onPause();
        stopService(intent);

        // 레지스터 등록 해제
        if (mySensorReceiver != null){
            unregisterReceiver(mySensorReceiver);
            mySensorReceiver = null;
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        // 레지스터 등록 해제
        if (mySensorReceiver != null) {
            unregisterReceiver (mySensorReceiver);
            mySensorReceiver = null;
        }
        stopService(intent);
    }
}
