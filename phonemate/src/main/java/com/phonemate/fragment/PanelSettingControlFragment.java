package com.phonemate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.phonemate.R;
import com.phonemate.adapter.PaenlSettingControlAdapter;
import com.phonemate.base.BaseFragment;
import com.phonemate.dialog.SelectPanelMenuDialog;
import com.phonemate.model.CoreMenuSelectedDbModel;
import com.phonemate.model.panelmodel.NullEntity;
import com.phonemate.model.panelmodel.OtherAppsEntity;
import com.phonemate.model.panelmodel.PanelMenuEntity;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.FastDB;
import com.rxx.fast.view.ViewInject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
public class PanelSettingControlFragment extends BaseFragment implements AdapterView.OnItemClickListener, SelectPanelMenuDialog.PanelMenuSelect {
    @ViewInject(id = R.id.mLayoutPanel)
    private LinearLayout mLayoutPanel;

    @ViewInject(id = R.id.mGridView, itemClick = true)
    private GridView mGridView;

    private FastDB mDb;


    private List<CoreMenuSelectedDbModel> mMenuSelectedDbModelList;

    private List<PanelMenuEntity> mPanelMenuEntitieList;

    private PaenlSettingControlAdapter mPanelMenuAdapter;

    private SelectPanelMenuDialog panelMenuDialog;

    @Override
    public View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pannelsetting_control, container, false);
    }

    @Override
    public void bingViewFinish() {
        ViewGroup.LayoutParams layout = mLayoutPanel.getLayoutParams();
        layout.width = (int) (WindowUtils.getWIndowWidth(mActivity) * 0.8);
        layout.height = (int) (WindowUtils.getWIndowWidth(mActivity) * 0.8);
        mDb = mApplication.fastDB;
        mPanelMenuEntitieList = new ArrayList<>();
        mMenuSelectedDbModelList = mDb.queryAll(CoreMenuSelectedDbModel.class);
        for (int i = 0; i < 9; i++) {
            mPanelMenuEntitieList.add(new NullEntity(mActivity));
        }
        //用户有选择
        for (int i=0; i < mMenuSelectedDbModelList.size(); i++) {
            if (mMenuSelectedDbModelList.get(i).getType() == PanelMenuEntity.OTHER_APP) {
                OtherAppsEntity otherAppsEntity=new OtherAppsEntity(mActivity, mMenuSelectedDbModelList.get(i).getPackageName()
                        , mMenuSelectedDbModelList.get(i).getClassName(), mMenuSelectedDbModelList.get(i).getAppName());
                mPanelMenuEntitieList.remove(mMenuSelectedDbModelList.get(i).getPosition());
                mPanelMenuEntitieList.add(mMenuSelectedDbModelList.get(i).getPosition(),otherAppsEntity);
            } else {
                try {
                    Class clazz = Class.forName(mMenuSelectedDbModelList.get(i).getClassName());
                    Constructor constructor = clazz.getConstructor(Context.class);
                    mPanelMenuEntitieList.remove(mMenuSelectedDbModelList.get(i).getPosition());
                    mPanelMenuEntitieList.add(mMenuSelectedDbModelList.get(i).getPosition(),(PanelMenuEntity) constructor.newInstance(mActivity));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

    mPanelMenuAdapter=new PaenlSettingControlAdapter(mPanelMenuEntitieList, mActivity);

    mGridView.setAdapter(mPanelMenuAdapter);
}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (panelMenuDialog == null) {
                panelMenuDialog = new SelectPanelMenuDialog(mActivity, this, mDb);
            }
            panelMenuDialog.show(position);
    }

    //菜单更改
    @Override
    public void panelMenuSelect(int position, PanelMenuEntity panelMenuEntity, int type) {
        //存入数据库
        CoreMenuSelectedDbModel entity = new CoreMenuSelectedDbModel();
        entity.setAppName(panelMenuEntity.menuName);
        entity.setClassName(panelMenuEntity.mClassName);
        entity.setPackageName(panelMenuEntity.mPackName);
        entity.setPosition(position);
        entity.setType(type);
        //选择位置用户以前未添加菜单
        if (position >= mMenuSelectedDbModelList.size()) {
            mDb.insert(entity);
        } else {
            //选择位置用户已经添加菜单
            mDb.delete(CoreMenuSelectedDbModel.class,"position=?",new String[] {""+position});
            mDb.insert(entity);
        }
        mPanelMenuEntitieList.remove(position);
        mPanelMenuEntitieList.add(position, panelMenuEntity);
        mPanelMenuAdapter.notifyDataSetChanged();
    }
}
