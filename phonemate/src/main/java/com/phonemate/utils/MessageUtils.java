package com.phonemate.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast信息提示
 *
 * @author rancq
 * @time 2015年3月27日
 */
public final class MessageUtils {
    private static Toast mToast;

    private MessageUtils() {
    }

    public static void init(Context context) {
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }


    public static void alertMessageBottom(String msg) {
        mToast.setGravity(Gravity.BOTTOM, 0, 0);
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void alertMessageTop( String msg) {
        mToast.setGravity(Gravity.TOP, 0, 0);
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void alertMessage(String msg,int gravity) {
        mToast.setGravity(gravity, 0, 0);
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void alertMessageCENTER( String msg) {
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

}
