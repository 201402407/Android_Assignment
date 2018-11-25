package com.example.administrator.mp01_08_201402407;
// 201402407 이해원

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

class LunarView extends SurfaceView implements SurfaceHolder.Callback {
    public Handler keyHandler;
    static final String TAG = "test";
    private Context mContext;
    private LunarThread thread;
    private SurfaceHolder mSurfaceHolder;

    // 초기 세팅. 시작 단계라는 뜻.
    gameResult game_result;
    int score = 0; // 승리횟수
    enum gameResult {
        GAME_WIN,
        GAME_LOSE,
        GAME_PAUSE,
        GAME_RESUME,
        GAME_ING,
        GAME_START
    }

    public LunarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new LunarThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
                Log.d(TAG, "# LunarThread.handleMessage");
            }
        });

        setFocusable(true); // make sure we get key events
    }

    public LunarThread getThread() {
        Log.d(TAG, "# getThread");
        return thread;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.d(TAG, "# surfaceChanged");
        thread.setSurfaceSize(width, height);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "# surfaceCreated");
        game_result = gameResult.GAME_START;
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "# surfaceDestroyed");
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    class LunarThread extends Thread {

        private Bitmap mBackgroundImage;
        private int mCanvasHeight = 1;
        private int mCanvasWidth = 1;
        private Drawable spaceshipDrawableImage;
        private Bitmap spaceshipBitmapImage;

        // 현재 쓰레드가 Running인지 아닌지 스위치
        private boolean mRun = false;
        private Resources res;
        public LunarThread(SurfaceHolder surfaceHolder, Context context,
                           Handler handler) {
            mSurfaceHolder = surfaceHolder;
            keyHandler = handler;
            mContext = context;
            res = context.getResources();

            Log.d(TAG, "# BackgroundImage 셋팅");
            mBackgroundImage = BitmapFactory.decodeResource(res,
                    R.drawable.earthrise);
        }

        public void setSurfaceSize(int width, int height) {
            setFocusableInTouchMode(true);
            Log.d(TAG, "# setSurfaceSize");
            synchronized (mSurfaceHolder) {
                mCanvasWidth = width;
                mCanvasHeight = height;
                mBackgroundImage = mBackgroundImage.createScaledBitmap(
                        mBackgroundImage, width, height, true);
                x = (width / 2) - 75;
            }
        }

        public void doStart() {
            Log.d(TAG, "# doStart");
            synchronized (mSurfaceHolder) {
            }
        }
        public void pause() {
            synchronized (mSurfaceHolder) {
            }
        }
        public synchronized void restoreState(Bundle savedState) {
            synchronized (mSurfaceHolder) {
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            Canvas c = null;
            while (mRun) {
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        draw(c);
                    }
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        public Bundle saveState(Bundle map) {
            Log.d(TAG, "# saveState");
            synchronized (mSurfaceHolder) {
            }
            return map;
        }

        public void setRunning(boolean b) {
            Log.d(TAG, "# setRunning");
            mRun = b;
        }

        public void unpause() {
            // Move the real time clock up to now
            synchronized (mSurfaceHolder) {
            }
        }

        int x = 0;
        int y = 0;
        int angle = 0;
        int y_count = 10;
        int x_count = 0;
        int fuel = 100;

        boolean isChange = false;
        boolean isDown = false;
        boolean isFuel = true;

        ProgressBar speedProgressBar;
        ProgressBar fuelProgressBar;

        Random random = new Random();
        int startX = 0;
        int stopX = 0;

        // 초기 세팅. 시작 단계라는 뜻.
        //gameResult game_result = gameResult.GAME_START;

        // 우주선 내려오는거 등 전체적인 그림
        @RequiresApi(api = Build.VERSION_CODES.O)
        private void draw(Canvas canvas) {
            Log.d(TAG, "# doDraw : " +game_result);
            canvas.drawBitmap(mBackgroundImage, 0, 0, null); // 백그라운드 이미지

            //Log.d(TAG, "here");
            // 결승선 그리기
            Paint finish_paint = new Paint();
            finish_paint.setStrokeWidth(12f);
            finish_paint.setStyle(Paint.Style.FILL);
            finish_paint.setColor(Color.RED);

            if(game_result == gameResult.GAME_START) {
                // 1 ~ mCanvasWidth / 3까지의 랜덤 수.
                startX = random.nextInt(mCanvasWidth / 3) + 1;
                Log.d(TAG, " start : " + startX);
                // mCanvasWidth /2 ~ mCanvasWidth까지 랜덤 수.
                stopX = random.nextInt(mCanvasWidth - (mCanvasWidth / 2) + 1) + (mCanvasWidth / 2);
                Log.d(TAG, " stop : " + stopX);
                printGameNoticeText(canvas, "Lunar Lander \n" + "Press Up To Play");
                setRunning(false);

                return;
            }

            canvas.drawLine(startX, mCanvasHeight, stopX, mCanvasHeight, finish_paint);

            if(!isDown)
                spaceshipBitmapImage = BitmapFactory.decodeResource(res, R.drawable.lander_plain); // 초기값 Bitmap 객체 세팅
            if(isDown)
                spaceshipBitmapImage = BitmapFactory.decodeResource(res, R.drawable.lander_firing); // 초기값 Bitmap 객체 세팅
            // 밑으로 내려오는 증가값
            spaceshipDrawableImage = getRotateDrawable(spaceshipBitmapImage, angle);
            // 아무 키도 누르지 않을 경우 자동으로 속도 증가.
            if((!isChange) && (y_count < 120)) {
                y_count++;
            }
            Log.d(TAG, "y : " + y);
            x = x + x_count;
            y = y + (y_count / 10) + 1; // 속도
            spaceshipDrawableImage.setBounds(x, y , x + 197, y + 236); // 애니메이션. 첫, 두 번째 : x, y 좌표 변하는값
            if( x > mCanvasWidth )  {
                x = 0;
            }

            // ProgressBar 그리기
            speedProgressBar.draw(canvas);
            fuelProgressBar.draw(canvas);
            // ProgressBar 실행시키기 위한 AsyncTask 작업
            new Speed().execute(y_count);
            new Fuel().execute(fuel);

            isChange = false; // 함수 끝나는 순간 누른 것이 끝난 것으로 간주.
            isDown = false;

            // 게임이 끝나는 기본 전제 조건
            if(y >= (mCanvasHeight - 220)  && game_result == gameResult.GAME_ING) {
                // 도착 선에 닿지 않은 경우
                if(x < startX || x > stopX) {
                    Log.d(TAG, "선 안밟아서 패배!");
                    game_result = gameResult.GAME_LOSE;
                    printGameNoticeText(canvas, "No Finish Line _" + "Game Over _" + "Press Up To Play");
                }
                // 기울어져 있는 경우 ( 20도 이상 좌우 중 하나라도 기울면 실패 )
                else if(angle > 30 || angle < -30) {
                    Log.d(TAG, "기울어져서 패배!");
                    game_result = gameResult.GAME_LOSE;
                    printGameNoticeText(canvas, "Too Lean _" + "Game Over _" + "Press Up To Play");
                }
                // 안전속도 이상으로 낙하한 경우
                else if(y_count > 60) {
                    Log.d(TAG, "속도 빨라서 패배!");
                    game_result = gameResult.GAME_LOSE;
                    printGameNoticeText(canvas, "Too Fast _" + "Game Over _" + "Press Up To Play");
                }
                else {
                    game_result = gameResult.GAME_WIN;
                    printGameNoticeText(canvas, "Success _" + score + " in a row _" + "Press Up To Play");
                    score++;
                }

                // 패배조건 만족하면
                if(game_result == gameResult.GAME_LOSE) {
                    spaceshipBitmapImage = BitmapFactory.decodeResource(res, R.drawable.lander_crashed);
                    spaceshipDrawableImage = getRotateDrawable(spaceshipBitmapImage, angle);
                    spaceshipDrawableImage.setBounds(x, y , x + 197, y + 236);
                    setRunning(false);
                    Log.d(TAG, "패배");
                }
                if(game_result == gameResult.GAME_WIN) {
                    setRunning(false);
                    Log.d(TAG, "성공");
                }
            }
            spaceshipDrawableImage.draw(canvas);
        }

        /*
        @TargetApi(Build.VERSION_CODES.O)
        @RequiresApi(api = Build.VERSION_CODES.N)
        private ProgressBar setFuelProgressBar() {
            ProgressBar progressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
            progressBar.setProgress(0);
            progressBar.setMax(120);
          //  progressBar = (ProgressBar) findViewById(R.id.progress_bar);
           // progressBar.drawableHotspotChanged(100, 100);
            //progressBar.setMin(0);
            return progressBar;
        }
        */

        private Drawable getRotateDrawable(final Bitmap b, final float angle) {
            final BitmapDrawable drawable = new BitmapDrawable(getResources(), b) {
                @Override
                public void draw(final Canvas canvas) {
                    canvas.save();
                    canvas.rotate(angle, x + 98, y + 168);
                    super.draw(canvas);
                    canvas.restore();
                }
            };
            return drawable;
        }

        private class Speed extends AsyncTask<Integer, Integer, Integer> {
            @Override
            protected void onPreExecute() {
            }
            @Override
            protected Integer doInBackground(Integer... value) {
                if(y_count <= 60) {
                    publishProgress(0);
                }
                else {
                    publishProgress(1);
                }
                return null;
            }
            @Override
            protected void onProgressUpdate(Integer... values) {
                if(values[0] == 0) {
                    speedProgressBar.setProgress(y_count);
                    speedProgressBar.setSecondaryProgress(0);
                    speedProgressBar.setMax(120);
                }
                if(values[0] == 1) {
                    speedProgressBar.setSecondaryProgress(y_count);
                }
            }
        }

        private class Fuel extends AsyncTask<Integer, Integer, Integer> {
            @Override
            protected void onPreExecute() {
            }
            @Override
            protected Integer doInBackground(Integer... value) {
              //  Log.d(TAG, "fuel : " + fuel);
                fuelProgressBar.setProgress(fuel);
                fuelProgressBar.setMax(100);
                return null;
            }
        }

        private void printGameNoticeText(Canvas canvas, String string) {

            Paint paint = new Paint();
            paint.setColor(Color.GRAY);
            paint.setTextSize(50);
            paint.setTextAlign(Paint.Align.CENTER);
            String[] temp = string.split("_");
            for(int i = 0; i < temp.length; i++) {
                canvas.drawText("" + temp[i], mCanvasWidth / 2, (mCanvasHeight / 2) + (50 * i), paint);
            }
        }
    }

    public void threadInit() {
        thread.x = 465;
        thread.y = 0;
        thread.angle = 0;
        thread.x_count = 0;
        thread.y_count = 10;
        thread.fuel = 100;

        thread.isChange = false;
        thread.isDown = false;
        thread.isFuel = true;
    }
    //화면 터치 이벤트
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:    //화면을 터치했을때
                Log.d(TAG , "Touch");
                Log.d(TAG, "" + game_result);
                if(game_result == gameResult.GAME_START) {
                    game_result = gameResult.GAME_ING;
                    thread.setRunning(true);
                    threadInit();
                    thread.start();
                }
                if(game_result == gameResult.GAME_WIN ||
                        game_result == gameResult.GAME_LOSE) {
                    game_result = gameResult.GAME_START;
                    thread.setRunning(true);
                    thread.start();
                }
                break;
            case MotionEvent.ACTION_UP:    //화면을 터치했다 땠을때
                break;
            case MotionEvent.ACTION_MOVE:    //화면을 터치하고 이동할때
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        super.onKeyDown(KeyCode, event);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (KeyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                //    Log.d(TAG, "왼쪽");
                    thread.angle = thread.angle + 2;
                    if(thread.angle % 6 == 0)
                        thread.x_count++;
                    thread.isChange = true;
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    //Log.d(TAG, "오른쪽");
                    thread.angle = thread.angle - 2;
                    if(thread.angle % 6 == 0)
                        thread.x_count--;
                    thread.isChange = true;
                    return true;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    //Log.d(TAG, "아래쪽");
                    thread.isChange = true;
                    if(thread.y_count >= 120) {
                        thread.y_count = 120;
                        return true;
                    }
                    thread.y_count = thread.y_count + 2;
                    return true;
                case KeyEvent.KEYCODE_DPAD_UP:
                    //Log.d(TAG, "위쪽");
                    if(thread.isFuel) {
                        thread.isChange = true;
                        thread.isDown = true;
                        thread.fuel--;
                        if(thread.fuel <= 0) // 연료 없으면 부스트 X
                            thread.isFuel = false;
                        if (thread.y_count <= 10) { // 최소 속도 유지
                            thread.y_count = 10;
                            return true;
                        }
                        thread.y_count = thread.y_count - 2; // 부스트 감속
                        return true;
                    }
                    return false;
            }
        }
        return false;
    }

}
