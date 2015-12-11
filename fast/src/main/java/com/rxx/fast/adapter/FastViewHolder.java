package com.rxx.fast.adapter;

import android.view.View;

import com.rxx.fast.view.ViewUtils;

/**
 * 
 * @author Ran
 *
 * 时间: 2015年8月20日
 */
public abstract class FastViewHolder {
    public View mRootView;
    public FastViewHolder(View view) {
        mRootView =view;
        ViewUtils.initViews(this,mRootView);
    }

}
