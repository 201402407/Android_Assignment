package example.administrator.mp01_10_201402407_lab02;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView tx, ty, tz, count, result;
    SeekBar seekBar;
    Button button;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static int SHAKE_THRESHOLD;
    long lastTime;
    float x, y, z, speed, lastX,  lastY, lastZ;

    int countNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 기본 셋팅 */
        tx = (TextView) findViewById(R.id.x);
        ty = (TextView) findViewById(R.id.y);
        tz = (TextView) findViewById(R.id.z);
        count = (TextView) findViewById(R.id.count);
        result = (TextView) findViewById(R.id.result);
        button = (Button) findViewById(R.id.resetBtn);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setProgress(0);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(seekbarListener);
        SHAKE_THRESHOLD  = 1;
        count.setText("0");

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); // 센서매니저 정의. 센서사용

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // 센서 중 가속도센서 사용

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("0");
                countNum = 0;
            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentTime = System.currentTimeMillis();
        long gabOfTime = (currentTime - lastTime);
        if (gabOfTime > 90) {
            lastTime = currentTime;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            tx.setText(x + "");
            ty.setText(y + "");
            tz.setText(z + "");
            speed = Math.abs(x + y + z - lastX - lastY - lastZ);
            Log.d("TESt", speed + "");

            // 이벤트가 발생한다.
            if (speed > SHAKE_THRESHOLD) {
                setCount();
            }

            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }

    /* 흔들기에 따른 카운트 증가 함수 */
    private void setCount() {
        countNum++;
        result.setText(countNum + "");

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 앱이 다시 켜졌을 때 가속도센서 사용하기 위해 등록.
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private SeekBar.OnSeekBarChangeListener seekbarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int threshold = seekBar.getProgress();
            threshold = threshold + 2;
            SHAKE_THRESHOLD = threshold;
            count.setText(seekBar.getProgress() + "");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
