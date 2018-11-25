package com.example.administrator.mp01_08_201402407;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 실제 화면 쓰레드
    private LunarView.LunarThread mainUIThread;

    // 실제 화면 뷰
    private LunarView mLunarView;
    static final String TAG = "test";
    private ProgressBar speedProgressBar;
    private ProgressBar fuelProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "# onCreated");
        mLunarView = (LunarView) findViewById(R.id.lunar);
        Log.d(TAG, "# myLunarView 완료");
        mainUIThread = mLunarView.getThread();
        Log.d(TAG, "# getThread 완료");

        // 그대로 id를 가져오면 그 위치에 사용가능함
        speedProgressBar = (ProgressBar) findViewById(R.id.progress_speedBar);
        fuelProgressBar = (ProgressBar) findViewById(R.id.progress_fuelBar);

        mainUIThread.speedProgressBar = speedProgressBar;
        mainUIThread.fuelProgressBar = fuelProgressBar;
        Log.d(TAG, "# progressbar 전송 완료");
    }
}
