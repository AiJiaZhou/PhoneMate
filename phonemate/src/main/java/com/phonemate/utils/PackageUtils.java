package com.phonemate.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * User: RanCQ
 * Date: 2015/9/10
 */
public class PackageUtils {
    /**查询本地所有APP*/
    public static  List<ResolveInfo> getAppInformation(Context context){
        List<ResolveInfo> list=null;
        PackageManager packageManager=context.getPackageManager();
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        list=packageManager.queryIntentActivities(intent,PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        return  list;
    }
}
