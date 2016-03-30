package com.phonemate.utils;

import android.content.Context;
import android.content.Intent;

import com.phonemate.activity.applock.AppGestureLockActivity;
import com.phonemate.service.CoreService;

/**
 * User: RanCQ
 * 所以的activity和service跳转都再这儿管理
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


    /**开启锁屏界面*/
    public static  void startLockScreen(Context context){

    }

    /**开启应用锁界面*/
    public static void startAppLockScreen(Context context,String packname){
//        if(context instanceof Activity){
            Intent intent = new Intent(context, AppGestureLockActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packName", packname);
            context.startActivity(intent);
//        }else {
//            Intent intent = new Intent(context, AppGestureLockActivity.class);
//            intent.putExtra(TagService.EXTRA_SAVE_MSGS, msgs);
//            intent.putExtra(TagService.EXTRA_STARRED, starred);
//            intent.putExtra(TagService.EXTRA_PENDING_INTENT, pending);
//            context.startService(intent);
//        }
    }

}
