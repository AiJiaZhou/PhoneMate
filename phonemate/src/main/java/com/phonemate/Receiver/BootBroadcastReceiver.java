package com.phonemate.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.phonemate.utils.IntentUtils;

/**
 *
 * 开机广播监听
 * User: RanCQ
 * Date: 2015/8/14
 * QQ  :392663986
 * TEL : 15310880724
 */
public class BootBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        IntentUtils.startCoreService(context);
    }
}
