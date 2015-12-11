package com.phonemate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.Receiver.AppLockReceiver;
import com.phonemate.adapter.AppLockListAdapter;
import com.phonemate.asynctask.GetApplockEntityAsynctask;
import com.phonemate.base.BaseActivity;
import com.phonemate.global.GlobalUtils;
import com.phonemate.model.AppLockEntity;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.SettingUtils;
import com.phonemate.widget.GestureLockView;
import com.rxx.fast.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/30 16:42
 * 修改人：colorful
 * 修改时间：15/11/30 16:42
 * 修改备注：
 */
public class AppLockSettingActivity extends BaseActivity implements View.OnClickListener,GestureLockView.OnGestureFinishListener,
        GetApplockEntityAsynctask.LoadAppsFinish,AdapterView.OnItemClickListener{
    @ViewInject(id = R.id.mTvCentre)
    TextView mTvCentre;

    @ViewInject(id = R.id.mTvRight)
    TextView mTvRight;

    @ViewInject(id = R.id.mTvLeft,click = true)
    TextView mTvLeft;

    @ViewInject(id = R.id.mTvPasswordTitle)
    TextView mTvPasswordTitle;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;


    @ViewInject(id = R.id.mGesture)
    private GestureLockView mGesture;




    @ViewInject(id=R.id.mLayoutGesture,click = true)
    private LinearLayout mLayoutGesture;

    @ViewInject(id=R.id.mLayouSettingPassword,click = true)
    private LinearLayout mLayouSettingPassword;


    @ViewInject(id=R.id.mLayouIsOpen,click = true)
    private LinearLayout mLayouIsOpen;

    @ViewInject(id=R.id.mCBMenuStatus,click = true)
    private CheckBox mCBMenuStatus;

    @ViewInject(id=R.id.mListView)
    private ListView mListView;

    @ViewInject(id = R.id.mScrollView)
    private ScrollView mScrollView;

    /**设置密码第一次*/
    private int type_password_first_input =1;

    /**设置密码 确认*/
    private int type_password_first_config =2;

    /**输入密码*/
    private int type_password_imput=3;

    /**修改输入密码*/
    private int type_password_change=4;

    private int mPasswordType;

    private String mPasswordFirstInput;

    private List<AppLockEntity> mAppLockList;

    private AppLockListAdapter mAppLockAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applock_setting);
    }

    @Override
    public void bingViewFinish() {
        mTitleColor= R.color.color_blue_trans_AA;
        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_blue_trans_AA));
        mTvCentre.setText("应用锁");
        mTvLeft.setText("返回");
        mTvRight.setText("");
        mGesture.setOnGestureFinishListener(this);
        mCBMenuStatus.setChecked(SettingUtils.isOpenAppLock(mActivity));

        //是否开启密码监听
        mCBMenuStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!SettingUtils.isSettingAppLockPassword(mActivity)){//用户还没有设置密码
                        mCBMenuStatus.setChecked(false);
                        showGesturePassword(type_password_first_input, "请输入应用锁密码");
                        return ;
                    }
                }
                Intent intent=new Intent();
                intent.setAction(GlobalUtils.BROADCAST_RECEIVER_APP_LOCK_CHANGE);
                intent.putExtra(AppLockReceiver.INTENT_KEY,isChecked);
                mActivity.sendBroadcast(intent);
                SettingUtils.setIsOpenAppLock(mActivity,isChecked);
            }
        });

        mAppLockList=new ArrayList<>();
        mAppLockAdapter=new AppLockListAdapter(mAppLockList,mActivity);
        mListView.setAdapter(mAppLockAdapter);
        mListView.setOnItemClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(SettingUtils. isSettingAppLockPassword(mActivity)){ //查看用户是否设置了密码
            showGesturePassword(type_password_imput,"请输入应用锁密码后访问");
        }else{
            mLayoutGesture.setVisibility(View.GONE);
            new GetApplockEntityAsynctask(this,mActivity).execute();
            mProgressDialog.mDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v==mTvLeft){
            mActivity.finish();
        }else if(v==mLayouIsOpen){
            mCBMenuStatus.setChecked(!mCBMenuStatus.isChecked());
        } else if(v==mLayouSettingPassword){
            //上次是否配置密码
            if(SettingUtils.isSettingAppLockPassword(mActivity)){
                showGesturePassword(type_password_change,"请输入应用锁旧密码");
            }else{
                showGesturePassword(type_password_first_input, "请输入应用锁密码");
            }
        }
    }

    @Override
    public void OnGestureFinish(String key) {
        if(mPasswordType==type_password_imput){//输入验证回掉
            if(key.equals(SettingUtils.getAppLockPassword(mActivity))){
                mLayoutGesture.setVisibility(View.GONE);
                new GetApplockEntityAsynctask(this,mActivity).execute();
                mProgressDialog.mDialog.show();
            }else{
                MessageUtils.alertMessageCENTER("密码验证失败,请重新输入");
            }
        }else if(mPasswordType== type_password_first_input){//修改密码第一次输入回掉
            //第一次密码验证成功
            mPasswordFirstInput = key;
            showGesturePassword(type_password_first_config, "请确认应用锁密码");
        }else if(mPasswordType== type_password_first_config){//修改密码第二次输入回掉
            if(key.equals(mPasswordFirstInput)) {
                //第二次密码验证成功
                mLayoutGesture.setVisibility(View.GONE);
                MessageUtils.alertMessageCENTER("密码设置成功");
                SettingUtils.setAppLockPassword(mActivity,mPasswordFirstInput);
            }else{
                //第二次验证失败,回到第一次
                showGesturePassword(type_password_first_input, "请输入新的应用锁密码");
                MessageUtils.alertMessageCENTER("两次密码输入不一致,请重新输入");
            }

        }else if(mPasswordType==type_password_change){//修改 密码输入旧密码
            if(key.equals(SettingUtils.getAppLockPassword(mActivity))) {
                //旧密码验证成功
                showGesturePassword(type_password_first_input, "请输入新的应用锁密码");
                MessageUtils.alertMessageCENTER("验证成功");
            }else {
                MessageUtils.alertMessageCENTER("密码验证失败,请重新输入");
            }
        }
    }

    public void showGesturePassword(int type,String text){
        mTvPasswordTitle.setText(text);
        mPasswordType=type;
        mLayoutGesture.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadAppFinish(List<AppLockEntity> list) {
        mAppLockList.addAll(list);
        mAppLockAdapter.notifyDataSetChanged();
        mProgressDialog.mDialog.dismiss();
        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 200l);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAppLockList.get(position).setIsLock(!mAppLockList.get(position).getIsLock());
        if(mAppLockList.get(position).getIsLock()){
            mApplication.fastDB.insert(mAppLockList.get(position));
        }else{
            mApplication.fastDB.delete(AppLockEntity.class,"className=?",new String[]{mAppLockList.get(position).getClassName()});
        }
        mAppLockAdapter.notifyDataSetChanged();
        Intent intent=new Intent();
        intent.setAction(GlobalUtils.BROADCAST_RECEIVER_APP_LOCK_CHANGE);
        intent.putExtra(AppLockReceiver.INTENT_KEY,mCBMenuStatus.isChecked());
        mActivity.sendBroadcast(intent);
    }
}
