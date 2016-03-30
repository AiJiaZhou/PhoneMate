package com.phonemate.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.base.BaseActivity;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.SettingUtils;
import com.rxx.fast.view.ViewInject;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/14 22:04
 * 修改人：colorful
 * 修改时间：15/12/14 22:04
 * 修改备注：
 */
public class HongBaoRules extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @ViewInject(id = R.id.mTvCentre)
    TextView mTvCentre;

    @ViewInject(id = R.id.mTvRight)
    TextView mTvRight;

    @ViewInject(id = R.id.mTvLeft, click = true)
    TextView mTvLeft;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;

    //抢红包开关
    @ViewInject(id = R.id.mLayoutHB, click = true)
    private LinearLayout mLayoutHB;

    @ViewInject(id = R.id.mCbHB)
    private CheckBox mCbHB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hongbao_ruels);
    }

    @Override
    public void bingViewFinish() {
        mTitleColor = R.color.color_red;
        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_red));
        mTvCentre.setTextColor(ResUtils.getColor(mActivity, R.color.color_yellow));
        mTvLeft.setTextColor(ResUtils.getColor(mActivity, R.color.color_yellow));
        mTvRight.setTextColor(ResUtils.getColor(mActivity, R.color.color_yellow));

        mTvCentre.setText("使用说明-必读");
        mTvLeft.setText(R.string._return);
        mTvRight.setText("");
        mTvRight.setVisibility(View.INVISIBLE);
        mCbHB.setOnCheckedChangeListener(this);

        mCbHB.setChecked(SettingUtils.isHBBg(mActivity));
    }

    @Override
    public void onClick(View v) {
        if (v == mTvLeft) {
            mActivity.finish();
        } else if (v == mLayoutHB) {
            mCbHB.setChecked(!mCbHB.isChecked());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SettingUtils.setisHBBg(mActivity, isChecked);
    }
}
