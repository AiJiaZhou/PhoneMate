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
public class HongBaoRules extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

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

    //后台抢红包
    @ViewInject(id = R.id.mLayoutLS, click = true)
    private LinearLayout mLayoutLS;

    @ViewInject(id = R.id.mCbSP)
    private CheckBox mCbSP;

    //自动回复
    @ViewInject(id = R.id.mLayoutAutoAnswer, click = true)
    private LinearLayout mLayoutAutoAnswer;

    @ViewInject(id = R.id.mCbAutoAanswer)
    private CheckBox mCbAutoAanswer;

//    回复抢到的钱数
    @ViewInject(id = R.id.mLayoutAnswerMoeny, click = true)
    private LinearLayout mLayoutAnswerMoeny;

    @ViewInject(id = R.id.mCBAnswerMoeny)
    private CheckBox mCBAnswerMoeny;
//
    //@发红包的人
    @ViewInject(id = R.id.mLayoutOthers, click = true)
    private LinearLayout mLayoutOthers;

    @ViewInject(id = R.id.mCbOthers)
    private CheckBox mCbOthers;

    //自定义回复内容
    @ViewInject(id = R.id.mLayoutDIY, click = true)
    private LinearLayout mLayoutDIY;

    @ViewInject(id = R.id.mCbDIY)
    private CheckBox mCbDIY;

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
        }else if(v==mLayoutAnswerMoeny){
            mCBAnswerMoeny.setChecked(!mCBAnswerMoeny.isChecked());
        }else if(v==mLayoutAutoAnswer){
            mCbAutoAanswer.setChecked(!mCbAutoAanswer.isChecked());
        }else if(v==mLayoutDIY){
            mCbDIY.setChecked(!mCbDIY.isChecked());
        }else if(v==mLayoutHB){
            mCbHB.setChecked(!mCbHB.isChecked());
        }else if(v==mLayoutLS){
            mCbSP.setChecked(!mCbSP.isChecked());
        }else if(v==mLayoutOthers){
            mCbOthers.setChecked(!mCbOthers.isChecked());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView==mCBAnswerMoeny){

        }else if(buttonView==mCbAutoAanswer){

        }else if(buttonView==mCbDIY){

        }else if(buttonView==mCbHB){

        }else if(buttonView==mCbOthers){

        }else if(buttonView==mCbSP){

        }
    }
}
