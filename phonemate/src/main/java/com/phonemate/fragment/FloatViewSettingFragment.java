package com.phonemate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.adapter.FloatViewSettingColorAdapter;
import com.phonemate.base.BaseFragment;
import com.phonemate.global.GlobalUtils;
import com.phonemate.model.FloatViewSettingSelectColorEntity;
import com.phonemate.utils.SettingUtils;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.adapter.BaseRecyclerView;
import com.rxx.fast.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：已废弃,放在panelsetting中去
 * 创建人：colorful
 * 创建时间：15/12/14 21:53
 * 修改人：colorful
 * 修改时间：15/12/14 21:53
 * 修改备注：
 */
public class FloatViewSettingFragment extends BaseFragment implements SeekBar.OnSeekBarChangeListener,
        BaseRecyclerView.OnItemClickListener<FloatViewSettingSelectColorEntity> {
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
    public View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_floatview_setting, container, false);
    }

    @Override
    public void bingViewFinish() {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mColorAdapter);
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
        mActivity.sendBroadcast(mIntentUpdateBroadCastReceiver);
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
        mActivity.sendBroadcast(mIntentUpdateBroadCastReceiver);
    }
}
