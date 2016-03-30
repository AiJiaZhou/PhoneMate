package com.phonemate.lockscreent;

import android.content.Context;
import android.view.WindowManager;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/11 16:03
 * 修改人：colorful
 * 修改时间：15/12/11 16:03
 * 修改备注：
 */
public abstract  class LockScreen {

    protected Context mContext;

    protected WindowManager mWindowManager;

    protected   WindowManager.LayoutParams mLayoutParams;

    public LockScreen(Context context){
        mContext=context;
    }


    /**解锁*/
    public abstract void unLock();

    /**锁屏*/
    public abstract void lock();

    /**更新界面*/
    public  abstract void  updateView();


}
