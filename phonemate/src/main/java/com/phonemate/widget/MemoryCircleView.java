package com.phonemate.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.phonemate.R;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/12 15:26
 * 修改人：colorful
 * 修改时间：15/11/12 15:26
 * 修改备注：
 */
public class MemoryCircleView extends View{

    private int mCentreX;
    private int mCentreY;

    /**指针颜色*/
    private int mColorInnerRing;
    /**指针*/
    private RectF mBounInnerRing;
    /**指针画笔*/
    private Paint mBgInnerRingPaint;


    private Paint mProgressPaint;

    private float mStrokeWidthRing;

    private float mInnerRadius;

    private int mColorOutRing;

    private Matrix matrixSweepGradient = new Matrix();

    private RectF mBounOutTimeRing;

    private RectF mInnerCircleRectF;

    private Paint mBgOutRingPaint;

    private Paint mInnerCirclePaint;

    private Paint mPaintMinutePointer;

    private float mOutRadius;

    private  Shader mSweepGradient;

    private float[] mSweepGradientColorPos;
    private int[] mSweepGradientColors ;

    private Context mContext;

    private int mDistence=5;

    private float progressRingInitRotateDegree = 270;

    private float rotateSecondPointer = 0;

    private ValueAnimator secondAnim;

    private ValueAnimator steadyAnim;

    private  float mInnerCircleRadius=10;

    private Path pathPointer;

    private float mpTopEdgeLength = 5;

    private float mpPointerLength = 20;

    private float strokeWidthRing = dp2px(25);


    private float mpTopCYOffset = dp2px(3);
    private  float mpBottomEdgeLength = dp2px(4);
    private int mRolate;


    public MemoryCircleView(Context context) {
        super(context);
        initAttrs(null);
    }

    public MemoryCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public MemoryCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MemoryCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCentreX=getWidth()/2;
        mCentreY=getHeight()/2;
        mInnerRadius =getWidth()/4;
        mOutRadius=  mInnerRadius+mDistence+mStrokeWidthRing;

        mBounInnerRing = new RectF(mCentreX - mInnerRadius, mCentreY - mInnerRadius, mCentreX + mInnerRadius, mCentreY + mInnerRadius);
        mBounOutTimeRing = new RectF(mCentreX - mOutRadius, mCentreY -mOutRadius,
                mCentreX + mOutRadius, mCentreY +mOutRadius);
        mInnerCircleRectF=new RectF(mCentreX - mInnerCircleRadius, mCentreY -mInnerCircleRadius,
                mCentreX + mInnerCircleRadius, mCentreY +mInnerCircleRadius);




        mpPointerLength = mInnerRadius * 7 / 8;
        float topX1 = mCentreX - mpTopEdgeLength / 2;
        float topY1 = mCentreY - mpPointerLength + strokeWidthRing / 2;

        float topX2 = mCentreX + mpTopEdgeLength / 2;
        float topY2 = topY1;

        float topCX1 = mCentreX;
        float topCY1 = topY1 - mpTopCYOffset;

        float bottomX1 = mCentreX - mpBottomEdgeLength / 2;
        float bottomY1 = mCentreY;

        float bottomX2 = mCentreX + mpBottomEdgeLength / 2;
        float bottomY2 = bottomY1;
        pathPointer = new Path();
        pathPointer.moveTo(bottomX1, bottomY1);
        pathPointer.lineTo(bottomX2, bottomY2);
        pathPointer.lineTo(topX2, topY2);
        pathPointer.quadTo(topCX1, topCY1, topX1, topY1);
        pathPointer.close();
    }



    public void initAttrs(AttributeSet attrs){
        this.mContext=getContext();
        if(attrs!=null) {
            TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.MemoryCircleView, 0, 0);
            mColorInnerRing = a.getColor(R.styleable.MemoryCircleView_ringcolor, Color.parseColor("#FFFFFF"));
            mStrokeWidthRing=a.getDimension(R.styleable.MemoryCircleView_srokeringwidth, 30f);
            mColorOutRing=a.getColor(R.styleable.MemoryCircleView_outringcolor, Color.parseColor("#FFFFFF"));
            a.recycle();
        }

        mSweepGradientColorPos  =  new float[]{0f, 300f / 360f, 330f / 360f, 1f};
        mSweepGradientColors =  new int[]{Color.TRANSPARENT, 0x80ffffff, 0xddffffff, Color.WHITE};

        mBgInnerRingPaint = new Paint();
        mBgInnerRingPaint.setStyle(Paint.Style.STROKE);
        mBgInnerRingPaint.setStrokeWidth(mStrokeWidthRing);
        mBgInnerRingPaint.setAntiAlias(true);
        mBgInnerRingPaint.setColor(mColorInnerRing);



        mBgOutRingPaint = new Paint();
        mBgOutRingPaint.setStyle(Paint.Style.STROKE);
        mBgOutRingPaint.setStrokeWidth(2);
        mBgOutRingPaint.setAntiAlias(true);
        mBgOutRingPaint.setColor(mColorOutRing);

        mProgressPaint = new Paint();
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mStrokeWidthRing);
        mProgressPaint.setAntiAlias(true);



        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setStyle(Paint.Style.FILL);
//        mInnerCirclePaint.setStrokeWidth(2);
        mInnerCirclePaint.setAntiAlias(true);
        mInnerCirclePaint.setColor(Color.WHITE);

        mPaintMinutePointer = new Paint();
        mPaintMinutePointer.setStyle(Paint.Style.FILL);
        mPaintMinutePointer.setAntiAlias(true);
        mPaintMinutePointer.setColor(Color.WHITE);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRing(canvas);
    }

    public  void drawRing(Canvas canvas){
        if (rotateSecondPointer >= 360f) {
            rotateSecondPointer %= 360f;
        }
        if (mRolate >= 360) {
            mRolate %= 360;
        }

        mBgInnerRingPaint.setPathEffect(new DashPathEffect(new float[]{(float) (mInnerRadius * Math.PI / 360), (float) (mInnerRadius * Math.PI / 90)}, 0));
        mProgressPaint.setPathEffect(new DashPathEffect(new float[]{(float) (mInnerRadius * Math.PI / 360), (float) (mInnerRadius * Math.PI / 90)}, 0));


//        canvas.save();
//        canvas.rotate(-45, mCentreX, mCentreY);
        canvas.drawArc(mBounOutTimeRing, 130f, 280f, false, mBgOutRingPaint);
//        canvas.drawArc(mBounOutTimeRing, 100, 70f, false, mBgOutRingPaint);
//        canvas.drawArc(mBounOutTimeRing, 190, 70f, false, mBgOutRingPaint);
//        canvas.drawArc(mBounOutTimeRing, 280, 70f, false, mBgOutRingPaint);
//        canvas.restore();


        canvas.drawArc(mBounInnerRing, 129, 282f, false, mBgInnerRingPaint);
        canvas.drawArc(mInnerCircleRectF, 0, 360f, false, mInnerCirclePaint);

        canvas.save();
        canvas.rotate(mRolate, mCentreX, mCentreY);
        canvas.drawPath(pathPointer, mPaintMinutePointer);
        canvas.restore();

        mSweepGradient = new SweepGradient(mCentreX, mCentreY, mSweepGradientColors, null);
        matrixSweepGradient.setRotate(getProgressRingRotateDegree(), mCentreX, mCentreY);
        mSweepGradient.setLocalMatrix(matrixSweepGradient);
        mProgressPaint.setShader(mSweepGradient);
        canvas.drawArc(mBounInnerRing, 129, 282f, false, mProgressPaint);

    }


    private float getProgressRingRotateDegree() {
        float degree = (rotateSecondPointer + progressRingInitRotateDegree) % 360;
        return degree;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startNewSecondAnim();
    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void startNewSecondAnim() {
        final float startDegree = 0f;
        final float endDegree = 360f;
        secondAnim = ValueAnimator.ofFloat(startDegree, endDegree);
        secondAnim.setDuration(60 * 1000);
        secondAnim.setInterpolator(new LinearInterpolator());
        secondAnim.setRepeatMode(ValueAnimator.RESTART);
        secondAnim.setRepeatCount(ValueAnimator.INFINITE);
        secondAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private float lastDrawValue = 0;
            private float drawInterval = 0f;

            private float lastUpdatePointerValue = 0;
            private float updatePointerInterval = 360 / 60 * 5;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float newValue = (float) animation.getAnimatedValue();

                // Check if it is the time to update pointer position
                float increasedPointerValue = newValue - lastUpdatePointerValue;
                if (increasedPointerValue < 0) {
                    increasedPointerValue = endDegree + increasedPointerValue;
                }
                if (increasedPointerValue >= updatePointerInterval) {
                    lastUpdatePointerValue = newValue;
//                    updateTimePointer();
                }

                // Check if it is the time to invalidate
                float increasedDrawValue = newValue - lastDrawValue;
                if (increasedDrawValue < 0) {
                    increasedDrawValue = endDegree + increasedDrawValue;
                }
                if (increasedDrawValue >= drawInterval) {
                    lastDrawValue = newValue;
                    rotateSecondPointer += increasedDrawValue;
                    mRolate+=1;
                    invalidate();
//					if (DEBUG) {
//						Log.d(TAG, String.format("newValue:%s , currentPlayTime:%s", newValue, animation.getCurrentPlayTime()));
//					}
                }
            }
        });
        secondAnim.start();
    }

    public float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    public float sp2px(float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources().getDisplayMetrics());
    }
}
