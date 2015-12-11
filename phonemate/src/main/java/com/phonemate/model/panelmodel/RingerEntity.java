package com.phonemate.model.panelmodel;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.RingerUtils;

/**
 * 控制声音
 * User: RanCQ
 * Date: 2015/8/20
 * QQ  :392663986
 * TEL : 15310880724
 */
public class RingerEntity extends PanelMenuEntity {

    public static final String NAME = "声音";
    public static final String CLASS_NAME = "com.phonemate.model.panelmodel.RingerEntity";
    public static final int ICON_ON = R.mipmap.icon_panelmenu_ringer_on;
    public static final int ICON_OFF = R.mipmap.icon_panelmenu_ringer_off;
    public static final Object[] INSERT_VALUES = {ICON_ON, CLASS_NAME, NAME};

    public RingerEntity(Context context) {
        super(context);
    }


    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        switchModel();
    }


    @Override
    public void init() {
        isNeedCloseCoreDialog = false;
        menuIcon = ResUtils.getDrawable(context, ICON_ON);
        menuName = NAME;
        updateView();
    }

    public void updateView() {
        if (RingerUtils.isSilent(context)) {
            menuIcon = ResUtils.getDrawable(context, ICON_ON);
        } else {
            menuIcon = ResUtils.getDrawable(context, ICON_OFF);
        }
    }

    public void switchModel() {
        if (RingerUtils.isSilent(context)) {
            RingerUtils.setRingerMode(false, RingerUtils.isVibarte(context), context);
        } else {
            RingerUtils.setRingerMode(true, RingerUtils.isVibarte(context), context);
        }
        updateView();
    }
}
