package com.rxx.fast;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rxx.fast.view.ViewUtils;

/**
 * User: RanCQ
 * Date: 2015/10/9
 */
public abstract class FastFragment extends android.support.v4.app.Fragment {
    protected View mRootView =null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mRootView ==null){
            mRootView=init(inflater, container, savedInstanceState);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        }
        ViewUtils.initViews(this, mRootView);
        bingViewFinish();
        return mRootView;
    }

    /**
     * 返回加载当前FragmentView,不能处理业务
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    public abstract View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    public abstract void bingViewFinish();
}
