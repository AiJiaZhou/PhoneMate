package com.phonemate.model.panelmodel;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.WifiUtils;

/**
 * User: RanCQ
 * Date: 2015/8/26
 * QQ  :392663986
 * TEL : 15310880724
 */
public class WifiMenuEntity extends PanelMenuEntity{

    public static final String NAME="WIFI";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.WifiMenuEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_wifi_on;
    public static final int ICON_OFF= R.mipmap.icon_panelmenu_wifi_off;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };



    public WifiMenuEntity(Context context) {
        super(context);
    }


    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        changeWifiStatus();
    }

    @Override
    public void init() {
        isNeedCloseCoreDialog=true;
        menuName=NAME;
        changeWifiIcon();
    }

    public void changeWifiIcon(){
        if(WifiUtils.isOpen(context)){
            menuIcon= ResUtils.getDrawable(context,ICON_ON);
        }else{
            menuIcon= ResUtils.getDrawable(context,ICON_OFF);
        }
    }

    public void changeWifiStatus(){
        if(WifiUtils.isOpen(context)){
            menuIcon= ResUtils.getDrawable(context,ICON_OFF);
            WifiUtils.operation(false, context);
        }else{
            menuIcon= ResUtils.getDrawable(context,ICON_ON);
            WifiUtils.operation(true, context);
        }
    }
}
