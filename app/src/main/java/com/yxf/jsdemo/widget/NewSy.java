package com.yxf.jsdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.yxf.jsdemo.R;
import com.yxf.jsdemo.util.DensityUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    private Callback callback;
    private Context mCtx;

    /**
     * handler是线程之间的信使
     * 定义：
     * 1.在主线程中定义一个handler，那么这个Looper和thread都在主线程
     * <p>
     * 使用：
     * 怎么存放消息:
     * handler对象通过sendMessage来发送msg(也就是通过MessageQueue的enqueueMessage入队)
     * 怎么把消息取出来交给handler处理：
     * Looper.loop();方法，这是一个死循环的方法，一直监听入队的消息
     * msg.target.dispatchMessage();发送
     * <p>
     * msg.target其实就是一个handler对象
     */
    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Logger.w("handleMessage position :%d ,thread :%s", msg.what, Thread.currentThread().getName());
            if (callback != null) {
                callback.clickItem(msg.what);
            }
            return false;
        }
    });

    public NewSy(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewSy(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCtx = context;
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

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClick();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Logger.w("onSizeChanged w:%d,h:%d,oldw:%d,oldh:%d", w, h, oldw, oldh);
    }

    public void setClickCallBack(Callback callBack) {
        this.callback = callBack;
    }

    private void viewClick() {
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            Logger.w("subscribe thread :%s,handler Looper hashCode:%d", Thread.currentThread().getName(), handler.getLooper().hashCode());
            handler.sendEmptyMessage(0);
            e.onNext(0);
            e.onComplete();
        })
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(integer -> Logger.w("accept thread :%s", Thread.currentThread().getName()));
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setTextSize(DensityUtil.sp2px(mCtx, 12));
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
        Logger.w("width:%d, height:%d", containerWidth, containerHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Logger.w("onDraw ex");
        canvas.drawText(name, containerWidth / 2, containerHeight / 2 + baseLine, paint);
    }

    public interface Callback {
        void clickItem(int position);
    }
}


