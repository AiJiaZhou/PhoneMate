package com.phonemate.Receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

import com.phonemate.utils.MessageUtils;

/**
 * User: RanCQ
 * Date: 2015/8/23
 * QQ  :392663986
 * TEL : 15310880724
 */
public class LockScreenReceiver extends DeviceAdminReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        MessageUtils.alertMessageCENTER("激活成功");
        super.onEnabled(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        MessageUtils.alertMessageCENTER("取消激活");
        super.onDisabled(context, intent);
    }

}

