package com.phonemate.model.panelmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.activity.PanelSettingActivity;
import com.phonemate.utils.ResUtils;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/20 16:45
 * 修改人：colorful
 * 修改时间：15/11/20 16:45
 * 修改备注：
 */
public class NullEntity extends PanelMenuEntity{

    public static final String NAME="添加";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.NullEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_add;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };

    public NullEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(context, PanelSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        isNeedCloseCoreDialog=true;
        menuIcon= ResUtils.getDrawable(context, ICON_ON);
        menuName=NAME;
    }
}
