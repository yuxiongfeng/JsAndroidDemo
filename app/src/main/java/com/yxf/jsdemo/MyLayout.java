package com.yxf.jsdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/19 14:47
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/19 14:47
 */
public class MyLayout extends ViewGroup {
    public static final String TAG = "MyLayout---";
    private int containerWidth;
    private int containerHeight;
    private Paint mPaint;

    public MyLayout(Context context) {
        super(context);
        Logger.w(TAG + "MyLayout(Context context)");
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.w(TAG + "MyLayout(Context context, AttributeSet attrs)");
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#2CE06F"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//测量容器
        containerWidth = MeasureSpec.getSize(widthMeasureSpec);
        containerHeight = MeasureSpec.getSize(heightMeasureSpec);
        Logger.w("onMeasure width:%d,height:%d", containerWidth, containerHeight);
        //测量子view
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Logger.w(TAG + "onLayout(boolean changed=%b, int l=%d, int t=%d, int r=%d, int b=%d)", changed, l, t, r, b);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int measuredWidth = childView.getMeasuredWidth();
            int measuredHeight = childView.getMeasuredHeight();
            int width = childView.getWidth();
            int height = childView.getHeight();
            Logger.w(TAG + "measureWidth:%d,MeasureHeight:%d,width:%d,height:%d", measuredWidth, measuredHeight, width, height);
            childView.layout(i * measuredWidth, (containerHeight - measuredHeight) / 2, (i + 1) * measuredWidth, (containerHeight + measuredHeight) / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, containerHeight / 2, containerWidth, containerHeight / 2, mPaint);
        int childCount = getChildCount();
       /* for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int measuredWidth = childView.getMeasuredWidth();
            int measuredHeight = childView.getMeasuredHeight();
            int width = childView.getWidth();
            int height = childView.getHeight();
            //从这里的打印我们可以知道measuredWidth,measuredHeight 在onMeasure之后就有值了， width 和 height 在onLayout之后才有值
            Logger.w(TAG + "onDraw measureWidth:%d,MeasureHeight:%d,width:%d,height:%d", measuredWidth, measuredHeight, width, height);
        }*/
    }
}
