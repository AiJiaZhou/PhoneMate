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
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.phonemate.R;
import com.phonemate.utils.MemoryUtils;

/**
 * 项目名称：PhoneMate
 * 类描述：主页内存清理 widget
 * 创建人：colorful
 * 创建时间：15/11/15 11:54
 * 修改人：colorful
 * 修改时间：15/11/15 11:54
 * 修改备注：
 */
public class MemoryClearView extends View {

    private float mCentreX;
    private float mCentreY;

    //外圆属性
    private int mOutCircleColor;
    private float mOutCircleWidth;
    private float mOutCircleStartAngle;
    private float mOutCircleEndAngle;
    private float mOutCirlceRadius;
    private Paint mOutCirclePaint;
    private RectF mOutCircleRectF;

    //底层环形属性
    private int mInnerCircleColor;
    private float mInnerCircleWidth;
    private float mInnerCircleStartAngle;
    private float mInnerCircleEndAngle;
    private float mInnerCirlceRadius;
    private Paint mInnerCirclePaint;
    private RectF mInnerCircleRectF;

    //外层进度属性
    private int mInnerProgressCircleColor;
    private float mInnerProgressCircleWidth;
    private float mInnerProgressCircleStartAngle;
    private float mInnerProgressCircleEndAngle;
    private float mInnerProgressCirlceRadius;
    private Paint mInnerProgressCirclePaint;
    private RectF mInnerProgressCircleRectF;


    //指针属性
    private int mIndexPointerColor;
    private Path mIndexPointer;
    private Paint mIndexPounterPaint;
    private float mpTopEdgeLength = dp2px(2);
    private float mpPointerLength = dp2px(100);
    private float strokeWidthRing = dp2px(20);
    private float mpTopCYOffset = dp2px(4);
    private float mpBottomEdgeLength = dp2px(4);

    //内部小远
    private Paint mIndexCirclePaint;
    private int mIndexCircleColor;

    //外圆和环形间距
    private float mInner2OutDiatance;

    private int mDefaultColor = 0x80ffffff;

    private float[] mInnerProgressColorsPos = new float[]{0f, 300f / 360f, 1f};
    private int[] mInnerProgressColors = {Color.TRANSPARENT, 0x33FFFFFF, Color.WHITE};
    private Shader mInnerProgressShader;

    private ValueAnimator mValueAnimation;
    private float mProgressvalue = 0;

    private Matrix mSweepGradient;

    private long mDelayMillis = 200;

    public MemoryClearView(Context context) {
        this(context, null);
    }

    public MemoryClearView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MemoryClearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray typedArray = context.getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.MemoryClearView, defStyleAttr, 0);

            //加载外圆属性
            mOutCircleColor = typedArray.getColor(R.styleable.MemoryClearView_outcircle_color, mDefaultColor);
            mOutCircleWidth = typedArray.getDimension(R.styleable.MemoryClearView_outcircle_width, dp2px(1));
            mOutCircleStartAngle = typedArray.getInt(R.styleable.MemoryClearView_outcircle_start_angle, 0);
            mOutCircleEndAngle = typedArray.getInt(R.styleable.MemoryClearView_outcircle_end_angle, 270);
            mOutCirlceRadius = typedArray.getDimension(R.styleable.MemoryClearView_outcircle_radius, -1);

            mInnerCircleColor = typedArray.getColor(R.styleable.MemoryClearView_innercircle_color, mDefaultColor);
            mInnerCircleWidth = typedArray.getDimension(R.styleable.MemoryClearView_innercircle_width, dp2px(20));
            mInnerCircleStartAngle = typedArray.getInt(R.styleable.MemoryClearView_innercircle_start_angle, 0);
            mInnerCircleEndAngle = typedArray.getInt(R.styleable.MemoryClearView_innercircle_end_angle, 270);
            mInnerCirlceRadius = typedArray.getDimension(R.styleable.MemoryClearView_innercircle_radius, -1);

            mInnerProgressCircleColor = typedArray.getColor(R.styleable.MemoryClearView_innerprogress_circle_color, Color.WHITE);
            mInnerProgressCircleWidth = typedArray.getDimension(R.styleable.MemoryClearView_innerprogress_circle_width, dp2px(20));
            mInnerProgressCircleStartAngle = typedArray.getInt(R.styleable.MemoryClearView_innerprogress_circle_start_angle, 0);
            mInnerProgressCircleEndAngle = typedArray.getInt(R.styleable.MemoryClearView_innerprogress_circle_end_angle, 270);
            mInnerProgressCirlceRadius = typedArray.getDimension(R.styleable.MemoryClearView_innerprogress_circle_radius, -1);

            mInner2OutDiatance = typedArray.getDimension(R.styleable.MemoryClearView_inner_2_outdiatance, dp2px(4));

            mIndexPointerColor = typedArray.getColor(R.styleable.MemoryClearView_indexpointer_color, Color.WHITE);
            mIndexCircleColor = typedArray.getColor(R.styleable.MemoryClearView_indexpointer_circle_color, Color.WHITE);
        }

        mOutCirclePaint = new Paint();
        mOutCirclePaint.setAntiAlias(true);
        mOutCirclePaint.setStyle(Paint.Style.STROKE);
        mOutCirclePaint.setStrokeWidth(mOutCircleWidth);
        mOutCirclePaint.setColor(mOutCircleColor);

        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setAntiAlias(true);
        mInnerCirclePaint.setStyle(Paint.Style.STROKE);
        mInnerCirclePaint.setStrokeWidth(mInnerCircleWidth);
        mInnerCirclePaint.setColor(mInnerCircleColor);


        mInnerProgressCirclePaint = new Paint();
        mInnerProgressCirclePaint.setAntiAlias(true);
        mInnerProgressCirclePaint.setStyle(Paint.Style.STROKE);
        mInnerProgressCirclePaint.setStrokeWidth(mInnerProgressCircleWidth);
        mInnerProgressCirclePaint.setColor(mInnerProgressCircleColor);
        mSweepGradient = new Matrix();

        // Minute Pointer
        mIndexPounterPaint = new Paint();
        mIndexPounterPaint.setAntiAlias(true);
        mIndexPounterPaint.setStyle(Paint.Style.FILL);
        mIndexPounterPaint.setColor(mIndexPointerColor);

        mIndexCirclePaint = new Paint();
        mIndexCirclePaint.setAntiAlias(true);
        mIndexCirclePaint.setStyle(Paint.Style.FILL);
        mIndexCirclePaint.setColor(mIndexCircleColor);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCentreX = getWidth() / 2;
        mCentreY = getHeight() / 2;
        float maxXY = mCentreX > mCentreY ? mCentreX : mCentreY;
        //外圆
        if (mOutCirlceRadius == -1) {
            mOutCirlceRadius = maxXY / 3 * 2;
        }

        mOutCircleRectF = new RectF(mCentreX - mOutCirlceRadius, mCentreY - mOutCirlceRadius, mCentreX + mOutCirlceRadius, mCentreY + mOutCirlceRadius);

        //内环形
        mInnerCirlceRadius = mOutCirlceRadius - mInnerCircleWidth - mInner2OutDiatance;
        mInnerCircleRectF = new RectF(mCentreX - mInnerCirlceRadius, mCentreY - mInnerCirlceRadius, mCentreX + mInnerCirlceRadius, mCentreY + mInnerCirlceRadius);
        mInnerCirclePaint.setPathEffect(new DashPathEffect(new float[]{(float) (mInnerCirlceRadius * Math.PI / 360 * 1),
                (float) (mInnerCirlceRadius * Math.PI / 360 * 2.5)}, 0));

        //内环进度
        mInnerProgressShader = new SweepGradient(mCentreX, mCentreY, mInnerProgressColors, mInnerProgressColorsPos);
        mInnerProgressCirclePaint.setShader(mInnerProgressShader);
        mInnerProgressCircleRectF = new RectF(mCentreX - mInnerCirlceRadius, mCentreY - mInnerCirlceRadius, mCentreX + mInnerCirlceRadius, mCentreY + mInnerCirlceRadius);
        mInnerProgressCirclePaint.setPathEffect(new DashPathEffect(new float[]{(float) (mInnerCirlceRadius * Math.PI / 360 * 1),
                (float) (mInnerCirlceRadius * Math.PI / 360 * 2.5)}, 0));


        mpPointerLength = mInnerCirlceRadius;
        float topX1 = mCentreX - mpTopEdgeLength / 2;
        float topY1 = mCentreY - mpPointerLength + strokeWidthRing;

        float topX2 = mCentreX + mpTopEdgeLength / 2;
        float topY2 = topY1;

        float topCX1 = mCentreX;
        float topCY1 = topY1 - mpTopCYOffset;

        float bottomX1 = mCentreX - mpBottomEdgeLength / 2;
        float bottomY1 = mCentreY;
        float bottomX2 = mCentreX + mpBottomEdgeLength / 2;
        float bottomY2 = bottomY1;

        mIndexPointer = new Path();
        mIndexPointer.moveTo(bottomX1, bottomY1);
        mIndexPointer.lineTo(bottomX2, bottomY2);
        mIndexPointer.lineTo(topX2, topY2);
        mIndexPointer.quadTo(topCX1, topCY1, topX1, topY1);
        mIndexPointer.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.rotate(135, mCentreX, mCentreY);
        canvas.drawArc(mOutCircleRectF, mOutCircleStartAngle, mOutCircleEndAngle, false, mOutCirclePaint);
        canvas.drawArc(mInnerCircleRectF, mInnerCircleStartAngle, mInnerCircleEndAngle, false, mInnerCirclePaint);
        mSweepGradient.setRotate(mProgressvalue, mCentreX, mCentreY);
        mInnerProgressShader.setLocalMatrix(mSweepGradient);
        canvas.drawArc(mInnerProgressCircleRectF, mInnerProgressCircleStartAngle, mInnerProgressCircleEndAngle, false, mInnerProgressCirclePaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(mProgressvalue + 225, mCentreX, mCentreY);
        canvas.drawPath(mIndexPointer, mIndexPounterPaint);
        canvas.restore();

        canvas.drawCircle(mCentreX, mCentreY, mpBottomEdgeLength * 1.5f, mIndexPounterPaint);
        canvas.drawCircle(mCentreX, mCentreY, mpBottomEdgeLength * 0.8f, mIndexCirclePaint);

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, mDelayMillis);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimation();
        //还原
        restore();
    }


    public void restore() {
        mProgressvalue = 0;
        invalidate();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public final void startAnimation() {
        mValueAnimation = ValueAnimator.ofFloat(mInnerProgressCircleStartAngle, (mInnerProgressCircleStartAngle + mInnerProgressCircleEndAngle) * 2);
        mValueAnimation.setInterpolator(new LinearInterpolator());
        mValueAnimation.setRepeatCount(0);
        mValueAnimation.setRepeatMode(Animation.RESTART);
        mValueAnimation.setDuration(3 * 1000);
         long availMem = MemoryUtils.getAvailMemory(getContext());
         long totalmen = MemoryUtils.getTotalMemory(getContext());
        final float percent=(availMem / (totalmen+0.5f));
        mValueAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animationValue = (float) animation.getAnimatedValue();
                if (animationValue > (mInnerProgressCircleStartAngle + mInnerProgressCircleEndAngle)) {
                    if (mProgressvalue / (360+0.5f) >percent ) {
                        mProgressvalue = (mInnerProgressCircleStartAngle + mInnerProgressCircleEndAngle) * 2 - animationValue;
                    }

                } else {
                    mProgressvalue = animationValue;
                }
                invalidate();
            }
        });
        mValueAnimation.start();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void cancelAnimation() {
        if (mValueAnimation != null && (mValueAnimation.isStarted() || mValueAnimation.isRunning())) {
            mValueAnimation.cancel();
        }
    }

    public float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    public float sp2px(float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources().getDisplayMetrics());
    }
}
