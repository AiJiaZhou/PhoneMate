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
import com.phonemate.fragment.FloatViewSettingFragment;
import com.phonemate.fragment.PanelSettingBgFragment;
import com.phonemate.fragment.PanelSettingControlFragment;
import com.phonemate.fragment.PanelSettingDoubleClickFragment;
import com.phonemate.fragment.PanelSettingLongClickFragment;
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
 * 创建时间：15/11/17 22:54
 * 修改人：colorful
 * 修改时间：15/11/17 22:54
 * 修改备注：
 */
public class PanelSettingActivity extends BaseFragmentActivity
        implements View.OnClickListener,ViewPager.OnPageChangeListener{
    @ViewInject(id = R.id.mTvCentre)
    TextView mTvCentre;

    @ViewInject(id = R.id.mTvRight)
    TextView mTvRight;

    @ViewInject(id = R.id.mTvLeft,click = true)
    TextView mTvLeft;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;

    @ViewInject(id = R.id.mTvBg,click = true)
    TextView mTvBg;

    @ViewInject(id = R.id.mTvControl,click = true)
    TextView mTvControl;

    @ViewInject(id = R.id.mTvSwitch,click = true)
    TextView mTvSwitch;

    @ViewInject(id = R.id.mTvDoubleClick,click = true)
    TextView mTvDoubleClick;

    @ViewInject(id = R.id.mTvFloatView,click = true)
    TextView mTvFloatView;


    @ViewInject(id = R.id.mViewPager)
    ViewPager mViewPager;

    TextView mLastView;

    @ViewInject(id = R.id.mMoveView,click = true)
    View mMoveView;

    private FragmentAdapter mAdapter;
    private List<Fragment> mFragmentList;

    private int mColorOn;
    private int mColorOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_setting);
    }

    @Override
    public void bingViewFinish() {
        mTitleColor= R.color.color_blue_trans_AA;
        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_blue_trans_AA));
        mTvCentre.setText("面板管理");
        mTvLeft.setText("返回");
        mTvRight.setText("");

        ViewGroup.LayoutParams layoutParams=mMoveView.getLayoutParams();
        layoutParams.width= WindowUtils.getWIndowWidth(mActivity)/5;
        layoutParams.height=-1;
        mLastView=mTvControl;

        mColorOn=ResUtils.getColor(mActivity,R.color.color_orange);
        mColorOff=ResUtils.getColor(mActivity,R.color.color_white);

        mLastView.setTextColor(mColorOn);

        mFragmentList=new ArrayList<>();
        mFragmentList.add(new PanelSettingControlFragment());
        mFragmentList.add(new PanelSettingLongClickFragment());
        mFragmentList.add(new PanelSettingDoubleClickFragment());
        mFragmentList.add(new PanelSettingBgFragment());
        mFragmentList.add(new FloatViewSettingFragment());

        mAdapter=new FragmentAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==mTvLeft){
            mActivity.finish();
        }else if(v==mTvBg){
            mLastView.setTextColor(mColorOff);
            ViewUtils.cursorMoveBtnItemAnimation(mLastView, mTvBg, mMoveView);
            mLastView=mTvBg;
            mLastView.setTextColor(mColorOn);
            if(mViewPager.getCurrentItem()!=3){
                mViewPager.setCurrentItem(3);
            }
        }else if(v==mTvControl){
            mLastView.setTextColor(mColorOff);
            ViewUtils.cursorMoveBtnItemAnimation(mLastView, mTvControl, mMoveView);
            mLastView=mTvControl;
            mLastView.setTextColor(mColorOn);
            if(mViewPager.getCurrentItem()!=0){
                mViewPager.setCurrentItem(0);
            }
        }else if(v==mTvSwitch){
            mLastView.setTextColor(mColorOff);
            ViewUtils.cursorMoveBtnItemAnimation(mLastView, mTvSwitch, mMoveView);
            mLastView=mTvSwitch;
            mLastView.setTextColor(mColorOn);
            if(mViewPager.getCurrentItem()!=1){
                mViewPager.setCurrentItem(1);
            }
        }else if(v==mTvDoubleClick){
            mLastView.setTextColor(mColorOff);
            ViewUtils.cursorMoveBtnItemAnimation(mLastView,mTvDoubleClick,mMoveView);
            mLastView=mTvDoubleClick;
            mLastView.setTextColor(mColorOn);
            if(mViewPager.getCurrentItem()!=2){
                mViewPager.setCurrentItem(2);
            }
        }else if(v==mTvFloatView){
            mLastView.setTextColor(mColorOff);
            ViewUtils.cursorMoveBtnItemAnimation(mLastView,mTvFloatView,mMoveView);
            mLastView=mTvFloatView;
            mLastView.setTextColor(mColorOn);
            if(mViewPager.getCurrentItem()!=4){
                mViewPager.setCurrentItem(4);
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
                onClick(mTvControl);
                break;
            case 1:
                onClick(mTvSwitch);
                break;
            case 2:
                onClick(mTvDoubleClick);
                break;
            case 3:
                onClick(mTvBg);
                break;
            case 4:
                onClick(mTvFloatView);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
