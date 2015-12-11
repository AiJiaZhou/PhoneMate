package com.phonemate.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * User: RanCQ
 * Date: 2015/8/23
 * QQ  :392663986
 * TEL : 15310880724
 */
public class AppLockReceiver extends BroadcastReceiver {
    public static String INTENT_KEY="is_app_lock";

    public AppLockReceiver(AppLockListener mAppLockListener) {
        this.mAppLockListener = mAppLockListener;
    }

    private AppLockListener mAppLockListener;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(mAppLockListener!=null){
                mAppLockListener.appLockChangeListener(intent.getBooleanExtra(INTENT_KEY,false));
            }
        }


    public interface  AppLockListener{
        public void appLockChangeListener(boolean value);
    }
}

