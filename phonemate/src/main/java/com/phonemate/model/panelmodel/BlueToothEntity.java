package com.phonemate.model.panelmodel;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.BlueToothUtils;
import com.phonemate.utils.DataTrafficUtils;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ResUtils;

/**
 * User: RanCQ
 * Date: 2015/8/26
 * QQ  :392663986
 * TEL : 15310880724
 */
public class BlueToothEntity extends PanelMenuEntity{
    public static final String NAME="蓝牙开关";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.BlueToothEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_bluetooth_on;
    public static final int ICON_OFF= R.mipmap.icon_panelmenu_bluetooth_off;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };
    public BlueToothEntity(Context context) {
        super(context);
    }


    @Override
    public void init() {
        isNeedCloseCoreDialog=true;
        menuName=NAME;
        menuIcon=ResUtils.getDrawable(context,ICON_ON);
    }



    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        changeStatus();
    }

    public void changeIcon(){
        if(DataTrafficUtils.isOpen(context)){
            menuIcon= ResUtils.getDrawable(context, ICON_ON);
        }else{
            menuIcon= ResUtils.getDrawable(context,ICON_OFF);
        }
    }

    public void changeStatus(){
        if(BlueToothUtils.isOpne()){
            MessageUtils.alertMessageCENTER("正在关闭蓝牙");
            menuIcon= ResUtils.getDrawable(context,ICON_OFF);
            BlueToothUtils.operation(false);
        }else{
            MessageUtils.alertMessageCENTER("正在开启蓝牙");
            menuIcon= ResUtils.getDrawable(context, ICON_ON);
            BlueToothUtils.operation(true);
        }
    }
}
