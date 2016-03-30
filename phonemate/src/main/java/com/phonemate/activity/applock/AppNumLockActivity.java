package com.phonemate.activity.applock;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.base.BaseActivity;
import com.phonemate.utils.SettingUtils;
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
public class AppNumLockActivity extends BaseActivity {

    private String appName;
    private Drawable appDrawable;
    private String appPackage;

    @ViewInject(id = R.id.mIvApp)
    private ImageView mIvApp;

    @ViewInject(id = R.id.mTvPasswordTitle)
    private TextView mTvPasswordTitle;

    private boolean isGoLunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_num_lock);
    }

    @Override
    public void bingViewFinish() {
        mTitleColor = R.color.color_app_lock_bg;
        appPackage = getIntent().getStringExtra("packName");
        try {
            appDrawable = mActivity.getPackageManager().getApplicationIcon(appPackage);
            appName = mActivity.getPackageManager().getApplicationLabel
                    (mActivity.getPackageManager().getApplicationInfo(appPackage,
                            PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mTvPasswordTitle.setText(appName);
        mIvApp.setImageDrawable(appDrawable);
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
        if (isGoLunch) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            mActivity.startActivity(home);
        }
    }
//    isGoLunch = false;
//    mActivity.finish();
}
