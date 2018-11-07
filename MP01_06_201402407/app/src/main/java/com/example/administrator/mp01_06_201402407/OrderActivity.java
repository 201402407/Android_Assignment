package com.example.administrator.mp01_06_201402407;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class OrderActivity extends Activity {
    static int pizzanum = 0;
    static int spaghettinum = 0;

    TextView pizzacount, spaghetticount;
    Button okbtn, pizzaplusbtn, pizzaminusbtn, spaplusbtn, spaminusbtn;
    CheckBox pizza, spaghetti;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // 체크박스 참조
        pizza = (CheckBox) findViewById(R.id.PizzaBox);
        spaghetti = (CheckBox) findViewById(R.id.SpaghettiBox);
        pizza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    pizzaplusbtn.setEnabled(true);
                    pizzaminusbtn.setEnabled(true);
                }
                else{
                    pizzaplusbtn.setEnabled(false);
                    pizzaminusbtn.setEnabled(false);
                    pizzacount.setText(0);
                    pizzanum = 0;
                }
            }
        });
        spaghetti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    spaplusbtn.setEnabled(true);
                    spaminusbtn.setEnabled(true);
                }
                else {
                    spaplusbtn.setEnabled(false);
                    spaminusbtn.setEnabled(false);
                    spaghetticount.setText(0);
                    spaghettinum = 0;
                }
            }
        });
        // TextView 참조
        pizzacount = (TextView) findViewById(R.id.PizzaCount);
        spaghetticount = (TextView) findViewById(R.id.SpaghettiCount);

        // 버튼 참조
        pizzaplusbtn = (Button) findViewById(R.id.PizzaPlusBtn);
        pizzaminusbtn = (Button) findViewById(R.id.PizzaMinusBtn);
        spaplusbtn = (Button) findViewById(R.id.SpaghettiPlusBtn);
        spaminusbtn = (Button) findViewById(R.id.SpaghettiMinusBtn);
        okbtn = (Button) findViewById(R.id.OkBtn);

        pizzaplusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzanum++;
                pizzacount.setText(pizzanum);
            }
        });
        pizzaminusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pizzanum == 0)
                    return;
                pizzanum--;
                pizzacount.setText(pizzanum);
            }
        });
        spaplusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spaghettinum++;
                spaghetticount.setText(spaghettinum);
            }
        });
        spaminusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spaghettinum == 0)
                    return;
                spaghettinum--;
                spaghetticount.setText(spaghettinum);
            }
        });
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("PIZZA", pizzacount.getText().toString());
                intent.putExtra("SPAGHETTI", spaghetticount.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onclicked(View view) {
        switch (view.getId()) {
            case R.id.PizzaPlusBtn:
                break;
            case R.id.PizzaMinusBtn:
                break;
            case R.id.SpaghettiPlusBtn:
                break;
            case R.id. SpaghettiMinusBtn:
                break;
        }
    }

}
