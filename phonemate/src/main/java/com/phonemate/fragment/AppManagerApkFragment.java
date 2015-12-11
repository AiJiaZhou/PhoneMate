package com.phonemate.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.adapter.AppManagerApkAdapter;
import com.phonemate.asynctask.ApkFileAsynctask;
import com.phonemate.base.BaseFragment;
import com.phonemate.model.AppManagerApkEntity;
import com.phonemate.utils.MessageUtils;
import com.rxx.fast.view.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
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
public class AppManagerApkFragment extends BaseFragment implements AdapterView.OnItemClickListener, ApkFileAsynctask.ApkScanningFinishListener
        , View.OnClickListener {

    @ViewInject(id = R.id.mListView, itemClick = true)
    private ListView mListView;

    @ViewInject(id = R.id.progress_bar_layout)
    private LinearLayout progress_bar_layout;

    @ViewInject(id = R.id.mTvAll, click = true)
    private TextView mTvAll;

    @ViewInject(id = R.id.mTvDelete, click = true)
    private TextView mTvDelete;

    @ViewInject(id = R.id.mTvInstall, click = true)
    private TextView mTvInstall;


    private AppManagerApkAdapter managerApkAdapter;

    private List<AppManagerApkEntity> mList;

    private boolean isAllChose = false;

    @Override
    public View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appmanager_apk, container, false);
    }

    @Override
    public void bingViewFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new ApkFileAsynctask(this, mActivity.getPackageManager()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new ApkFileAsynctask(this, mActivity.getPackageManager()).execute();
        }

        mList = new ArrayList<>();
        managerApkAdapter = new AppManagerApkAdapter(mList, mActivity);
        mListView.setAdapter(managerApkAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mList.get(position).isChoose = !mList.get(position).isChoose;
        managerApkAdapter.notifyDataSetChanged();
    }

    @Override
    public void scanningFilish(List<AppManagerApkEntity> list) {
        progress_bar_layout.setVisibility(View.GONE);
        if(list==null || list.size()==0){
            MessageUtils.alertMessageCENTER("没有扫描到安装包");
            return ;
        }
        mList.clear();
        mList.addAll(list);
        managerApkAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v == mTvAll) {
            isAllChose = !isAllChose;
            for (AppManagerApkEntity apk : mList) {
                apk.isChoose = isAllChose;
            }
            if (isAllChose) {
                mTvAll.setText("取消全选");
            } else {
                mTvAll.setText("全选");
            }
            managerApkAdapter.notifyDataSetChanged();
        } else if (v == mTvDelete) {
            for (Iterator<AppManagerApkEntity> it = mList.iterator(); it.hasNext(); ) {
                AppManagerApkEntity apk=it.next();
                if (apk.isChoose) {
                    try{
                        File file=new File(apk.appPath);
                        file.delete();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    it.remove();
                }
            }
            managerApkAdapter.notifyDataSetChanged();
        } else if (v == mTvInstall) {
            for (AppManagerApkEntity apk : mList) {
                if (apk.isChoose) {

                    Uri uri = Uri.fromFile(new File(apk.appPath));

                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    intent.setDataAndType(uri, "application/vnd.android.package-archive");

                    startActivity(intent);
                }
            }
        }
    }

}
