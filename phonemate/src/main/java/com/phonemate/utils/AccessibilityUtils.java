package com.phonemate.utils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import com.phonemate.R;

import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/18 11:52
 * 修改人：colorful
 * 修改时间：15/12/18 11:52
 * 修改备注：
 */
public class AccessibilityUtils {
    @SuppressLint("NewApi")
    public static boolean isAccessibleEnabled(Context mContext) {
        AccessibilityManager manager = (AccessibilityManager) mContext.getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = manager.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo info : runningServices) {
            //
            if (info.getId().equals(mContext.getPackageName() + "/.service.PMAccessibilityService")) {
                return true;
            }
        }
        manager.getInstalledAccessibilityServiceList();
        return false;
    }

    public  static void goAccessSetting(Context context){
        MessageUtils.alertMessageCENTER("请开启"+context.getString(R.string.app_name)+"辅助功能权限");
        Intent settingsIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(settingsIntent);
    }
}
