package com.example.administrator.mp01_08_201402407;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    // 실제 화면 쓰레든
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

        speedProgressBar = (ProgressBar) findViewById(R.id.progress_speedBar);
        fuelProgressBar = (ProgressBar) findViewById(R.id.progress_speedBar);
        mainUIThread.speedProgressBar = speedProgressBar;
        mainUIThread.fuelProgressBar = fuelProgressBar;
        Log.d(TAG, "# progressbar 전송 완료");
    //    new CounterTask().execute(0);
    }

    /*
    private class CounterTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... integers) {
            return null;
        }
    }
    */
}
