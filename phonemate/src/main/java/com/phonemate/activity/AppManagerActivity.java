package com.phonemate.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.adapter.FragmentAdapter;
import com.phonemate.base.BaseFragmentActivity;
import com.phonemate.fragment.AppManagerApkFragment;
import com.phonemate.fragment.AppManagerAppFragment;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.ViewUtils;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/2 12:18
 * 修改人：colorful
 * 修改时间：15/12/2 12:18
 * 修改备注：
 */
public class AppManagerActivity extends BaseFragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    @ViewInject(id = R.id.mTvCentre)
    TextView mTvCentre;

    @ViewInject(id = R.id.mTvRight)
    TextView mTvRight;

    @ViewInject(id = R.id.mTvLeft, click = true)
    TextView mTvLeft;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;


    @ViewInject(id = R.id.mTvApp,click = true)
    TextView mTvApp;

    @ViewInject(id = R.id.mTvApk,click = true)
    TextView mTvApk;

    @ViewInject(id = R.id.mViewPager)
    ViewPager mViewPager;

    TextView mLastView;

    @ViewInject(id = R.id.mMoveView)
    View mMoveView;

    private FragmentAdapter mAdapter;
    private List<Fragment> mFragmentList;

    private int mColorOn;
    private int mColorOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appmanager);
    }

    @Override
    public void bingViewFinish() {
        mColorOn=ResUtils.getColor(mActivity,R.color.color_orange);
        mColorOff=ResUtils.getColor(mActivity,R.color.color_white);


        mTitleColor= R.color.color_blue_trans_AA;
        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_blue_trans_AA));
        mTvCentre.setText("软件管理");
        mTvLeft.setText("返回");
        mTvRight.setText("");

        ViewGroup.LayoutParams layoutParams=mMoveView.getLayoutParams();
        layoutParams.width= WindowUtils.getWIndowWidth(mActivity)/2;
        layoutParams.height=-1;

        mLastView=mTvApp;
        mLastView.setTextColor(mColorOn);

        mFragmentList=new ArrayList<>();
        mFragmentList.add(new AppManagerAppFragment());
        mFragmentList.add(new AppManagerApkFragment());
        mAdapter=new FragmentAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==mTvLeft){
            mActivity.finish();
        }else if(v==mTvApk){
            mLastView.setTextColor(mColorOff);
            ViewUtils.cursorMoveBtnItemAnimation(mLastView, mTvApk, mMoveView);
            mLastView=mTvApk;
            mLastView.setTextColor(mColorOn);
            if(mViewPager.getCurrentItem()!=1){
                mViewPager.setCurrentItem(1);
            }
        }else if(v==mTvApp){
            mLastView.setTextColor(mColorOff);
            ViewUtils.cursorMoveBtnItemAnimation(mLastView, mTvApp, mMoveView);
            mLastView=mTvApp;
            mLastView.setTextColor(mColorOn);
            if(mViewPager.getCurrentItem()!=0){
                mViewPager.setCurrentItem(0);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                onClick(mTvApp);
                break;
            case 1:
                onClick(mTvApk);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
