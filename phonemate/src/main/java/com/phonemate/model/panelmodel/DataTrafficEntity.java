package com.phonemate.model.panelmodel;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.DataTrafficUtils;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ResUtils;

/**
 * 数据流量工具类
 * User: RanCQ
 * Date: 2015/8/26
 * QQ  :392663986
 * TEL : 15310880724
 */
public class DataTrafficEntity extends PanelMenuEntity{
    public static final String NAME="数据开关";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.DataTrafficEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_mobile_data_on;
    public static final int ICON_OFF= R.mipmap.icon_panelmenu_mobile_data_off;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };
    public DataTrafficEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        changeStatus();
    }

    @Override
    public void init() {
        isNeedCloseCoreDialog=true;
        menuName=NAME;
        menuIcon=ResUtils.getDrawable(context,ICON_ON);
    }

    public void changeIcon(){
        if(DataTrafficUtils.isOpen(context)){
            menuIcon= ResUtils.getDrawable(context,ICON_ON);
        }else{
            menuIcon= ResUtils.getDrawable(context,ICON_OFF);
        }
    }

    public void changeStatus(){
        if(DataTrafficUtils.isOpen(context)){
            MessageUtils.alertMessageCENTER("正在关闭数据流量");
            menuIcon= ResUtils.getDrawable(context,ICON_OFF);
            DataTrafficUtils.operation(false, context);
        }else{
            MessageUtils.alertMessageCENTER("正在开启数据流量");
            menuIcon= ResUtils.getDrawable(context,ICON_ON);
            DataTrafficUtils.operation(true, context);
        }
    }
}
