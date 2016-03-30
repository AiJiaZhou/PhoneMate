package com.phonemate.utils;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/3 19:02
 * 修改人：colorful
 * 修改时间：15/12/3 19:02
 * 修改备注：
 */

import android.app.Activity;

import com.rxx.fast.utils.LUtils;

import java.util.Stack;

public class ScreenManager {
    private static Stack<Activity> activityStack;
    private static ScreenManager instance;

    private ScreenManager() {
    }

    public int getSize(){
        return activityStack.size();
    }

    public static ScreenManager getScreenManager() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void popActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    public void popActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public Activity currentActivity() {
        try {
            Activity activity = activityStack.lastElement();
            return activity;
        }catch (Exception ex){
            return null;
        }
    }

    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    public  void popAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }

            if(activity.getClass().getName().endsWith("LockActivity")){
                LUtils.i(activity.getClass().getName());
                break ;
            }

            popActivity(activity);
        }
    }
}