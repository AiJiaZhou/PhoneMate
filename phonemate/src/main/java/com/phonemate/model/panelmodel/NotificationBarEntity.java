package com.phonemate.model.panelmodel;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ResUtils;

import java.lang.reflect.Method;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/20 22:22
 * 修改人：colorful
 * 修改时间：15/11/20 22:22
 * 修改备注：
 */
public class NotificationBarEntity extends PanelMenuEntity{

    public static final String NAME="通知栏";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.NotificationBarEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_expand_notifications;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };

    public NotificationBarEntity(Context context) {
        super(context);
    }
    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        try{
            Object service = context.getSystemService("statusbar");
            Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
            Method expand = null;
            if (service != null) {
                if (currentApiVersion <= 16) {
                    expand = statusbarManager.getMethod("expand");
                } else {
                    expand = statusbarManager.getMethod("expandNotificationsPanel");
                }
                expand.setAccessible(true);
                expand.invoke(service);
            }
        }catch(Exception e){
             MessageUtils.alertMessageCENTER("打开通知栏失败");
        }
    }

    @Override
    public void init() {
        isNeedCloseCoreDialog=true;
        menuIcon= ResUtils.getDrawable(context, ICON_ON);
        menuName=NAME;
    }
}

