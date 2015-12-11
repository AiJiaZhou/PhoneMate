package com.phonemate.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.phonemate.R;
import com.phonemate.Receiver.LockScreenReceiver;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/19 18:17
 * 修改人：colorful
 * 修改时间：15/11/19 18:17
 * 修改备注：
 */
public class LockScreenTransActivity extends Activity{

    DevicePolicyManager policyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this,
                LockScreenReceiver.class);
        if (policyManager.isAdminActive(componentName)) {// 判断是否有权限(激活了设备管理器)
            policyManager.lockNow();// 直接锁屏
        } else {
            activeManager(componentName);// 激活设备管理器获取权限
        }
        this.finish();
    }
    private void activeManager(ComponentName componentName) {
        // 使用隐式意图调用系统方法来激活指定的设备管理器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, this.getString(R.string.lock_screen_notice));
        startActivity(intent);
    }
}
