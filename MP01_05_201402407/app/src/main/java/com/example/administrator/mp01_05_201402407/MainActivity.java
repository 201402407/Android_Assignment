package com.example.administrator.mp01_05_201402407;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    MySurfaceView view;
    AnimationDrawable rocketAnimation;

    private LinearLayout layout;
    private Button fadeButton, slideButton, explodeButton;
    private ImageView imageView, imageView2;
    boolean visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
       // view = new MySurfaceView(this);
       // setContentView(view);
        setContentView(R.layout.activity_main);


      //  /* 실습 5-12
        ImageView rocketImage = (ImageView) findViewById(R.id.imageView1);
        rocketImage.setBackgroundResource(R.drawable.anim);
        rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
     //   */

        /* 실습 5-11
        layout = (LinearLayout) findViewById(R.id.layout);
        fadeButton = (Button) findViewById(R.id.fade);
        slideButton = (Button) findViewById(R.id.slide);
        explodeButton = (Button) findViewById(R.id.explode);
        imageView = (ImageView) findViewById(R.id.imageview);
        imageView2 = (ImageView) findViewById(R.id.imageview2);

        fadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(layout, new Fade());
                visible = !visible;
                imageView.setVisibility(visible ? View.VISIBLE : View.GONE);
                imageView2.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });

        slideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(layout, new Slide());
                visible = !visible;
                imageView.setVisibility(visible ? View.VISIBLE : View.GONE);
                imageView2.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });

        explodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(layout, new Explode());
                visible = !visible;
                imageView.setVisibility(visible ? View.VISIBLE : View.GONE);
                imageView2.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });
        */
      //  MyView v = new MyView(this);
      //  setContentView(v);
      //  CustomView cv = new CustomView(this);
      //   setContentView(cv);
      //  setContentView(new MyView(this)); // 실습 5-8
       // setContentView(R.layout.activity_main);
       // setContentView(R.layout.activity_main); 실습 5-7
    }

    ///* 실습 5-12
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            rocketAnimation.start();
            return true;
        }
        return super.onTouchEvent(event);
    }
    //*/
    /*
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    */
}
