package example.administrator.mp01_10_201402407_lab01;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener, TextToSpeech.OnInitListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final int SHAKE_THRESHOLD = 820; // 흔들기 속도 조건
    private TextToSpeech tts;
    private TextView textView;

    long lastTime;
    float x, y, z, speed, lastX,  lastY, lastZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 기본 셋팅 */
        textView = (TextView) findViewById(R.id.getNumber);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE); // 센서매니저 정의. 센서사용

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // 센서 중 가속도센서 사용

        tts = new TextToSpeech(this, this); // tts(텍스트를 음성으로 변환)
    }

    /* 가속도센서 값이 변할 때 마다 실행되는 함수 */
    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentTime = System.currentTimeMillis();
        long gabOfTime = (currentTime - lastTime);
        if (gabOfTime > 100) {
            lastTime = currentTime;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

            // 이벤트가 발생한다.
            if (speed > SHAKE_THRESHOLD) {
                getDiceNumber();
            }

            lastX = x;
            lastY = y;
            lastZ = z;
        }

    }

    /* 주사위를 위한 랜덤으로 값 얻는 함수 */
    private void getDiceNumber() {
        Random random = new Random();
        int num = random.nextInt(6) + 1; // 0~5 + 1까지.
        textView.setText(num + "");
        tts.setPitch(1.2f);         // 음성 톤은 약간높게
        tts.setSpeechRate(1.1f);    // 읽는 속도를 1.1배 설정
        tts.speak(Integer.toString(num), TextToSpeech.QUEUE_FLUSH, null); // num 숫자를 읽는다.
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onInit(int status) {
        // 만약 TextToSpeech의 현재 상태가 오류가 안났으면 진행
        if(status != TextToSpeech.ERROR) {
            Locale language = getResources().getConfiguration().locale; // 현재 디바이스의 설정한 언어 불러옴
            tts.setLanguage(language); // 현재 디바이스의 언어로 TextToSpeech 진행.

        }
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

    /* 종료시 TextToSpeech 종료하는 함수 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거한다.
        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }
}
