package com.phonemate.controlpannel;

import android.content.Intent;
import android.view.View;

import com.phonemate.PMApplication;
import com.phonemate.floatmenu.FloatView;
import com.phonemate.service.CoreService;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/18 18:37
 * 修改人：colorful
 * 修改时间：15/11/18 18:37
 * 修改备注：
 */
public class FloatViewClickListener  implements FloatView.FLoatViewClickListener{
    private PMApplication mApplication;
    public PanelControl mPanelControl;


    public FloatViewClickListener(PMApplication mApplication) {
        this.mApplication = mApplication;
        mPanelControl=new PanelControl(mApplication);
    }

    @Override
    public void OnClick(View v) {
        mPanelControl.showPanelView();
        mApplication.startService(new Intent(mApplication, CoreService.class));
    }
}
