package com.phonemate.model.panelmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.ResUtils;

/**
 * User: RanCQ
 * Date: 2015/8/20
 * QQ  :392663986
 * TEL : 15310880724
 */
public class GoLuncherEntity extends PanelMenuEntity{

    public static final String NAME="主屏幕";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.GoLuncherEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_go_luncher;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };

    public GoLuncherEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(home);

    }

    @Override
    public void init() {
        isNeedCloseCoreDialog=false;
        menuIcon= ResUtils.getDrawable(context, ICON_ON);
        menuName=NAME;
    }
}
