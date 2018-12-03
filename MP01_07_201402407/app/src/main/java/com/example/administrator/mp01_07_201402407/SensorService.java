package com.example.administrator.mp01_07_201402407;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class SensorService extends Service implements SensorEventListener {
    final static String MY_ACTION = "com.example.administrator.mp01_07_201402407.SensorService.MY_ACTION";
    MediaPlayer player;

    //Using the Accelometer & Gyroscoper
    private SensorManager mySensorManager = null;
    private boolean music_onoff = false;

    //Using the Accelometer
    private SensorEventListener mAccLis;
    private List<Sensor> myAccelometerSensor;

    public SensorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        //Using the Gyroscope & Accelometer
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Using the Accelometer
        myAccelometerSensor = mySensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        for (Sensor sensor : myAccelometerSensor) {
            mySensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        StringBuilder builder = new StringBuilder();
        StringBuilder compare_builder = new StringBuilder(); // 비교하기 위해 StringBuilder 하나 생성.
        String result;
        String temp[];

        for (int i = 0; i < sensorEvent.values.length; i++) {
            builder.append(" [");
            builder.append(i);
            builder.append("] = ");
            builder.append(sensorEvent.values[i]);
            compare_builder.append(sensorEvent.values[i]);
            compare_builder.append("_");
            builder.append("\n");
        }

        result = builder.toString();
        temp = compare_builder.toString().split("_");
    //    Log.d("test", temp[0] + "," + temp[1] + "," + temp[2]);
        if (temp[0].equals("0.0") && temp[1].equals("9.81")) {
          //  Log.d("test", "여긴 중지1");
            if(temp[2].equals("0.0")) {
            //    Log.d("test", "여긴 중지2");
                if (music_onoff) {
           //         Log.d("test", "여긴 중지");
                    Toast.makeText(this, "Music Service가 중지되었습니다.", Toast.LENGTH_LONG).show();
                    player.stop();
                    music_onoff = false;
                }
            }
        }

        if (temp[0].equals("-8.57617E-7") && temp[1].equals("-9.81")) {
         //   Log.d("test", "여긴 시작1");
            if (temp[2].equals("0.0")) {
             //   Log.d("test", "여긴 시작2");
                if (!music_onoff) {
                //    Log.d("test", "여긴 시작");
                    Toast.makeText(this, "Music Service가 시작되었습니다.", Toast.LENGTH_LONG).show();
                    player = MediaPlayer.create(this, R.raw.old_pop);
                    player.setLooping(false); // Set Looping
                    player.start();
                    music_onoff = true;

                }
            }
        }

        Intent intent = new Intent(MY_ACTION); // MY_ACTION을 인텐트에 담기
        intent.putExtra("result", result); // 결과값도
        //Log.d("Sensor", "-> onReceive" + result);
        sendBroadcast(intent); // 브로드캐스트로 보내기
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
