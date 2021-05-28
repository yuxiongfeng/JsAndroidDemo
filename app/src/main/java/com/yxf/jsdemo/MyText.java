package com.yxf.jsdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/24 9:43
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/24 9:43
 */
public class MyText extends View {

    private String text;
    private int size;
    private int color;
    private int type;
    private Paint mPaint;

    private int containerWidth;
    private int containerHeight;
    private int baseLine;

    public MyText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyText);
        if (typedArray != null) {
            text = typedArray.getString(R.styleable.MyText_text);
            size = typedArray.getDimensionPixelSize(R.styleable.MyText_size, 0);
            color = typedArray.getColor(R.styleable.MyText_textColor, Color.parseColor("#ffff00"));
            type = typedArray.getInteger(R.styleable.MyText_type, 0);
        }
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setTextSize(size);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Rect rect=new Rect();
        mPaint.getTextBounds(text,0,text.length(),rect);
        baseLine=rect.height();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        containerWidth = MeasureSpec.getSize(widthMeasureSpec);
        containerHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, containerWidth / 2, containerHeight / 2 + baseLine/2, mPaint);
        canvas.drawLine(0, containerHeight / 2, containerWidth, containerHeight / 2, mPaint);
    }
}
