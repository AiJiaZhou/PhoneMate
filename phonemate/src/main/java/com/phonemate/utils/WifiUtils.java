package com.phonemate.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * User: RanCQ
 * Date: 2015/8/26
 * QQ  :392663986
 * TEL : 15310880724
 */
public class WifiUtils {

    /**wifi是否打开*/
    public static boolean isOpen(Context context){
        boolean isOpne=false;
        WifiManager wifiManager= (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
       switch (wifiManager.getWifiState()){
           case WifiManager.WIFI_STATE_DISABLED:
               isOpne=false;
               break;
           case WifiManager.WIFI_STATE_DISABLING:
               isOpne=false;
               break;
           case WifiManager.WIFI_STATE_ENABLED:
               isOpne=true;
               break;
           case WifiManager.WIFI_STATE_ENABLING:
               isOpne=true;
               break;
           case WifiManager.WIFI_STATE_UNKNOWN:
               isOpne=false;
               break;
       }
        return isOpne;
    }



    /**关闭打开wifi*/
    public static void operation(boolean isOpne,Context context){
        WifiManager wifiManager= (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(isOpne);
    }
}
