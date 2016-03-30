package com.phonemate.model.panelmodel;

import android.content.Context;
import android.hardware.Camera;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.R;
import com.phonemate.utils.FlashLightUtils;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ResUtils;

/**
 * User: RanCQ
 * Date: 2015/8/18
 * QQ  :392663986
 * TEL : 15310880724
 */
public class FlashLightEntity extends PanelMenuEntity {

    public static final String NAME = "手电筒";
    public static final String CLASS_NAME = "com.phonemate.model.panelmodel.FlashLightEntity";
    public static final int ICON_ON = R.mipmap.icon_panelmenu_flashlight_on;
    public static final int ICON_OFF = R.mipmap.icon_panelmenu_flashlight_off;
    public static final Object[] INSERT_VALUES = {ICON_ON, CLASS_NAME, NAME};
    private Camera camera;

    public FlashLightEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        if(camera==null){
            camera = Camera.open();
        }
        if (FlashLightUtils.isOpen(camera)) {
            FlashLightUtils.turnLightOff(camera);
            if(camera!=null) {
                camera.release();
                camera = null;
            }
        } else {
            if (FlashLightUtils.hasFlashLight(context)) {
                FlashLightUtils.turnLightOn(camera);//

            } else {
                MessageUtils.alertMessageCENTER("开启手电筒失败，您的设备不支持。");
            }
        }
        changeIcon();
    }

    @Override
    public void init() {
        isNeedCloseCoreDialog = true;
        menuName=NAME;
        menuIcon=ResUtils.getDrawable(context,ICON_ON);
    }

    @Override
    public void updateView() {
        super.updateView();
        changeIcon();
    }

    public void changeIcon(){
        if (FlashLightUtils.isOpen(camera)) {
            menuIcon= ResUtils.getDrawable(context,R.mipmap.icon_panelmenu_flashlight_on);
        }else{
            menuIcon= ResUtils.getDrawable(context,R.mipmap.icon_panelmenu_flashlight_off);

        }
    }

}
