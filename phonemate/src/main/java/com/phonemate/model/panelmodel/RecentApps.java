package com.phonemate.model.panelmodel;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.ResUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/20 15:39
 * 修改人：colorful
 * 修改时间：15/11/20 15:39
 * 修改备注：
 */
public class RecentApps extends PanelMenuEntity {

    //插入小工具列表数据库需要
    public static final String NAME = "最近任务";
    public static final String CLASS_NAME = "com.phonemate.model.panelmodel.RecentApps";
    public static final int ICON_ON = R.mipmap.icon_panelmenu_recent_apps;
    public static final Object[] INSERT_VALUES = {ICON_ON, CLASS_NAME, NAME};
    public RecentApps(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        Class serviceManagerClass;
        try {
            serviceManagerClass = Class.forName("android.os.ServiceManager");
            Method getService = serviceManagerClass.getMethod("getService", String.class);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerClass, "statusbar");
            Class statusBarClass = Class.forName(retbinder.getInterfaceDescriptor());
            Object statusBarObject = statusBarClass.getClasses()[0].getMethod("asInterface", IBinder.class).invoke(null, new Object[]{retbinder});
            Method clearAll = statusBarClass.getMethod("toggleRecentApps");
            clearAll.setAccessible(true);
            clearAll.invoke(statusBarObject);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init() {
        isNeedCloseCoreDialog = true;
        menuIcon = ResUtils.getDrawable(context, ICON_ON);
        menuName = NAME;
    }
}