package com.phonemate.model.panelmodel;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.RingerUtils;

/**
 * 震动菜单
 * User: RanCQ
 * Date: 2015/8/23
 * QQ  :392663986
 * TEL : 15310880724
 */
public class VibrateEntity extends  PanelMenuEntity{

    public static final String NAME="震动";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.VibrateEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_vibrate_on;
    public static final int ICON_OFF= R.mipmap.icon_panelmenu_vibrate_off;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };

    public VibrateEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        if(RingerUtils.isVibarte(context)){
            RingerUtils.setRingerMode(RingerUtils.isSilent(context),false,context);
        }else{
            RingerUtils.setRingerMode(RingerUtils.isSilent(context),true,context);
        }
        updateView();
    }

    @Override
    public void init() {
        isNeedCloseCoreDialog=false;
        menuIcon= ResUtils.getDrawable(context, ICON_ON);
        menuName=NAME;
        updateView();
    }

    public void updateView(){
        if(RingerUtils.isVibarte(context)){
            menuIcon= ResUtils.getDrawable(context, ICON_ON);
        }else{
            menuIcon= ResUtils.getDrawable(context, ICON_OFF);
        }
    }
}
