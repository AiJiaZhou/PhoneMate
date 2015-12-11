package com.phonemate.model.panelmodel;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.ResUtils;

/**
 * User: RanCQ
 * 1.0版本不开放
 * Date: 2015/8/20
 * QQ  :392663986
 * TEL : 15310880724
 */
public class GoBackEntity extends PanelMenuEntity {

    public static final String NAME="回退";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.GoBackEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_goback;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };
    public GoBackEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void init() {
        isNeedCloseCoreDialog=false;
        menuIcon= ResUtils.getDrawable(context,ICON_ON);
        menuName=NAME;
    }
}
