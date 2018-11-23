/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.mp01_08_201402407;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ProgressBar;


/**
 * View that draws, takes keystrokes, etc. for a simple LunarLander game.
 *
 * Has a mode which RUNNING, PAUSED, etc. Has a x, y, dx, dy, ... capturing the
 * current ship physics. All x/y etc. are measured with (0,0) at the lower left.
 * updatePhysics() advances the physics based on realtime. draw() renders the
 * ship, and does an invalidate() to prompt another draw() as soon as possible
 * by the system.
 */
class LunarView extends SurfaceView implements SurfaceHolder.Callback {
    public Handler keyHandler;
    static final String TAG = "test";

    /** Handle to the application context, used to e.g. fetch Drawables. */
    private Context mContext;

    /** The thread that actually draws the animation */
    private LunarThread thread;

    public LunarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        thread = new LunarThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
                Log.d(TAG, "# LunarThread.handleMessage");
            }
        });

        setFocusable(true); // make sure we get key events
    }

    /**
     * Fetches the animation thread corresponding to this LunarView.
     *
     * @return the animation thread
     */
    public LunarThread getThread() {
        Log.d(TAG, "# getThread");
        return thread;
    }


    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.d(TAG, "# surfaceChanged");
        thread.setSurfaceSize(width, height);
    }

    /*
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "# surfaceCreated");
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
        thread.setRunning(true);
        thread.start();
    }

    /*
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "# surfaceDestroyed");
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
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
        /** The drawable to use as the background of the animation canvas */
        private Bitmap mBackgroundImage;
        /**
         * Current height of the surface/canvas.
         *
         * @see #setSurfaceSize
         */
        private int mCanvasHeight = 1;

        /**
         * Current width of the surface/canvas.
         *
         * @see #setSurfaceSize
         */
        private int mCanvasWidth = 1;
        private Drawable spaceshipDrawableImage;
        private Bitmap spaceshipBitmapImage;

        // 현재 쓰레드가 Running인지 아닌지 스위치
        private boolean mRun = false;

        /** Handle to the surface manager object we interact with */
        private SurfaceHolder mSurfaceHolder;
        private Resources res;
        public LunarThread(SurfaceHolder surfaceHolder, Context context,
                           Handler handler) {
            // get handles to some important objects
            mSurfaceHolder = surfaceHolder;
            keyHandler = handler;
            mContext = context;
            res = context.getResources();

            Log.d(TAG, "# BackgroundImage 셋팅");
            mBackgroundImage = BitmapFactory.decodeResource(res,
                    R.drawable.earthrise);
        }

        /* Callback invoked when the surface dimensions change. */
        public void setSurfaceSize(int width, int height) {
            setFocusableInTouchMode(true);
            Log.d(TAG, "# setSurfaceSize");
            // synchronized to make sure these all change atomically
            synchronized (mSurfaceHolder) {
                // don't forget to resize the background image
                mCanvasWidth = width;
                mCanvasHeight = height;
                mBackgroundImage = mBackgroundImage.createScaledBitmap(
                        mBackgroundImage, width, height, true);
                x = (width / 2) - 75;
            }
        }

        /**
         * Starts the game, setting parameters for the current difficulty.
         */
        public void doStart() {
            Log.d(TAG, "# doStart");
            synchronized (mSurfaceHolder) {
            }
        }

        /**
         * Pauses the physics update & animation.
         */
        public void pause() {
            synchronized (mSurfaceHolder) {
            }
        }

        /**
         * Restores game state from the indicated Bundle. Typically called when
         * the Activity is being restored after having been previously
         * destroyed.
         *
         * @param savedState Bundle containing the game state
         */
        public synchronized void restoreState(Bundle savedState) {
            synchronized (mSurfaceHolder) {
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        draw(c);
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        /**
         * Dump game state to the provided Bundle. Typically called when the
         * Activity is being suspended.
         *
         * @return Bundle with this view's state
         */
        public Bundle saveState(Bundle map) {
            Log.d(TAG, "# saveState");
            synchronized (mSurfaceHolder) {
            }
            return map;
        }

        /**
         * Used to signal the thread whether it should be running or not.
         * Passing true allows the thread to run; passing false will shut it
         * down if it's already running. Calling start() after this was most
         * recently called with false will result in an immediate shutdown.
         *
         * @param b true to run, false to shut down
         */
        public void setRunning(boolean b) {
            Log.d(TAG, "# setRunning");
            mRun = b;
        }

        /**
         * Resumes from a pause.
         */
        public void unpause() {
            // Move the real time clock up to now
            synchronized (mSurfaceHolder) {
            }
        }
        int x = 0;
        int y = 0;
        int angle = 0;
        int y_count = 0;
        int x_count = 0;
        boolean isChange = false;
        boolean isDown = false;
        boolean isFuel = true;
        int fuel = 100;
        ProgressBar speedProgressBar;
        ProgressBar fuelProgressBar;

        // 우주선 내려오는거 등 전체적인 그림
        @RequiresApi(api = Build.VERSION_CODES.O)
        private void draw(Canvas canvas) {
            //Log.d(TAG, "# doDraw");
            canvas.drawBitmap(mBackgroundImage, 0, 0, null); // 백그라운드 이미지

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
            x = x + x_count;
            y = y + (y_count / 14); // 속도
            spaceshipDrawableImage.setBounds(x, y , x + 197, y + 236); // 애니메이션. 첫, 두 번째 : x, y 좌표 변하는값
            if( x > mCanvasWidth ) x = 0;
            if( y > mCanvasHeight ) y = 0;
            spaceshipDrawableImage.draw(canvas);

            // 속도 ProgressBar
            //progressBar.setMax(200);
            //progressBar.setProgress(y_count);
            //progressBar.setSecondaryProgress(200);

            speedProgressBar.draw(canvas);
            fuelProgressBar.draw(canvas);
            new Speed().execute(y_count);
            new Fuel().execute(fuel);
            isChange = false; // 함수 끝나는 순간 누른 것이 끝난 것으로 간주.
            isDown = false;
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
                    //    Log.d(TAG, "0 : " + y_count);
                    speedProgressBar.setProgress(y_count);
                    speedProgressBar.setSecondaryProgress(0);
                    speedProgressBar.setMax(120);
                }
                if(values[0] == 1) {
                    //    Log.d(TAG, "1 : " + y_count);
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
                Log.d(TAG, "fuel : " + fuel);
                fuelProgressBar.setProgress(fuel);
                fuelProgressBar.setMax(100);
                return null;
            }
        }
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
