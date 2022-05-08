package com.example.v2vega109;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleButton extends View {
    //circle and text colors
    private int  circleCol;
    private Paint circlePaint;

    public CircleButton(Context context) {
        super(context);
        init();
    }

    public CircleButton(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    public CircleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CircleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        //circlePaint.setAntiAlias(true);
        circleCol = Color.YELLOW;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;
        int radius = 0;
        if(viewWidthHalf>viewHeightHalf)
            radius=viewHeightHalf-7;
        else
            radius=viewWidthHalf-7;

        circlePaint.setStyle(Style.FILL);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleCol);

        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);
        circlePaint.setStyle(Style.STROKE);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStrokeWidth(7);
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);
    }

    public int getCircleColor(){
        return circleCol;
    }

    public void setCircleColor(int newColor){
        circleCol=newColor;
        invalidate();
        requestLayout();
    }

}
