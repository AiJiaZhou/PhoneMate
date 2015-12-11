package com.phonemate.model.panelmodel;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.EventHandler;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ResUtils;

import java.util.List;

/**
 * User: RanCQ
 * Date: 2015/8/20
 * QQ  :392663986
 * TEL : 15310880724
 */
public class AppReturnEntity extends PanelMenuEntity {

    //插入小工具列表数据库需要
    public static final String NAME="返回";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.AppReturnEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_return;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };


    public AppReturnEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            if (!isAccessibleEnabled(context)) {
                MessageUtils.alertMessageCENTER("请开启"+context.getString(R.string.app_name)+"辅助功能权限");
                Intent settingsIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(settingsIntent);
            }else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                } else {
                    new EventHandler(context).sendKeys(KeyEvent.KEYCODE_BACK);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    public static boolean isAccessibleEnabled(Context mContext) {
        AccessibilityManager manager = (AccessibilityManager) mContext.getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = manager.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo info : runningServices) {
            //
            if (info.getId().equals(mContext.getPackageName() + "/.service.PMAccessibilityService")) {
                Intent mIntent = new Intent(mContext.getPackageName() + ".ACCESSIBILITY_RECEIVED_ACTION");
                mIntent.putExtra("action", "back");
                mContext.sendBroadcast(mIntent);
                return true;
            }
        }
        manager.getInstalledAccessibilityServiceList();
        return false;
    }

    @Override
    public void init() {
        isNeedCloseCoreDialog=true;
        menuIcon= ResUtils.getDrawable(context,ICON_ON);
        menuName=NAME;
    }
}
