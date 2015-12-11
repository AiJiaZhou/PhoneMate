package com.phonemate.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.dialog.DialogHolder;
import com.rxx.fast.dialog.DialogUtils;
import com.rxx.fast.view.ViewInject;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/2 19:59
 * 修改人：colorful
 * 修改时间：15/12/2 19:59
 * 修改备注：
 */
public class AppDetailDialog extends DialogHolder{

    @ViewInject(id = R.id.mIvApp)
    public ImageView mIvApp;

    @ViewInject(id = R.id.mTvVersion)
    public TextView mTvVersion;

    @ViewInject(id = R.id.mTvName)
    public TextView mTvName;

    @ViewInject(id = R.id.mTvPackage)
    public TextView mTvPackage;

    @ViewInject(id = R.id.mTvClass)
    public TextView mTvClass;

    @ViewInject(id = R.id.mTvInstallTime)
    public TextView mTvInstallTime;

    @ViewInject(id = R.id.mTvUpdateTime)
    public TextView mTvUpdateTime;
    public AppDetailDialog(Context mActivity) {
        super(mActivity, R.layout.dialog_appmanager_app_detail);
    }

    @Override
    public Dialog createDialog() {
        return   DialogUtils.createDialogCenter(mActivity, mRootView, R.style.Select_Progress_Dialog, Gravity.CENTER,
                (int) (WindowUtils.getWIndowWidth(mActivity)*0.9), -2, true);
    }
}
