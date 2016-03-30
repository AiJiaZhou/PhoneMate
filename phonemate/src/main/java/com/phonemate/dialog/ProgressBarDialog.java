package com.phonemate.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.phonemate.R;
import com.rxx.fast.dialog.DialogHolder;
import com.rxx.fast.dialog.DialogUtils;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/30 22:14
 * 修改人：colorful
 * 修改时间：15/11/30 22:14
 * 修改备注：
 */
public class ProgressBarDialog extends DialogHolder {

    public ProgressBarDialog(Context mActivity) {
        super(mActivity, R.layout.dialog_progress_view);
    }

    @Override
    public Dialog createDialog() {
        return DialogUtils.createDialogCenter(mActivity, mRootView, R.style.Select_Progress_Dialog, Gravity.CENTER, -1, -1, true);
    }

    public void show(String text) {
        mDialog.show();
    }

    public boolean dialogIsShow() {
        return mDialog.isShowing();
    }

    public void dissmissProgressDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
