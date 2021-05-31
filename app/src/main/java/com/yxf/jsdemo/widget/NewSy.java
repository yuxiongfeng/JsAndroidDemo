package com.yxf.jsdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.yxf.jsdemo.R;


/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/31 10:33
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/31 10:33
 */
public class NewSy extends View {
    private Paint paint;
    private String name;
    private int baseLine;
    private int containerWidth;
    private int containerHeight;

    public NewSy(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewSy(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Newsy);
        if (typedArray != null) {
            name = typedArray.getString(R.styleable.Newsy_name);
            int age = typedArray.getInteger(R.styleable.Newsy_age, 0);
            String size = typedArray.getString(R.styleable.Newsy_sizeSy);
            int height = typedArray.getInteger(R.styleable.Newsy_heightSy, 0);
            String character = typedArray.getString(R.styleable.Newsy_character);
        }
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#ffff00"));
        Rect rect = new Rect();
        paint.getTextBounds(name, 0, name.length(), rect);
        baseLine = rect.height() / 2;
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        containerWidth = MeasureSpec.getSize(widthMeasureSpec);
        containerHeight = MeasureSpec.getSize(heightMeasureSpec);
        Logger.w("width:%d, height:%d",containerWidth,containerHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(name, containerWidth / 2, containerHeight / 2 + baseLine, paint);
    }
}
