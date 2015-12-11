package com.phonemate.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.base.BaseActivity;
import com.phonemate.dialog.QuestionDialog;
import com.phonemate.utils.IntentUtils;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.SettingUtils;
import com.rxx.fast.view.ViewInject;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/2 12:18
 * 修改人：colorful
 * 修改时间：15/12/2 12:18
 * 修改备注：
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener, UmengUpdateListener {
    @ViewInject(id = R.id.mTvCentre)
    TextView mTvCentre;

    @ViewInject(id = R.id.mTvRight)
    TextView mTvRight;

    @ViewInject(id = R.id.mTvLeft, click = true)
    TextView mTvLeft;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;


    @ViewInject(id = R.id.layout_add_init_setting, click = true)
    private LinearLayout layout_add_init_setting;

    @ViewInject(id = R.id.layout_introduce, click = true)
    private LinearLayout layout_introduce;

    @ViewInject(id = R.id.layout_advice, click = true)
    private LinearLayout layout_advice;

    @ViewInject(id = R.id.layout_about_us, click = true)
    private LinearLayout layout_about_us;

    @ViewInject(id = R.id.layout_market, click = true)
    private LinearLayout layout_market;

    @ViewInject(id = R.id.layout_update, click = true)
    private LinearLayout layout_update;

    @ViewInject(id = R.id.tv_has_update)
    private TextView tvHasUpdate;

    @ViewInject(id = R.id.checkbox_show_flaot)
    private CheckBox mCheckBox;
    private QuestionDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void bingViewFinish() {
        mTitleColor = R.color.color_blue_trans_AA;
        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_blue_trans_AA));
        mTvCentre.setText("设置");
        mTvLeft.setText("返回");
        mTvRight.setText("");

        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(this);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.forceUpdate(mActivity);

        dialog=new QuestionDialog(mActivity);

        mCheckBox.setChecked(SettingUtils.isOpenFloatView(mActivity));
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingUtils.setIsOpenFloatView(mActivity, isChecked);
                IntentUtils.startCoreService(mActivity);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==layout_add_init_setting){
            mActivity.startActivity(new Intent(mActivity,InitSettingActivity.class));
        }else if(v==layout_market){
            Uri uri = Uri.parse("market://details?id="+getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if(v==layout_about_us){
            mActivity.startActivity(new Intent(mActivity,AboutUsActivity.class));
        }else if(v==layout_update){
            mProgressDialog.show("检查更新");
            UmengUpdateAgent.forceUpdate(mActivity);
        }else if(v==layout_advice){
//            FeedbackAgent agent = new FeedbackAgent(mActivity);
//            agent.startFeedbackActivity();
        }else  if(v==layout_introduce){
            dialog.mDialog.show();
        }else if(v==mTvLeft){
            mActivity.finish();
        }
    }

    @Override
    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
        switch (updateStatus) {
            case UpdateStatus.Yes: // has update
                if (mProgressDialog.dialogIsShow()) {
                    UmengUpdateAgent.showUpdateDialog(mActivity, updateInfo);
                }
                tvHasUpdate.setText("有新版本(" + updateInfo.version + ")");
                break;
            case UpdateStatus.No: // has no update
                tvHasUpdate.setText("已是最新版本");
                if (mProgressDialog.dialogIsShow()) {
                    MessageUtils.alertMessageCENTER("已是最新版本");
                }
                break;
            case UpdateStatus.NoneWifi: // none wifi
                tvHasUpdate.setText("已是最新版本");
                if (mProgressDialog.dialogIsShow()) {
                    MessageUtils.alertMessageCENTER("请在 wifi 下操作");
                }
                break;
            case UpdateStatus.Timeout: // time out
                tvHasUpdate.setText("已是最新版本");
                if (mProgressDialog.dialogIsShow()) {
                    MessageUtils.alertMessageCENTER("检查新版本超时");
                }
                break;
        }
        mProgressDialog.dissmissProgressDialog();
    }
}
