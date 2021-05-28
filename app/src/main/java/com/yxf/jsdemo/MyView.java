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

import com.orhanobut.logger.Logger;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @Description:自定义view
 * 步骤：
 * 1.定义属性  xml/value/attrs.xml  确定view的属性
 * @Author: yxf
 * @CreateDate: 2021/5/19 14:38
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/19 14:38
 */
public class MyView extends View {
    public static final String TAG = "MyView---";
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private int defaultCircleColor = Color.parseColor("#fff000");
    private int defaultCircleRadius = 50;//这里表示px
    private int circleColor;
    private int circleRadius;

    private String text;
    private int textColor;
    private int textSize;

    /**
     * view的宽高
     */
    private int viewWidth;
    private int viewHeight;
    private Rect mBound;

    public MyView(Context context) {
        this(context, null);
        Logger.w(TAG + "MyView(Context context)");
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        if (typedArray != null) {
            circleColor = typedArray.getColor(R.styleable.MyView_circleColor, defaultCircleColor);
            circleRadius = typedArray.getDimensionPixelSize(R.styleable.MyView_circleRadius, defaultCircleRadius);
            text = typedArray.getString(R.styleable.MyView_circleText);
            textColor = typedArray.getColor(R.styleable.MyView_circleTextColor, Color.parseColor("#333333"));
            textSize = typedArray.getDimensionPixelSize(R.styleable.MyView_circleTextSize, 12);
        }

        initPaint();

        this.setOnClickListener(v -> {
            text = randomText();
            postInvalidate();
        });

    }

    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }
        return sb.toString();
    }


    private void initPaint() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(circleColor);

        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);

        mBound = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), mBound);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        Logger.w(TAG + "mode:%d", mode);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        Logger.w(TAG + "widthMeasureSpec:%d,heightMeasureSpec:%d", viewWidth, viewHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Logger.w(TAG + "left:%d,top:%d,right:%d,bottom:%d", left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewSize = viewWidth < viewHeight ? viewWidth : viewHeight;
        int radius = (viewSize < circleRadius ? viewSize : circleRadius) / 2 - 10;
        canvas.drawCircle(viewWidth / 2, viewHeight / 2, radius, mCirclePaint);
        canvas.drawLine(0, viewHeight / 2, viewWidth, viewHeight / 2, mTextPaint);
        canvas.drawText(text, viewWidth / 2, viewHeight / 2 + mBound.height() / 2, mTextPaint);
    }

}
