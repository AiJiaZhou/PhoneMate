package com.phonemate.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.base.BaseActivity;
import com.phonemate.utils.ResUtils;
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
public class QingHongBaoSettingActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(id = R.id.mTvCentre)
    TextView mTvCentre;

    @ViewInject(id = R.id.mTvRight)
    TextView mTvRight;

    @ViewInject(id = R.id.mTvLeft, click = true)
    TextView mTvLeft;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qianghongbao);
    }

    @Override
    public void bingViewFinish() {
        mTitleColor = R.color.color_blue_trans_AA;
        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_blue_trans_AA));

        mTvCentre.setText("抢红包");
        mTvLeft.setText(R.string._return);
        mTvRight.setText("");
        mTvRight.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if(v==mTvLeft){
            mActivity.finish();
        }
    }
}
