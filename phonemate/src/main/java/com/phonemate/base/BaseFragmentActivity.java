package com.phonemate.base;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.phonemate.PMApplication;
import com.phonemate.R;
import com.phonemate.dialog.ProgressBarDialog;
import com.phonemate.utils.ResUtils;
import com.rxx.fast.FastFragmentActivity;
import com.rxx.fast.utils.LUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * User: RanCQ
 * Date: 2015/10/10
 */
public abstract class BaseFragmentActivity extends FastFragmentActivity {
    protected PMApplication mApplication;
    protected FragmentActivity mActivity;
    public InputMethodManager imm;
    protected View titleView;
    protected int mTitleHeight = 0;
    protected int mTitleColor = R.color.color_green;
    protected ProgressBarDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mApplication = (PMApplication) getApplication();
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        setTranslucentStatus(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT);
        mProgressDialog = new ProgressBarDialog(mActivity);
        mApplication.screenManager.pushActivity(mActivity);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LUtils.i("event", event.toString());
        if(keyCode==KeyEvent.KEYCODE_HOME){
            LUtils.i("Home");
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        //统一关闭软键盘
        if (mActivity.getWindow().getDecorView().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            isLoadTitleView();
        }
    }


    /**
     * 是否加载android 沉浸式状
     */
    public void isLoadTitleView() {
        titleView = findViewById(R.id.titleView);
        titleView.setBackgroundColor(ResUtils.getColor(mActivity, mTitleColor));
        if (mTitleHeight == 0) {
            //应用绘制区域
            Rect appRect = new Rect();
            mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(appRect);

            //View绘制区域
            Rect viewtRect = new Rect();
            mActivity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(viewtRect);

            mTitleHeight = viewtRect.height() - appRect.height();

            ViewGroup.LayoutParams layoutParams = titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;

            layoutParams.height = mTitleHeight;
        }
    }

    /**
     * 设置沉侵栏
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
