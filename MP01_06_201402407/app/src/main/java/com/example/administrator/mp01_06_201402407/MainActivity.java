package com.example.administrator.mp01_06_201402407;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final int GET_RESULT = 1;
    static int pizzaselect = 0;
    TextView order_result;
    RadioButton domino, mrpizza, pizzahut; // 라디오버튼 선언
    Button orderbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 라디오 버튼 참조
        domino = (RadioButton) findViewById(R.id.DominoPizza);
        mrpizza = (RadioButton) findViewById(R.id.MrPizza);
        pizzahut = (RadioButton) findViewById(R.id.PizzaHut);

        // 주문갯수 textview 참조
        order_result = (TextView) findViewById(R.id.TextView);
        order_result.setPaintFlags(order_result.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // textview에 밑줄 추가.

        // 주문하기 버튼 참조.
        orderbtn = (Button) findViewById(R.id.OrderBtn);

        // 주문하기 버튼 클릭 이벤트
        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);

                startActivityForResult(intent, GET_RESULT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GET_RESULT) {
            if(resultCode == RESULT_OK) {
                String PizzaNum = data.getStringExtra("PIZZA");
                String SpaghettiNum = data.getStringExtra("SPAGHETTI");

                order_result.setText("피자=" + PizzaNum + ", 스파게티=" + SpaghettiNum);
            }
        }
    }

    public void Pizza_Select_Check() {
        Intent intent = null;

        // 라디오버튼 선택한 것 분류
        if(domino.isChecked())
            pizzaselect = 1;
        if(mrpizza.isChecked())
            pizzaselect = 2;
        if(pizzahut.isChecked())
            pizzaselect = 3;
    }

    public void clicked(View view) {
        Intent intent = null;
        Pizza_Select_Check(); // 라디오버튼 체크 확인
        switch (view.getId()) {
            case R.id.CallBtn:
                if(pizzaselect == 1)
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+82)15773082"));
                if(pizzaselect == 2)
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+82)15770077"));
                if(pizzaselect == 3)
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+82)15885588"));
                break;
            case R.id.InternetBtn:
                if(pizzaselect == 1)
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.dominos.co.kr/main"));
                if(pizzaselect == 2)
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mrpizza.co.kr/index"));
                if(pizzaselect == 3)
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pizzahut.co.kr"));
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }
}
