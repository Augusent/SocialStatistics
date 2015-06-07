package com.serovr.vkspy.app.charting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.serovr.vkspy.app.R;

public class PieChartView extends View {
    private Paint slicePaint;
    private Paint linePaint;
    private Paint labelPaint;
    private Paint textPaint;
    private int[] sliceClrs = {getResources().getColor(R.color.dOnline), getResources().getColor(R.color.mOnline),
                               getResources().getColor(R.color.Offline)};
    private Path path;
    private Matrix matrix;
    private RectF oval;
    private int bisX, bisY;
    private float[] mData;

    public PieChartView(Context context, AttributeSet attrs){
        super(context, attrs);
        // Paints
        slicePaint = new Paint();
        linePaint = new Paint();
        labelPaint = new Paint();
        textPaint = new Paint();

        //Path & Matrix
        path = new Path();
        matrix = new Matrix();

        slicePaint.setAntiAlias(true);
        slicePaint.setDither(true);
        slicePaint.setStyle(Paint.Style.FILL);

        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(3.0f);
        linePaint.setStyle(Paint.Style.STROKE);

        labelPaint.setAntiAlias(true);
        labelPaint.setColor(Color.WHITE);
        labelPaint.setStyle(Paint.Style.FILL);

        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(20);
    }

    private float[] scale(){
        float[] scaledValues = new float[this.mData.length];
        float total = getTotal(); //Total all values supplied to the chart
        for (int i = 0; i < this.mData.length; i++) {
            scaledValues[i] = (this.mData[i] / total) * 360; //Scale each value
        }
        return scaledValues;
    }

    private float getTotal() {
        float total = 0;
        for (float val : this.mData)
            total += val;
        return total;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(this.mData != null) {

            path.reset();
            int startTop = 0;
            int startLeft = 0;
            int endBottom = getHeight();
            int endRight = endBottom; //To make this an equal square
            //Create the box
            oval = new RectF(startLeft, startTop, endRight, endBottom); //Creating the box

            float[] scaledValues = scale(); //Get the scaled values
            float sliceStartPoint = 0;
            for (int i = 0; i < scaledValues.length; i++) {
                bisectorXY(canvas, sliceStartPoint, scaledValues[i], oval);
                //Цвет сектора
                slicePaint.setColor(sliceClrs[i]);
                //Рисуем сектор
                canvas.drawArc(oval, sliceStartPoint, scaledValues[i], true, slicePaint);
                //Рисуем контур
                canvas.drawArc(oval, sliceStartPoint, scaledValues[i], true, linePaint);
                //Рисуем лейбл в текущем куске
                canvas.drawCircle(bisX, bisY, 30, labelPaint);
                float labelValue = ((int) ((scaledValues[i] * 100 / 360) * 100));
                canvas.drawText("" + labelValue / 100 + "%", bisX - 25, bisY + 4, textPaint);


/*
                path.moveTo(oval.centerX(), oval.centerY());
                path.lineTo(oval.width(), oval.centerY());
                path.arcTo(oval, sliceStartPoint, scaledValues[i]);
                path.lineTo(oval.centerX(), oval.centerY());
                canvas.drawPath(path, slicePaint);

*/
                sliceStartPoint += scaledValues[i]; //Update starting point of the next slice

            }

        }
    }

    private void moveSlice(int distance){
        int rectCenterX = (int) oval.centerX();
        int rectCenterY = (int) oval.centerY();

        int newCenterX = (int)((bisX - rectCenterX)*0.08);
        int newCenterY = (int)((bisY - rectCenterY)*0.08);

        oval.offsetTo(newCenterX, newCenterY);
        bisX+=newCenterX;
        bisY+=newCenterY;
    }

    private void bisectorXY(Canvas canvas, float angleStart, float angleValue, RectF rect){
        int X = 0, Y = 0;
        int dX =0, dY = 0;
        double DXY_RATIO = 0.7;
        int radius = (int) (rect.width()/2);
        int dXY = (int) (radius * DXY_RATIO);

        float angleFullBisector = angleStart + (angleValue/2);

        int calculatedAngle = calcAngle(angleStart, angleValue);
//        Log.d("calculatedAngle", " ="+calculatedAngle);

        if(angleFullBisector >= 0 && angleFullBisector < 90){
                dX = (int)Math.round(Math.cos(Math.toRadians(calculatedAngle)) * dXY);
                dY = (int)Math.round(Math.sin(Math.toRadians(calculatedAngle)) * dXY);
            X = dX + radius;
            Y = dY + radius;
        }

        if(angleFullBisector >= 90 && angleFullBisector < 180){
                dX = (int)Math.round(Math.sin(Math.toRadians(calculatedAngle)) * dXY);
                dY = (int)Math.round(Math.cos(Math.toRadians(calculatedAngle)) * dXY);
            X = radius - dX;
            Y = dY + radius;
        }

        if(angleFullBisector >= 180 && angleFullBisector < 270){
                dX = (int)Math.round(Math.cos(Math.toRadians(calculatedAngle)) * dXY);
                dY = (int)Math.round(Math.sin(Math.toRadians(calculatedAngle)) * dXY);
            X = radius - dX;
            Y = radius - dY;
        }

        if(angleFullBisector >= 270 && angleFullBisector < 360){
                dX = (int)Math.round(Math.sin(Math.toRadians(calculatedAngle)) * dXY);
                dY = (int)Math.round(Math.cos(Math.toRadians(calculatedAngle)) * dXY);
            X = dX + radius;
            Y = radius - dY;
        }

        bisX = X;
        bisY = Y;


    }

    private int calcAngle(float _angleStart, float _angleValue){
        float angleSum = _angleStart + _angleValue;
        float angleBis = _angleValue/2;
        float angle = angleSum - angleBis;
        float resultAngle;

        if(angle >= 90 && angle < 180){
            resultAngle = angleSum - 90 - angleBis;
            return (int) resultAngle;
        }
        if(angle >= 180 && angle < 270){
            resultAngle = angleSum - 180 - angleBis;
            return (int) resultAngle;
        }
        if(angle >= 270 && angle < 360){
            resultAngle = angleSum - 270 - angleBis;
            return (int) resultAngle;
        }
//        Log.d("Calculated angle is: ", ""+angle);
        return  Math.round(angle);

    }


    public void setDataPoints(float[] data) {
        this.mData = data;
        invalidate(); //Tells the chart to redraw itself
    }

}
