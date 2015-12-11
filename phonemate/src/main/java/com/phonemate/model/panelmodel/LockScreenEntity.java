package com.phonemate.model.panelmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.activity.LockScreenTransActivity;
import com.phonemate.utils.ResUtils;

/**
 * User: RanCQ
 * Date: 2015/8/20
 * QQ  :392663986
 * TEL : 15310880724
 */
public class LockScreenEntity extends PanelMenuEntity {

    //插入小工具列表数据库需要
    public static final String NAME="锁屏";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.LockScreenEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_lockscreen;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };


    public LockScreenEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(context, LockScreenTransActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }




    @Override
    public void init() {
        isNeedCloseCoreDialog=true;
        menuIcon= ResUtils.getDrawable(context,ICON_ON);
        menuName=NAME;
    }
}
