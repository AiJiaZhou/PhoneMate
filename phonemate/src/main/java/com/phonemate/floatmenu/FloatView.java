package com.phonemate.floatmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.phonemate.PMApplication;
import com.phonemate.R;
import com.phonemate.interpolator.FLoatViewOutInterpolator;
import com.phonemate.model.FloatViewSettingSelectColorEntity;
import com.phonemate.model.FloatviewClickDbEntity;
import com.phonemate.model.WeatherModel;
import com.phonemate.model.panelmodel.PanelMenuEntity;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.SettingUtils;
import com.phonemate.utils.WindowUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：悬浮菜单
 * 创建人：colorful
 * 创建时间：15/11/16 17:41
 * 修改人：colorful
 * 修改时间：15/11/16 17:41
 * 修改备注：
 */
public class FloatView implements View.OnTouchListener {
    /**
     * 已经显示
     */
    public final static int STATUS_SHOWING = 1;
    /**
     * 未显示
     */
    public final static int STATUS_DISSMISS = -1;

    /**
     * 当前状态
     */
    private int mStatus;

    /**
     * 默认尺寸
     */
    private int mSize = 40;
    /**
     * 默认次存缩放比例
     */
    private float mSizeZoom = 1.0f;


    /**
     * 悬浮窗 view
     */
    private View mRootView;
    /**
     * 悬浮窗根布局
     */
    private FrameLayout mFrameLayout;
    /**
     * 悬浮窗 静态 ImageView
     */
    private ImageView mImageView;

    /**
     * 非激活颜色
     */
    private int mDefaultColor = 0x66000000;
    private float mDefaultColorZoom = 1.0f;

    /**
     * 激活颜色
     */
    private int mActivationColor = 0xFF000000;
    private float mActivationColorZoom = 1.0f;
    /**
     * 默认圆角
     */
    private int mRadius = 8; // 8dp 圆角半径
    private float mRadiusZoom = 1.0f;
    /**
     * 背景
     */
    private GradientDrawable mBgDrawable;

    /**
     * 移动最小距离
     */
    private int mTouchSlop;
    private float mTouchX = 0, mTouchY = 0;
    private float totalMoveX = 0, totalMoveY = 0;
    private int mWindowX;
    private boolean mCanClick;

    private FLoatViewClickListener mClickListener;

    private FLoatViewLongClickListener mLongClickListener;

    private FLoatViewClickListener mTwiceClickListener;


    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;
    private Context mContext;
    private PMApplication mApplication;
    private GestureDetector mDetertor;

    public FloatView(Context mContext) {
        this.mContext = mContext;
        mApplication = (PMApplication) mContext.getApplicationContext();
        createView();

    }

    public void createView() {
        //获取屏幕尺寸
        mWindowX = WindowUtils.getWIndowWidth(mContext);
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        //创建背景
        mBgDrawable = new GradientDrawable();//创建drawable
        mBgDrawable.setColor(mDefaultColor);
        mBgDrawable.setCornerRadius(ResUtils.dp2px(mRadius * mRadiusZoom, mContext));
        mBgDrawable.setStroke(0, mDefaultColor);

        mRootView = View.inflate(mContext, R.layout.include_floatview, null);
        mFrameLayout = (FrameLayout) mRootView.findViewById(R.id.mLayoutFloatView);
        mImageView = (ImageView) mRootView.findViewById(R.id.mIvFloatview);
        mRootView.setOnTouchListener(this);
        mDetertor = new GestureDetector(mContext, new DefaultGestureListener());

        mWindowManager = (WindowManager) mApplication.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 不需要权限也可以弹窗，解决小米、华为等坑爹的禁止弹窗设置。
//            mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//        } else {
//            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        }

        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;

        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mLayoutParams.format = PixelFormat.RGBA_8888;

        mLayoutParams.height = (int) ResUtils.dp2px(mSize, mContext);
        mLayoutParams.width = (int) ResUtils.dp2px(mSize, mContext);
        ;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.CENTER;
        // 改变状态
        mStatus = STATUS_DISSMISS;

        updateView();


    }

    /**
     * 显示悬浮窗
     */
    public void showView() {
        if (mStatus != STATUS_SHOWING) {
            mStatus = STATUS_SHOWING;
            mWindowManager.addView(mRootView, mLayoutParams);
            updateView();
            updateWeather();
        }
    }

    /**
     * 隐藏悬浮窗
     */
    public void dismissView() {
        if (mStatus == STATUS_SHOWING) {
            mStatus = STATUS_DISSMISS;
            mWindowManager.removeView(mRootView);
        }
    }

    /**
     * 获取View状态
     */
    public int getStatus() {
        return mStatus;
    }


    /**
     * 更新悬浮窗,背景圆角,尺寸,透明度
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void updateView() {
        mRadiusZoom = SettingUtils.getFLoatViewRadius(mContext);
        mSizeZoom = SettingUtils.getFLoatViewSize(mContext);
        mDefaultColorZoom = SettingUtils.getFLoatViewTrans(mContext);

        //背景
        mBgDrawable.setColor(FloatViewSettingSelectColorEntity.mColors[SettingUtils.getFLoatViewColor(mContext)]);
        //圆角
        mBgDrawable.setCornerRadius(ResUtils.dp2px(mRadius * mRadiusZoom, mContext));
        //描边
        mBgDrawable.setStroke(0, mDefaultColor);
        mFrameLayout.setBackground(mBgDrawable);
        //尺寸
        mLayoutParams.width = (int) ResUtils.dp2px(mSize * mSizeZoom, mContext);
        mLayoutParams.height = mLayoutParams.width;
        if (mStatus == STATUS_SHOWING) {
            mWindowManager.updateViewLayout(mRootView, mLayoutParams);
        }
    }

    /**
     * 更新天气
     */
    public void updateWeather() {
        List<WeatherModel> list = mApplication.fastDB.queryAll(WeatherModel.class);
        if (list != null && list.size() > 0) {
            mImageView.setImageDrawable(ResUtils.getDrawable(mApplication, list.get(0).getWeatherIcon()));
        }
    }


    public void setLongClickListener(FLoatViewLongClickListener mLongClickListener) {
        this.mLongClickListener = mLongClickListener;
    }

    public void setTwiceClickListener(FLoatViewClickListener mTwiceClickListener) {
        this.mTwiceClickListener = mTwiceClickListener;
    }

    public void setOnClickListener(FLoatViewClickListener onClickListener) {
        this.mClickListener = onClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mDetertor.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (event.getRawX() > (mWindowX / 2)) {
                    updatePosition(-1, -1, -1);
                } else {
                    updatePosition(-1, -1, 1);
                }
                break;
        }
        return true;
    }

    /**
     * @param f    x轴位移
     * @param g    Y轴位移
     * @param type 类型： 0：当前位移 1：靠左 -1：靠右
     */
    public void updatePosition(float f, float g, int type) {
        if (type == 0) {
            mLayoutParams.x = (int) (mLayoutParams.x + f);
            mLayoutParams.y = (int) (mLayoutParams.y + g);
        } else if (type == 1) {
            mLayoutParams.x = 0;
        } else if (type == -1) {
            mLayoutParams.x = mWindowX - mSize;
        }
        mWindowManager.updateViewLayout(mRootView, mLayoutParams);
    }

    /**
     * 点击接口
     */
    public interface FLoatViewClickListener {
        public void OnClick(View v);
    }

    /**
     * 长按接口
     */
    public interface FLoatViewLongClickListener {
        public void OnLongClick(View v);
    }

    /**
     * 双击接口
     */
    public interface FLoatViewTwiceClickListener {
        public void OnTwiceClick(View v);
    }


    /**
     * 悬浮窗监听
     */
    private class DefaultGestureListener extends GestureDetector.SimpleOnGestureListener {
        float moveX;
        float moveY;

        public DefaultGestureListener() {
            super();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return super.onSingleTapUp(event);
        }

        @Override
        public void onLongPress(MotionEvent event) {
            super.onLongPress(event);
            doClick(FloatviewClickDbEntity.LONG_CLICK + "");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            moveX = e2.getRawX() - mTouchX;
            moveY = e2.getRawY() - mTouchY;
            totalMoveX += moveX;
            totalMoveY += moveY;
            if (Math.sqrt(totalMoveY * totalMoveY + totalMoveX * totalMoveX) < mTouchSlop) {
                mCanClick = false;
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
            updatePosition(moveX, moveY, 0);
            mTouchX = (int) e2.getRawX();
            mTouchY = (int) e2.getRawY();
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent event) {
            mTouchX = event.getRawX();
            mTouchY = event.getRawY();
            totalMoveX = totalMoveY = 0;
            return super.onDown(event);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            doClick(FloatviewClickDbEntity.DOUBLE_CLICK + "");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            if (mClickListener != null) {
                mClickListener.OnClick(mRootView);
            }
            return super.onSingleTapConfirmed(event);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return super.onContextClick(e);
        }
    }

    public void doClick(String type) {
        List<FloatviewClickDbEntity> clickDbEntities = mApplication.fastDB.queryByWhere(FloatviewClickDbEntity.class, "type=?",
                new String[]{type});
        if (clickDbEntities != null && clickDbEntities.size() > 0) {
            try {
                Class clazz = Class.forName(clickDbEntities.get(0).getClassName());
                Constructor constructor = clazz.getConstructor(Context.class);
                ((PanelMenuEntity) constructor.newInstance(mApplication)).onClick(null, null, -1, -1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //移动动画
    @SuppressLint("NewApi")
    private void startCancelAnimation(final int fromX, final int toX) {

        ValueAnimator mSnapAnimator = ValueAnimator.ofFloat(0, 1);
        mSnapAnimator.setDuration(800);
        FLoatViewOutInterpolator beo = new FLoatViewOutInterpolator();
        beo.getInterpolation(0.1f);
        mSnapAnimator.setInterpolator(beo);
        mSnapAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        mSnapAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLayoutParams.x = fromX + (int) (animation.getAnimatedFraction() * (toX - fromX));
                mWindowManager.updateViewLayout(mRootView, mLayoutParams);
            }
        });
        mSnapAnimator.start();
    }

}
