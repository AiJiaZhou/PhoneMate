package com.phonemate.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.phonemate.R;

/**
 * User: RanCQ
 * Date: 2015/8/17
 * QQ  :392663986
 * TEL : 15310880724
 */
public class CircleProgressView extends View {
    /**
     * 背景颜色
     */
    private int progressBgColor;
    /**
     * 进度条颜色
     */
    private int progressColor;
    /**
     * 当前进度
     */
    private int progress;
    /**
     * 总进度
     */
    private int progressTotal;

    public onAttachedToWindowListener getOnWindowFocusChangedLintener() {
        return onWindowFocusChangedLintener;
    }

    public void setOnWindowFocusChangedLintener(onAttachedToWindowListener onWindowFocusChangedLintener) {
        this.onWindowFocusChangedLintener = onWindowFocusChangedLintener;
    }

    onAttachedToWindowListener onWindowFocusChangedLintener;

    private Context mContext;
    private Paint mPaint;
    private int circleStroke;
    private int circleRadius;

    public void setProgressBgColor(int progressBgColor) {
        this.progressBgColor = progressBgColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    Circle circleBg;
    Circle circleProgress;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        circleBg = new Circle();
        circleProgress = new Circle();
        TypedArray mTypedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        progressBgColor = mTypedArray.getColor(R.styleable.CircleProgressView_progressBgColor,
                0x88FFFFFF);
        progressColor = mTypedArray.getColor(R.styleable.CircleProgressView_progressColor,
                0xFFFFFFFF);
        progress = mTypedArray.getInt(R.styleable.CircleProgressView_progress, 50);
        progressTotal = mTypedArray.getInt(R.styleable.CircleProgressView_progressTotal, 100);

    }



    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(onWindowFocusChangedLintener!=null){
            onWindowFocusChangedLintener.onWindowFocusChanged();
        }
    }

    public synchronized void setProgress(int progress) {
        if(progress<0){
            progress=0;
        } else if(progress>progressTotal){
            progress=progressTotal;
        }
        this.progress = progress;
        postInvalidate();
    }

    public synchronized void setProgressTotal(int progressTotal) {
        if(progressTotal < 0){
            progressTotal=0;
        }
        this.progressTotal = progressTotal;
        postInvalidate();
    }

    public synchronized int getProgressTotal() {
        return progressTotal;
    }

    public synchronized int getProgress() {
        return progress;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        circleProgress.stroke = circleBg.stroke = getWidth() / 30;
        circleProgress.radius = circleBg.radius = getWidth() / 2 - circleProgress.stroke;
        circleProgress.windX = circleBg.windX = getWidth() / 2;
        circleProgress.windY = circleBg.windY = getHeight() / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(progressBgColor);
        mPaint.setStrokeWidth(circleBg.stroke);
        canvas.drawCircle(circleBg.windX, circleBg.windY, circleBg.radius, mPaint);


        mPaint.setStrokeWidth(circleProgress.stroke); //设置圆环的宽度
        mPaint.setColor(progressColor);  //设置进度的颜色
        RectF oval = new RectF(circleProgress.windX - circleProgress.radius, circleProgress.windX - circleProgress.radius,
                circleProgress.windX + circleProgress.radius,
                circleProgress.windX + circleProgress.radius);  //用于定义的圆弧的形状和大小的界限
        canvas.drawArc(oval, -90, 360 *(progressTotal-progress)  / progressTotal, false, mPaint);  //根据进度画圆弧
    }

    public class Circle {
        int windX;
        int windY;
        int radius;
        int stroke;
    }

    /**监听WindowFocusChanged*/
    public interface onAttachedToWindowListener {
        public void onWindowFocusChanged();
    }

}
