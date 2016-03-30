package com.phonemate.model.panelmodel;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.AccessibilityUtils;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ResUtils;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/29 09:41
 * 修改人：colorful
 * 修改时间：15/12/29 09:41
 * 修改备注：
 */
public class ScreenCapture  extends PanelMenuEntity{

    public static final String ACTION_DATA="screen_capture";
    public static final String NAME="截屏";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.ScreenCapture";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_screen_capture;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };

    public ScreenCapture(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            if (!AccessibilityUtils.isAccessibleEnabled(context)) {
                MessageUtils.alertMessageCENTER("请开启" + context.getString(R.string.app_name) + "辅助功能权限");
                Intent settingsIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(settingsIntent);
            }else {
                    Intent mIntent = new Intent(context.getPackageName() + ".ACCESSIBILITY_RECEIVED_ACTION");
                    mIntent.putExtra("action", ACTION_DATA);
                     context.sendBroadcast(mIntent);
            }
        }else{
            MessageUtils.alertMessageCENTER("您的设备不支持");
        }
    }

    @Override
    public void init() {
        isNeedCloseCoreDialog=true;
        menuIcon= ResUtils.getDrawable(context, ICON_ON);
        menuName=NAME;
    }
}
