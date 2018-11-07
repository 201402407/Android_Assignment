package com.example.administrator.mp01_04_201402407;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button selectbtn;
    TextView textView;
    private String TAG = "MainActivity";
    String datepicker_result, timepicker_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectbtn = (Button)findViewById(R.id.btn);
        textView = (TextView)findViewById(R.id.textview);
        DatePicker_init();
        Log.d("MainActivity", "# onCreate");
    }

    // DatePicker 출력하는 함수.
    private void DatePicker_init() {
        // Calendar 객체를 이용하여 Date, Time Picker에 날짜 값 넣기.
        final Calendar cal = Calendar.getInstance();

        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog DatePicker = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new DatePickerDialog.OnDateSetListener() {

                    // 사용자가 날짜를 선택하면
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        datepicker_result = String.format("%d.%d.%d", year, month + 1, date);

                        // 곧바로 TimePicker 실행.
                        TimePicker_init(datepicker_result);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


               // DatePicker.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션

                DatePicker.show();
                Log.d(TAG, "please...");
            }

            // TimePicker 출력하는 함수.
            private void TimePicker_init(final String datepicker_result) {

                TimePickerDialog TimePicker = new TimePickerDialog(MainActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timepicker_result = String.format("-%d:%d", hourOfDay, minute);

                        String result = datepicker_result + timepicker_result;
                        Log.d(TAG, result);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd-hh:mm");
                        Date date = null;
                        try {
                            date = simpleDateFormat.parse(result);
                            String temp = new SimpleDateFormat("yyyy.MM.dd-hh:mm").format(date);
                            textView.setText(temp + "");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //textView.setText(print[0] + "." + print[1] + "." + print[2] + "-" + print[3] + ":" + print[4]);
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지
                TimePicker.show();
            }
        });
    }
}
