package com.phonemate.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.base.BaseActivity;
import com.phonemate.utils.AccessibilityUtils;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.SettingUtils;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.view.ViewInject;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/18 09:51
 * 修改人：colorful
 * 修改时间：15/12/18 09:51
 * 修改备注：
 */
public class QHBActivity extends BaseActivity implements View.OnClickListener{

    private final String noteDoing="PhoneMate正在为您抢红包";

    private final String noteCanDo="为了抢红包能够正常运行,请点击这里到设置-辅助功能设置-开启PhoneMate";


    @ViewInject(id = R.id.mTvCentre)
    TextView mTvCentre;

    @ViewInject(id = R.id.mTvRight,click = true)
    TextView mTvRight;

    @ViewInject(id = R.id.mTvLeft, click = true)
    TextView mTvLeft;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;

    @ViewInject(id = R.id.mIvChineseKnot)
    ImageView mIvChineseKnot;

    @ViewInject(id = R.id.mIvTop)
    private ImageView mIvTop;

    @ViewInject(id = R.id.mTvNote,click = true)
    private TextView mTvNote;

    private AnimationDrawable mAnimationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qhb);
        SettingUtils.setHbIspen(mActivity,true);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void bingViewFinish() {
        mTitleColor = R.color.color_red;

        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_red));
        mTvCentre.setTextColor(ResUtils.getColor(mActivity, R.color.color_yellow));
        mTvLeft.setTextColor(ResUtils.getColor(mActivity, R.color.color_yellow));
        mTvRight.setTextColor(ResUtils.getColor(mActivity, R.color.color_yellow));
        mTvLeft.setText("返回");
        mTvCentre.setText("抢红包");
        mTvRight.setText("");
        Drawable drawable=ResUtils.getDrawable(mActivity,R.mipmap.icon_hb_help);
        drawable.setBounds(0, 0, (int) (drawable.getMinimumWidth()*1.5), (int) (drawable.getMinimumWidth()*1.5));
        mTvRight.setCompoundDrawables(drawable,null,null,null);


        ViewGroup.LayoutParams layoutParams=mIvTop.getLayoutParams();
        layoutParams.width=-1;
        layoutParams.height=  (WindowUtils.getWIndowWidth(mActivity)/2);

        mIvChineseKnot.setImageResource(R.drawable.chinese_knot_anim);
        FrameLayout.LayoutParams layoutParamsTop= (FrameLayout.LayoutParams) mIvChineseKnot.getLayoutParams();
        layoutParamsTop.topMargin= (int) (WindowUtils.getWIndowWidth(mActivity)/2);
        mAnimationDrawable= (AnimationDrawable) mIvChineseKnot.getDrawable();
        mAnimationDrawable.start();


        FrameLayout.LayoutParams layoutParamsTVNode= (FrameLayout.LayoutParams) mTvNote.getLayoutParams();
        layoutParamsTVNode.width=WindowUtils.getWIndowWidth(mActivity)/10*7;
        layoutParamsTVNode.topMargin=(int) (WindowUtils.getWIndowWidth(mActivity)/4);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v==mTvLeft){
            mActivity.finish();
        }else if(v==mTvRight){
            startActivity(new Intent(mActivity,HongBaoRules.class));
        }else if(v==mTvNote){
            if(!AccessibilityUtils.isAccessibleEnabled(mActivity)){
                AccessibilityUtils.goAccessSetting(mActivity);
            }else{
                MessageUtils.alertMessageCENTER("您已开启辅助功能,无需在重复开启");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SettingUtils.setHbIspen(mActivity, false);
    }
}
