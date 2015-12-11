package com.rxx.fast.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.rxx.fast.view.ViewUtils;

/**
 *
 * dialog Holder 对象,持有 dialog 对象和 dialog view 的所有对象
 *
 * @author Ran
 *
 * 时间: 2015年7月30日
 */
public abstract class DialogHolder {

    public Dialog mDialog;
    public View mRootView;
    public Context mActivity;

    public DialogHolder(Context mActivity,int layout) {
        this.mActivity=mActivity;
        mRootView=View.inflate(mActivity, layout,null);
        ViewUtils.initViews(this, mRootView);
        mDialog=createDialog();
    }
    public abstract Dialog createDialog();

}
