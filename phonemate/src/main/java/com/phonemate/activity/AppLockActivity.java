package com.phonemate.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.base.BaseActivity;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.SettingUtils;
import com.phonemate.widget.GestureLockView;
import com.rxx.fast.view.ViewInject;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/1 10:28
 * 修改人：colorful
 * 修改时间：15/12/1 10:28
 * 修改备注：
 */
public class AppLockActivity extends BaseActivity implements  GestureLockView.OnGestureFinishListener{

    private String appName;
    private Drawable appDrawable;
    private String appPackage;

    @ViewInject(id = R.id.mIvApp)
    private ImageView mIvApp;

    @ViewInject(id = R.id.mTvPasswordTitle)
    private TextView mTvPasswordTitle;

    @ViewInject(id = R.id.mGesture)
    private GestureLockView mGesture;

    private boolean isGoLunch=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applock);
    }

    @Override
    public void bingViewFinish() {
        mTitleColor= R.color.color_app_lock_bg;
        appPackage=getIntent().getStringExtra("packName");
        try {
            appDrawable=mActivity.getPackageManager().getApplicationIcon(appPackage);
            appName = mActivity.getPackageManager().getApplicationLabel
                    (mActivity.getPackageManager().getApplicationInfo(appPackage,
                            PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mTvPasswordTitle.setText(appName);
        mIvApp.setImageDrawable(appDrawable);
        mGesture.setOnGestureFinishListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SettingUtils.setIsOpenAppLockActiviy(mActivity, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SettingUtils.setIsOpenAppLockActiviy(mActivity, false);
        if(isGoLunch) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            mActivity.startActivity(home);
        }
    }

    @Override
    public void OnGestureFinish(String key) {
        if(key.equals(SettingUtils.getAppLockPassword(mActivity))){
            isGoLunch=false;
            mActivity.finish();
        }else{
            MessageUtils. alertMessageCENTER("密码错误");
        }
    }
}
