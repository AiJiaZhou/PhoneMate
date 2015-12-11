package com.rxx.fast;


import android.view.View;
import android.view.ViewGroup;

import com.rxx.fast.view.ViewUtils;

/**
 * User: RanCQ
 * Date: 2015/10/9
 */
public abstract class FastFragmentActivity extends android.support.v4.app.FragmentActivity {
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ViewUtils.initViews(this);
        bingViewFinish();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ViewUtils.initViews(this);
        bingViewFinish();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ViewUtils.initViews(this);
        bingViewFinish();
    }

    public abstract void bingViewFinish();

}
