package com.example.administrator.lec04_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Stack<Double> num = new Stack<>(); // 숫자만 담는 스택
    Stack<String> strBuho = new Stack<>(); // 부호(기호)만 담는 슽백

    // 결과 계산식을 string으로 받아서 분류한다.
    public void pushNum(String line) {
        String tempNum = line.replaceAll("[^0-9&^.]", "_"); // 숫자와 .을 제외한 나머지를 전부 _로 바꾼다.
        // 숫자는 한 문자가 아니기 때문에
        String[] strNum = tempNum.split("_"); // 바꾼 String을 split한다.(배열로 담김)
        String tempbuho = line.replaceAll("[0-9&.]", ""); // 숫자와 .을 전부 공백으로 바꾼다.

        int a = strNum.length - 1; // 숫자의 개수를 구하기.
        while (a >= 0) { // 숫자가 없을 때 까지
            num.push(Double.valueOf(strNum[a])); // 스택에 숫자들을 푸시
            Log.d("MainActivity", "strNum : " + strNum[a] + ", a : " + a);
            a--;
        }
        a = tempbuho.length() - 1; // 부호(기호)의 개수를 구하기.
        while(a >= 0) {
            strBuho.push(tempbuho.substring(a, a+1)); // 스택에 부호들을 푸시
            Log.d("MainActivity", "tempbuho : " + tempbuho.substring(a, a+1) + ", a : " + a);
            a--;
        }

        calculate(); // 계산한다.
    }

    // 계산하는 함수
    public void calculate() {
        if(num.size() == 1) { // 결국 숫자 스택에 하나의 숫자만 남을 때 까지 재귀.
            return;
        }

        double x = num.pop();
        String buho = strBuho.pop();
        switch (buho) { // 각 부호에 따른 계산.
            case "+":
                num.push(x + num.pop());
                calculate();
                break;
            case "-":
                num.push(x - num.pop());
                calculate();
                break;
            case "*":
                num.push(x * num.pop());
                calculate();
                break;
            case "/":
                num.push(x / num.pop());
                calculate();
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edittext = (EditText) findViewById(R.id.edittext);
       // final EditText editTextresult = (EditText) findViewById(R.id.edittextresult);
        Button num0btn = (Button) findViewById(R.id.n0);
        Button num1btn = (Button) findViewById(R.id.n1);
        Button num2btn = (Button) findViewById(R.id.n2);
        Button num3btn = (Button) findViewById(R.id.n3);
        Button num4btn = (Button) findViewById(R.id.n4);
        Button num5btn = (Button) findViewById(R.id.n5);
        Button num6btn = (Button) findViewById(R.id.n6);
        Button num7btn = (Button) findViewById(R.id.n7);
        Button num8btn = (Button) findViewById(R.id.n8);
        Button num9btn = (Button) findViewById(R.id.n9);
        Button plusbtn = (Button) findViewById(R.id.nplus);
        Button minusbtn = (Button) findViewById(R.id.nminus);
        Button mulbtn = (Button) findViewById(R.id.nmul);
        Button divbtn = (Button) findViewById(R.id.ndiv);
        Button dotbtn = (Button) findViewById(R.id.ndot);
        Button clearbtn = (Button) findViewById(R.id.nclear);
        Button nnbtn = (Button) findViewById(R.id.nn);

        num0btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("0");
            }
        });

        num1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("1");
            }
        });
        num2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("2");
            }
        });
        num3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("3");
            }
        });
        num4btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("4");
            }
        });
        num5btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("5");
            }
        });
        num6btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("6");
            }
        });
        num7btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("7");
            }
        });
        num8btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("8");
            }
        });
        num9btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("9");
            }
        });

        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("+");
            }
        });
        minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("-");
            }
        });
        mulbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("*");
            }
        });
        divbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append("/");
            }
        });
        dotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.append(".");
            }
        });

        // "="을 클릭한 경우
        nnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = edittext.getText().toString();
                Log.d("MainActivity", "result string : "  + str);
                pushNum(str);
                double result = num.pop();
              // editTextresult.setText("" + result);
                edittext.setText("" + result);

            }
        });

        // "C" 버튼을 클릭한 경우
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.setText("");
              //  editTextresult.setText("");
                if(!num.empty()) {
                    num.pop();
                }
            }
        });
    }
}

