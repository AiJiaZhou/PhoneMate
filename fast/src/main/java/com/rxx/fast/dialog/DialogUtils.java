package com.rxx.fast.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 创建 dialog
 *
 * @author Ran
 *         <p/>
 *         时间: 2015年7月30日
 */
public class DialogUtils {
    public static Dialog createDialogCenter(Context activity, View view, int style,
                                            int gravity, int width, int height, boolean cancelable) {
        Dialog dialog = new Dialog(activity, style);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams dialogLayoutParams = dialogWindow.getAttributes();
        dialogLayoutParams.width = width;
        dialogLayoutParams.height = height;
        dialogWindow.setGravity(gravity);
        dialog.setCancelable(cancelable);
        return dialog;
    }

}
