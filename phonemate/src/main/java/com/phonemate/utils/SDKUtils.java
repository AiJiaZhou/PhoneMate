package com.phonemate.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 项目名称：newzeroshop
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/10/13 19:02
 * 修改人：colorful
 * 修改时间：15/10/13 19:02
 * 修改备注：
 */
public class SDKUtils {
    /**获取当前版本号*/
    public static int getVersonCode(Context mContext){
        int versonCode=0;
        try {
            PackageManager mPackageManager=mContext.getPackageManager();
            PackageInfo mPackageInfo=mPackageManager.getPackageInfo(mContext.getPackageName(), 0);
            versonCode=mPackageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versonCode;
    }

    /**获取当前版本名*/
    public static String getVersonName(Context mContext){
        String versonCode="";
        try {
            PackageManager mPackageManager=mContext.getPackageManager();
            PackageInfo mPackageInfo=mPackageManager.getPackageInfo(mContext.getPackageName(), 0);
            versonCode=mPackageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versonCode;
    }
}
