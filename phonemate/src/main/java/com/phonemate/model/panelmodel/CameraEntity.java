package com.phonemate.model.panelmodel;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.CameraUtils;
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
public class CameraEntity extends PanelMenuEntity{

    public static final String NAME="相机";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.CameraEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_camera;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };

    public CameraEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        CameraUtils.launchCamera(context,true);

    }

    @Override
    public void init() {
        isNeedCloseCoreDialog=true;
        menuIcon= ResUtils.getDrawable(context, ICON_ON);
        menuName=NAME;
    }
}
