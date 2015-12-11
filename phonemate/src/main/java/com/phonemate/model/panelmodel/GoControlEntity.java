package com.phonemate.model.panelmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.activity.HomeActivity;
import com.phonemate.utils.ResUtils;

/**
 * User: RanCQ
 * Date: 2015/8/20
 * QQ  :392663986
 * TEL : 15310880724
 */
public class GoControlEntity extends PanelMenuEntity {

    //插入小工具列表数据库需要
    public static final String NAME="控制中心";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.GoControlEntity";
    public static final int ICON_ON= R.mipmap. icon_panelmenu_control;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };


    public GoControlEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
       Intent intent=new Intent(context, HomeActivity.class);
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
