package com.phonemate.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.phonemate.R;
import com.phonemate.adapter.PanelMenuClickAdapter;
import com.phonemate.base.BaseFragment;
import com.phonemate.model.FloatviewClickDbEntity;
import com.phonemate.model.panelmodel.GoBackEntity;
import com.phonemate.model.panelmodel.GoControlEntity;
import com.phonemate.model.panelmodel.GoLuncherEntity;
import com.phonemate.model.panelmodel.LockScreenEntity;
import com.phonemate.model.panelmodel.NotificationBarEntity;
import com.phonemate.model.panelmodel.PanelMenuEntity;
import com.phonemate.model.panelmodel.RecentApps;
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
public class PanelSettingDoubleClickFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @ViewInject(id = R.id.mListView,itemClick = true)
    private ListView mListView;

    private PanelMenuClickAdapter mMenuClickAdapter;

    private List<PanelMenuEntity> mMenuEntityList;
    @Override
    public View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pannelsetting_doubleclick, container, false);
    }

    @Override
    public void bingViewFinish() {
        mMenuEntityList=new ArrayList<>();
        mMenuEntityList.add(new GoLuncherEntity(mActivity));//主屏幕
        mMenuEntityList.add(new GoBackEntity(mActivity));//返回
        mMenuEntityList.add(new RecentApps(mActivity));//最近任务
        mMenuEntityList.add(new LockScreenEntity(mActivity));//锁屏
        mMenuEntityList.add(new NotificationBarEntity(mActivity));//通知lan
        mMenuEntityList.add(new GoControlEntity(mActivity));//控制中心
        mMenuClickAdapter=new PanelMenuClickAdapter(mMenuEntityList,mActivity, FloatviewClickDbEntity.DOUBLE_CLICK);
        mListView.setAdapter(mMenuClickAdapter);

        List<FloatviewClickDbEntity> clickDbEntities = mApplication.fastDB.queryByWhere(FloatviewClickDbEntity.class,
                "type=?", new String[]{FloatviewClickDbEntity.DOUBLE_CLICK
                        + ""});
        if (clickDbEntities != null && clickDbEntities.size() > 0) {
            if (clickDbEntities.get(0).getClassName().equals(GoLuncherEntity.class.getName())){
                mMenuClickAdapter.setSelect(0);
            }else if (clickDbEntities.get(0).getClassName().equals(GoBackEntity.class.getName())){
                mMenuClickAdapter.setSelect(1);
            }else if (clickDbEntities.get(0).getClassName().equals(RecentApps.class.getName())){
                mMenuClickAdapter.setSelect(2);
            }else if (clickDbEntities.get(0).getClassName().equals(LockScreenEntity.class.getName())){
                mMenuClickAdapter.setSelect(3);
            }else if (clickDbEntities.get(0).getClassName().equals(NotificationBarEntity.class.getName())){
                mMenuClickAdapter.setSelect(4);
            }else if (clickDbEntities.get(0).getClassName().equals(GoControlEntity.class.getName())){
                mMenuClickAdapter.setSelect(5);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mMenuClickAdapter.setSelect(position);
    }
}
