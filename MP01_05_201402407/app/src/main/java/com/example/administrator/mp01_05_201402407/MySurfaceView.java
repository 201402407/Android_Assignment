package com.example.administrator.mp01_05_201402407;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Ball {
    int x, y, xlnc = 1, ylnc = 1;
    int diameter;
    static int WIDTH = 1080, HEIGHT = 1920;

    public Ball(int d) {
        this.diameter = d;

        x = (int) (Math.random() * (WIDTH - d) + 3);
        y = (int) (Math.random() * (HEIGHT - d) + 3);

        xlnc = (int) (Math.random() * 30 + 1);
        ylnc = (int) (Math.random() * 30 + 1);
    }

    public void paint(Canvas g) {
        Paint paint = new Paint();

        if(x < diameter || x > (WIDTH - diameter))
            xlnc = -xlnc;
        if(y < diameter || y > (HEIGHT - diameter))
            ylnc = -ylnc;

        x += xlnc;
        y += ylnc;

        paint.setColor(Color.RED);
        g.drawCircle(x, y, diameter, paint);
    }
}

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    public Ball basket[] = new Ball[10];
    private MyThread thread;

    public MySurfaceView(Context context) {
        super(context);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new MyThread(holder);

        for(int i = 0; i < 10; i++) {
            basket[i] = new Ball(100);
        }


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        thread.setRunning(false);
        while(retry) {
            try{
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {

            }
        }
    }

    private class MyThread extends Thread {

        public boolean mRun = false;
        private SurfaceHolder mSurfaceHolder;

        public MyThread(SurfaceHolder holder) {
            mSurfaceHolder = holder;
        }

        @Override
        public void run() {
            while(mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    c.drawColor(Color.BLACK);
                    synchronized (mSurfaceHolder) {
                        for(Ball b : basket) {
                            b.paint(c);
                        }
                    }
                }
                finally {
                    if(c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        public void setRunning(boolean b) {
            mRun = b;
        }
    }
}
