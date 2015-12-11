package com.phonemate.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;

/**
 * 项目名称：newzeroshop
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/10/13 21:59
 * 修改人：colorful
 * 修改时间：15/10/13 21:59
 * 修改备注：
 */
public class CommonUtils {
    @TargetApi(Build.VERSION_CODES.M)
    public static int getColor(Context context,int colorId){
        if(SDKUtils.getVersonCode(context)>22){
            return context.getResources().getColor(colorId,null);
        }else{
            return context.getResources().getColor(colorId);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getDrawable(Context context,int drawableId){
        if(SDKUtils.getVersonCode(context)>20){
            return context.getDrawable(drawableId);
        }else{
             return context.getResources().getDrawable(drawableId);
        }
    }

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


    /**获取设备IMEI*/
    public static String getIMEI(Context mContext){
        String str;
        str =((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        return TextUtils.isEmpty(str)?"":str;
    }

    /**获取mac地址*/
    public static String getMAC(Context mContext){
        String str;
        str= ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();
        return TextUtils.isEmpty(str)?"":str;
    }

    /**获取手机sdk版本*/
    public static int getSDK(){
        return Build.VERSION.SDK_INT;
    }

    /**获取手机系统版本*/
    public static String getSystemInformation(){
        return Build.VERSION.RELEASE;
    }

    /**获取手机信息*/
    public static String getPhoneName(){
        return Build.MANUFACTURER+"."+ Build.MODEL;
    }

    /**获取WiFi路由器名称*/
    public static String getWifiName(Context mContext){
        String str;
        str= ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getBSSID();
        return TextUtils.isEmpty(str)?"":str;
    }

    /**获取路由器mac*/
    public static String getWifiMac(Context mContext){
        String str;
        str= ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getSSID();
        return TextUtils.isEmpty(str)?"":str;
    }

    /**2：已root 1未root*/
    public static int isRoot() {
        int isRoot = 1;
        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                isRoot = 1;
            } else {
                isRoot = 2;
            }
        } catch (Exception e) {
        }
        return isRoot;
    }
}
