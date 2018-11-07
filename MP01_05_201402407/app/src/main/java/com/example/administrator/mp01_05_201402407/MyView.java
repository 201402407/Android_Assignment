package com.example.administrator.mp01_05_201402407;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

class MyView extends View {
    private Paint mPaints, mFramePaint;
    private RectF mBigOval;
    private float mStart, mSweep;
    private static final float SWEEP_INC = 2;
    private static final float START_INC = 15;

    public MyView(Context context) {
        super(context);
          /* 실습 5-4
        mPaints = new Paint();
        mPaints.setAntiAlias(true);
        mPaints.setStyle(Paint.Style.FILL);
        mPaints.setColor(0x88FF0000);

        mFramePaint = new Paint();
        mFramePaint.setAntiAlias(true);
        mFramePaint.setStyle(Paint.Style.STROKE);

        mFramePaint.setStrokeWidth(3);
        mFramePaint.setColor(0x8800FF00);
        mBigOval = new RectF(100, 40, 900, 1000);
          */
        setBackgroundColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        /* 실습 5-10
        Paint paint = new Paint();
        Matrix m = new Matrix();
        m.preScale(1, -1);
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.house);
        Bitmap mb = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, false);
        Bitmap sb = Bitmap.createScaledBitmap(b, 500, 500, false);
        canvas.drawBitmap(mb, 0, 0, null);
        canvas.drawBitmap(sb, 300, 300, null);
        */
        /* 실습 5-9
        Matrix m = new Matrix();
        m.preScale(1, 2);
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.face);
        Bitmap mb = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, false);
        canvas.drawBitmap(b,500,0,null);
        canvas.drawBitmap(mb,0,0,null);
        */
        /* 실습 5-8
        Paint paint = new Paint();
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.cat);
        canvas.drawBitmap(b,0,0,null);
        */
        /* 실습 5-6
        Path path = new Path();
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);

        path.moveTo(20, 400);
        path.lineTo(300, 800);
        path.cubicTo(450, 120, 600, 1200, 900, 800);

        paint.setColor(Color.BLUE);
        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(200);
        canvas.drawTextOnPath("This is a test!!" , path, 0, 0, paint);
        */

        /* 실습 5-5
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(100);
        Typeface t;

        t = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
        paint.setTypeface(t);
        canvas.drawText("DEFAULT 폰트", 10, 400, paint);

        t = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL);
        paint.setTypeface(t);
        canvas.drawText("DEFAULT_BOLD 폰트", 10, 600, paint);

        t = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
        paint.setTypeface(t);
        canvas.drawText("MONOSPACE 폰트", 10, 800, paint);

        t = Typeface.create(Typeface.SERIF, Typeface.NORMAL);
        paint.setTypeface(t);
        canvas.drawText("SERIF 폰트", 10, 1000, paint);

        t = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        paint.setTypeface(t);
        canvas.drawText("SANS_SERIF 폰트", 10, 1200, paint);
        */
         /* 실습 5-4
        canvas.drawColor(Color.YELLOW);
        canvas.drawRect(mBigOval, mFramePaint);
        canvas.drawArc(mBigOval, mStart, mSweep, false, mPaints);
        mSweep += SWEEP_INC;
        if(mSweep > 360) {
            mSweep -= 360;
            mStart += START_INC;
            if(mStart >= 360)
                mStart -= 360;
        }
        invalidate();
         */


        /* 실습 5-1
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);
        canvas.drawLine(100, 100, 700, 100, paint);
        canvas.drawRect(100, 300, 700, 700, paint);
        canvas.drawCircle(300, 1200, 200, paint);
        paint.setTextSize(80);
        canvas.drawText("This is a test.", 100, 900, paint);
        */

        /* 실습 5-2
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);

        canvas.drawColor(Color.BLUE);
        canvas.drawRoundRect(new RectF(30, 50, 330, 550), 15, 15, paint);
        canvas.drawOval(new RectF(450, 50, 750, 550), paint);
        paint.setColor(Color.RED);
        canvas.drawArc(new RectF(30, 600, 330, 1100), 360, 1000, true, paint);
        paint.setColor(Color.YELLOW);
        float[] pts = {30, 1250, 300, 1350, 300, 1350, 60, 1450, 60, 1450, 360, 1500};
        paint.setStrokeWidth(10);
        canvas.drawLines(pts, paint);
        */
    }

}
