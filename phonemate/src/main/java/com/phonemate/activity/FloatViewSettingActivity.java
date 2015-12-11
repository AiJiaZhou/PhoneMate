package com.phonemate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.adapter.FloatViewSettingColorAdapter;
import com.phonemate.base.BaseActivity;
import com.phonemate.global.GlobalUtils;
import com.phonemate.model.FloatViewSettingSelectColorEntity;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.SettingUtils;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.adapter.BaseRecyclerView;
import com.rxx.fast.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/16 17:56
 * 修改人：colorful
 * 修改时间：15/11/16 17:56
 * 修改备注：
 */
public class FloatViewSettingActivity extends BaseActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener,BaseRecyclerView.OnItemClickListener<FloatViewSettingSelectColorEntity> {

    @ViewInject(id = R.id.mTvCentre)
    TextView mTvCentre;

    @ViewInject(id = R.id.mTvRight)
    TextView mTvRight;

    @ViewInject(id = R.id.mTvLeft,click = true)
    TextView mTvLeft;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;


    @ViewInject(id = R.id.mTvSize)
    TextView mTvSize;

    @ViewInject(id = R.id.mTvRadius)
    TextView mTvRadius;

    @ViewInject(id = R.id.mTvTrans)
    TextView mTvTrans;

    @ViewInject(id = R.id.mSbRadius)
    SeekBar mSbRadius;

    @ViewInject(id = R.id.mSbSize)
    SeekBar mSbSize;

    @ViewInject(id = R.id.mSbTrans)
    SeekBar mSbTrans;

    @ViewInject(id = R.id.mLayoutPanel)
    private LinearLayout mLayoutPanel;

    @ViewInject(id = R.id.mRecyclerView)
    private RecyclerView mRecyclerView;

    private List<FloatViewSettingSelectColorEntity> mList;
    private FloatViewSettingColorAdapter mColorAdapter;

    private Intent mIntentUpdateBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floatview_setting);
    }

    @Override
    public void bingViewFinish() {
        mTitleColor= R.color.color_blue_trans_AA;
        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_blue_trans_AA));
        mTvCentre.setText("悬浮菜单");
        mTvLeft.setText("返回");
        mTvRight.setText("");

        mIntentUpdateBroadCastReceiver=new Intent();
        mIntentUpdateBroadCastReceiver.setAction(GlobalUtils.BROADCAST_RECEIVER_UPDATE_FLOAT_VIEW);

        ViewGroup.LayoutParams layout=mLayoutPanel.getLayoutParams();
        layout.width= (int) (WindowUtils.getWIndowWidth(mActivity)*0.8);
        layout.height= (int) (WindowUtils.getWIndowWidth(mActivity)*0.8);

        mSbTrans.setOnSeekBarChangeListener(this);
        mSbRadius.setOnSeekBarChangeListener(this);
        mSbSize.setOnSeekBarChangeListener(this);

        mSbTrans.setProgress((int) (SettingUtils.getFLoatViewTrans(mActivity) * 50));
        mSbRadius.setProgress((int) (SettingUtils.getFLoatViewRadius(mActivity)*50));
        mSbSize.setProgress((int) (SettingUtils.getFLoatViewSize(mActivity)*50));


        mList=new ArrayList<>();
        for(int i=0;i<FloatViewSettingSelectColorEntity.mMaxNum;i++){
            FloatViewSettingSelectColorEntity mFloatView=new FloatViewSettingSelectColorEntity();
            if(i== SettingUtils.getFLoatViewColor(mActivity)){
                mFloatView.isSelect=true;
            }else{
                mFloatView.isSelect=false;
            }
            mList.add(mFloatView);
        }
        mColorAdapter=new FloatViewSettingColorAdapter(mList,mActivity);
        mColorAdapter.setOnItemClickListener(this);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mColorAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v==mTvLeft){
            mActivity.finish();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar==mSbRadius){
            if(progress>=50) {
                mTvRadius.setText(progress*2 + "%");
                SettingUtils.setFLoatViewRadius(mActivity, progress / 50f);
            }else{
                mTvRadius.setText((progress-100)*2 + "%");
                SettingUtils.setFLoatViewRadius(mActivity, progress/50f);
            }
        }else if(seekBar==mSbSize){
            if(progress>=50) {
                mTvSize.setText(progress * 2 + "%");
                SettingUtils.setFLoatViewSize(mActivity,progress /50f);
            }else{
                mTvSize.setText((progress-100)*2 + "%");
                SettingUtils.setFLoatViewSize(mActivity, progress/50f);
            }
        }else if(seekBar==mSbTrans){
            if(progress>=50) {
                mTvTrans.setText(progress*2 + "%");
                SettingUtils.setFLoatViewTrans(mActivity, (progress ) / 50f);
            }else{
                mTvTrans.setText((progress-100)*2 + "%");
                SettingUtils.setFLoatViewTrans(mActivity, progress  / 50f);
            }
        }
        sendBroadcast(mIntentUpdateBroadCastReceiver);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position, FloatViewSettingSelectColorEntity o) {
        for(FloatViewSettingSelectColorEntity entity:mList){
            entity.isSelect=false;
        }
        mList.get(position).isSelect=true;
        SettingUtils.setFLoatViewColor(mActivity, position);
        mColorAdapter.notifyDataSetChanged();
        sendBroadcast(mIntentUpdateBroadCastReceiver);
    }
}
