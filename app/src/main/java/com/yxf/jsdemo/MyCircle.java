package com.yxf.jsdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/21 9:39
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/21 9:39
 */
public class MyCircle extends View {
    private int internalColor;
    private int internalRadius;
    private int externalColor;
    private int externalRadius;
    private int externalStrokeWidth;

    private Paint internalPaint;
    private Paint externalPaint;

    private int viewWidth;
    private int viewHeight;

    public MyCircle(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //get attr
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCircle);
        if (typedArray!=null) {
            internalColor = typedArray.getColor(R.styleable.MyCircle_color, Color.parseColor("#ffff00"));
            internalRadius = typedArray.getDimensionPixelSize(R.styleable.MyCircle_radius, 50);
            externalColor = typedArray.getColor(R.styleable.MyCircle_externalColor, Color.parseColor("#ff00ff"));
            externalRadius = typedArray.getDimensionPixelSize(R.styleable.MyCircle_externalRadius, 100);
            externalStrokeWidth=typedArray.getDimensionPixelSize(R.styleable.MyCircle_externalStrokeWidth,20);
        }
        typedArray.recycle();
        initPaint();
    }

    private void initPaint(){
        internalPaint=new Paint();
        internalPaint.setColor(internalColor);
        internalPaint.setAntiAlias(true);
        externalPaint=new Paint();
        externalPaint.setColor(externalColor);
        externalPaint.setAntiAlias(true);
        externalPaint.setStyle(Paint.Style.STROKE);
        externalPaint.setStrokeWidth(externalStrokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth=MeasureSpec.getSize(widthMeasureSpec);
        viewHeight=MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(viewWidth/2,viewHeight/2,internalRadius,internalPaint);
        canvas.drawCircle(viewWidth/2,viewHeight/2,externalRadius,externalPaint);
    }

}
