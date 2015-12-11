package com.phonemate.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phonemate.R;
import com.phonemate.base.BaseFragment;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/18 15:56
 * 修改人：colorful
 * 修改时间：15/11/18 15:56
 * 修改备注：
 */
public class PanelSettingSwitchFragment extends BaseFragment{
    @Override
    public View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pannelsetting_switch, container, false);
    }

    @Override
    public void bingViewFinish() {

    }
}
