package com.phonemate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.adapter.HomeMenuAdapter;
import com.phonemate.base.BaseActivity;
import com.phonemate.dialog.QuestionDialog;
import com.phonemate.model.HomeMenuEntity;
import com.phonemate.utils.IntentUtils;
import com.phonemate.utils.MemoryUtils;
import com.phonemate.utils.SettingUtils;
import com.phonemate.widget.MemoryClearView;
import com.rxx.fast.view.ViewInject;
import com.umeng.update.UmengUpdateAgent;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/11 19:50
 * 修改人：colorful
 * 修改时间：15/11/11 19:50
 * 修改备注：
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    @ViewInject(id = R.id.mIvGRZX,click = true)
    private ImageView mIvGRZX;

    @ViewInject(id = R.id.mLayoutDrwaer,click = true)
    private DrawerLayout mLayoutDrwaer;


    @ViewInject(id = R.id.mLayoutUserInfo,click = true)
    private FrameLayout mLayoutUserInfo;

    @ViewInject(id = R.id.mGvMenu,itemClick = true)
    private GridView mGvMenu;

    @ViewInject(id = R.id.mTvClearMemory,click = true)
    private TextView mTvClearMemory;

    @ViewInject(id = R.id.mMemoryCliearView)
    private MemoryClearView mMemoryCliearView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        IntentUtils.startCoreService(mActivity);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void bingViewFinish() {
        IntentUtils.startCoreService(mActivity);
        mTitleColor=R.color.color_green;
        mGvMenu.setAdapter(new HomeMenuAdapter());

        //友盟自动更新
        UmengUpdateAgent.update(this);

        mLayoutDrwaer.setDrawerLockMode(mLayoutDrwaer.LOCK_MODE_LOCKED_CLOSED);

        if(SettingUtils.isSHowReadMeDialog(mActivity)){
            mTvClearMemory.postDelayed(new Runnable() {
                @Override
                public void run() {
                    new QuestionDialog(mActivity).mDialog.show();
                }
            }, 1000);
            SettingUtils.setIsSHowReadMeDialog(mActivity,false);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==mIvGRZX){
            openDawer();
        }else if(v==mLayoutUserInfo){
            closeDawer();
        }else if(v==mTvClearMemory){
            MemoryUtils.clear(mActivity);
            mMemoryCliearView.startAnimation();
        }
    }

    public void openDawer(){
        mLayoutDrwaer.openDrawer(GravityCompat.START);
    }

    public void closeDawer(){
        mLayoutDrwaer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(mActivity, HomeMenuEntity.mMenuAction[position]));
    }
}
