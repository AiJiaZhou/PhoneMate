package com.phonemate.fragment;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.phonemate.R;
import com.phonemate.adapter.AppManagerAppAdapter;
import com.phonemate.asynctask.AppManagerAppEntityAsynctask;
import com.phonemate.base.BaseFragment;
import com.phonemate.dialog.AppDetailDialog;
import com.phonemate.model.AppManagerAppEntity;
import com.rxx.fast.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/18 15:56
 * 修改人：colorful
 * 修改时间：15/11/18 15:56
 * 修改备注：
 */
public class AppManagerAppFragment extends BaseFragment  implements AppManagerAppEntityAsynctask.LoadAppsFinish,AdapterView.OnItemClickListener{

    @ViewInject(id = R.id.mListView,itemClick = true)
    private ListView mListView;

    @ViewInject(id = R.id.progress_bar_layout)
    private LinearLayout progress_bar_layout;

    private List<AppManagerAppEntity> mAppList;

    private AppManagerAppAdapter mAdapter;

    private AppManagerAppEntityAsynctask mAsyncTask;

    private AppDetailDialog mDialog;

    @Override
    public View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return   inflater.inflate(R.layout.fragment_appmanager_app, container, false);
    }

    @Override
    public void bingViewFinish() {
        mAppList=new ArrayList<>();
        mAdapter=new AppManagerAppAdapter(mAppList,mActivity);
        mListView.setAdapter(mAdapter);
        mDialog=new AppDetailDialog(mActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new AppManagerAppEntityAsynctask(this,mActivity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }else{
            new AppManagerAppEntityAsynctask(this,mActivity).execute();
        }
        progress_bar_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadAppFinish(List<AppManagerAppEntity> list) {
        progress_bar_layout.setVisibility(View.GONE);
        mAppList.clear();
        mAppList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mDialog.mIvApp.setImageDrawable(mAppList.get(position).appDrawable);
        mDialog.mTvClass.setText(mAppList.get(position).appClass);
        mDialog.mTvInstallTime.setText(mAppList.get(position).appInstallTime);
        mDialog.mTvName.setText(mAppList.get(position).appName);
        mDialog.mTvPackage.setText(mAppList.get(position).appPackage);
        mDialog.mTvUpdateTime.setText(mAppList.get(position).appUpdateTime);
        mDialog.mTvVersion.setText("V"+mAppList.get(position).appVersion);
        mDialog.mDialog.show();
    }
}
