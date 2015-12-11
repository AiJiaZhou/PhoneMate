package com.phonemate.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.base.BaseActivity;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ResUtils;
import com.rxx.fast.view.ViewInject;

/**
 * 初始设置
 * User: RanCQ
 * Date: 2015/9/28
 */
public class InitSettingActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(id = R.id.mTvCentre)
    TextView mTvCentre;

    @ViewInject(id = R.id.mTvRight)
    TextView mTvRight;

    @ViewInject(id = R.id.mTvLeft, click = true)
    TextView mTvLeft;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;

    @ViewInject(id = R.id.layout_floatview_setting,click = true)
    private LinearLayout layoutFloatSetting;

    @ViewInject(id = R.id.layout_selfstart_setting,click = true)
    private LinearLayout layoutSelfStartSetting;

    private boolean mIsFirstSetting=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_setting);
    }

    @Override
    public void bingViewFinish() {

        mTitleColor = R.color.color_blue_trans_AA;
        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_blue_trans_AA));
        mTvCentre.setText("初始设置");
        mTvLeft.setText("返回");
        mTvRight.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == mTvLeft) {
            mActivity.finish();
        } else if (v == layoutFloatSetting) {
            MessageUtils.alertMessageCENTER("请勾选显示悬浮窗");
            try {
                Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.putExtra("extra_pkgname", mActivity.getPackageName());
                intent.setClassName("com.miui.securitycenter",
                        "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                startActivity(intent);
            } catch (Exception e) {
                Uri packageURI = Uri.parse("package:" + mActivity.getPackageName());
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                startActivity(intent);
            }
        } else if (v == layoutSelfStartSetting) {
            MessageUtils.alertMessageCENTER("请开启手机伴侣自启动开关");
            try {
                ComponentName target = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                Intent intent = new Intent();
                intent.setComponent(target);
                startActivity(intent);
            } catch (Exception e) {
                try {
                    Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    localIntent.setComponent(new ComponentName("com.android.settings", "com.miui.securitycenter.permission.AppPermissionsEditor"));
                    localIntent.putExtra("extra_package_uid", mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0).applicationInfo.uid);
                    startActivity(localIntent);
                } catch (Exception e2) {
                    try {
                        ComponentName target = new ComponentName("com.android.settings", "com.miui.securitycenter.Main");
                        Intent intent = new Intent();
                        intent.setComponent(target);
                        startActivity(intent);
                    } catch (Exception e1) {
                        try {
                            ComponentName target = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.MainAcitivty");
                            Intent intent = new Intent();
                            intent.setComponent(target);
                            startActivity(intent);
                        } catch (Exception Exception) {
                            Exception.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
