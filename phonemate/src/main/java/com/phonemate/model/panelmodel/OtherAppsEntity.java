package com.phonemate.model.panelmodel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;

/**
 * User: RanCQ
 * Date: 2015/9/22
 */
public class OtherAppsEntity extends PanelMenuEntity{
    public String appName;
    public PackageManager packageManager;

    public OtherAppsEntity(Context context, String packageName, String className,String appName) {
        super(context);
        this.mPackName=packageName;
        this.mClassName=className;
        this.appName=appName;
        packageManager=context.getPackageManager();
        init();
    }



    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();
        ComponentName componentName=new ComponentName(mPackName,mClassName);
        intent.setComponent(componentName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    public void initData() {

    }

    @Override
    public void init() {
        isNeedCloseCoreDialog=true;
        try {
            menuIcon= packageManager.getApplicationIcon(mPackName);
            menuName=appName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
