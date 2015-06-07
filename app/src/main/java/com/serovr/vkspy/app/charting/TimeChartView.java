package com.serovr.vkspy.app.charting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class TimeChartView extends View {
    private Paint slicePaint;
    private Paint linePaint;
    private Paint clockPaint;
    private Rect rect;
    public TimeChartView(Context context, AttributeSet attrs){
        super(context, attrs);

        slicePaint = new Paint();
        linePaint = new Paint();
        clockPaint = new Paint();

        slicePaint.setAntiAlias(true);
        slicePaint.setStyle(Paint.Style.FILL);

        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.WHITE);

        clockPaint.setAntiAlias(true);
        clockPaint.setStyle(Paint.Style.STROKE);
        clockPaint.setColor(Color.BLACK);
        clockPaint.setStrokeWidth(8.0f);

    }

    @Override
    protected void onDraw(Canvas canvas){
        // Рисуем часы
        drawClock(canvas);

    }

    private void drawClock(Canvas canvas){
        Paint digitPaint = new Paint();
        Paint whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.FILL);
        digitPaint.setAntiAlias(true);
        digitPaint.setColor(Color.BLACK);
        digitPaint.setStyle(Paint.Style.FILL);
        digitPaint.setTextSize(50);

        float centerClockX, centerClockY;
        float clockRadius;
        int ROTATING_ANGLE = 30;

        RectF clockRect = new RectF(50,50,getHeight()-50,getHeight()-50);
        centerClockX = clockRect.centerX();
        centerClockY = clockRect.centerY();
        clockRadius = clockRect.width()/2;

        int startAngle = 0;
        for(int i=0; i<12; i++){
            canvas.drawArc(clockRect, startAngle, ROTATING_ANGLE, true, clockPaint);
            startAngle+=ROTATING_ANGLE;
        }
        canvas.drawCircle(centerClockX, centerClockY, clockRadius-10, whitePaint);

        startAngle = 0;
        for(int i=0; i<4; i++){
            canvas.drawArc(clockRect, startAngle, 90, true, clockPaint);
            startAngle+=90;
        }
        canvas.drawCircle(centerClockX, centerClockY, clockRadius-25, whitePaint);


    }

    private void drawSlices(Canvas canvas){

    }

    public void drawYourself(){
        invalidate();
    }

}
