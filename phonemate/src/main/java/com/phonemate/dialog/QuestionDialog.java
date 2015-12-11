package com.phonemate.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.webkit.WebView;

import com.phonemate.R;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.dialog.DialogHolder;
import com.rxx.fast.dialog.DialogUtils;
import com.rxx.fast.view.ViewInject;

import java.util.Locale;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/3 17:54
 * 修改人：colorful
 * 修改时间：15/12/3 17:54
 * 修改备注：
 */
public class QuestionDialog extends DialogHolder {

    @ViewInject(id = R.id.webview)
    private WebView webview;

    private int width;
    private int height;

    public QuestionDialog(Context mActivity) {
        super(mActivity, R.layout.dialog_webview);
        webview.getSettings().setJavaScriptEnabled(true);
        String language = Locale.getDefault().getLanguage();
        webview.getSettings().setDefaultTextEncodingName("gbk");
        webview.loadUrl("file:///android_asset/question.html");
    }

    @Override
    public Dialog createDialog() {
        width = WindowUtils.getWIndowWidth(mActivity) / 5 * 4;
        height = WindowUtils.getWindowHeigh(mActivity) / 10*8;
        Dialog dialog = DialogUtils.createDialogCenter(mActivity, mRootView, R.style.Select_PannelEntity_Dialog, Gravity.CENTER,
                width, height, true);
        return dialog;
    }
}
