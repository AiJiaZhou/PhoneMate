package com.phonemate.utils;


import android.content.Context;
import android.net.ConnectivityManager;

import java.lang.reflect.Method;

/**
 * 数据流量工具类
 * User: RanCQ
 * Date: 2015/8/26
 * QQ  :392663986
 * TEL : 15310880724
 */
public class DataTrafficUtils {

    private final static  String GET_DATA_TRAFIC_STATUS_METHOD = "getMobileDataEnabled";
    private final  static String SET_MOBILE_DATA_ENABLED="setMobileDataEnabled";
    public static boolean isOpen(Context context){
        boolean isOpen=false;
        ConnectivityManager mConnectivityManager=
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Method method = mConnectivityManager.getClass().getMethod(GET_DATA_TRAFIC_STATUS_METHOD);
            isOpen= (boolean) method.invoke(mConnectivityManager);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return isOpen;
    }

    public static void operation(boolean isOpen,Context context){
        if(CommonUtils.getSDK()>20){

        }else {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            try {
                Method[] methods = connectivityManager.getClass().getDeclaredMethods();
                Method method = connectivityManager.getClass().getDeclaredMethod(SET_MOBILE_DATA_ENABLED, boolean.class);
                method.invoke(connectivityManager, isOpen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
