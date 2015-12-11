package com.phonemate.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.phonemate.PMApplication;
import com.rxx.fast.FastFragment;

/**
 * User: RanCQ
 * Date: 2015/10/10
 */
public abstract class BaseFragment extends FastFragment {

    protected PMApplication mApplication;
    protected Activity mActivity;
    protected Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
        mActivity = this.getActivity();
        mApplication = (PMApplication) this.getActivity().getApplication();
    }
}
