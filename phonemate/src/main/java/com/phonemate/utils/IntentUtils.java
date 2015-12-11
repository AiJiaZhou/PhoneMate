package com.phonemate.utils;

import android.content.Context;
import android.content.Intent;

import com.phonemate.service.CoreService;

/**
 * User: RanCQ
 * Date: 2015/8/14
 * QQ  :392663986
 * TEL : 15310880724
 */
public class IntentUtils {

    /**开启核心service*/
    public static  void startCoreService(Context context){
        Intent intent=new Intent(context, CoreService.class);
        context.startService(intent);
    }

}
