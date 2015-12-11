package com.phonemate.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.adapter.PanelMenuSelectAdapter;
import com.phonemate.asynctask.LoadingAppsAsynctask;
import com.phonemate.model.ToolsDBModel;
import com.phonemate.model.panelmodel.OtherAppsEntity;
import com.phonemate.model.panelmodel.PanelMenuEntity;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.ViewUtils;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.FastDB;
import com.rxx.fast.dialog.DialogHolder;
import com.rxx.fast.dialog.DialogUtils;
import com.rxx.fast.utils.LUtils;
import com.rxx.fast.view.ViewInject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/24 22:40
 * 修改人：colorful
 * 修改时间：15/11/24 22:40
 * 修改备注：
 */
public class SelectPanelMenuDialog extends DialogHolder implements View.OnClickListener, AdapterView.OnItemClickListener ,LoadingAppsAsynctask.LoadAppsFinish {

    @ViewInject(id = R.id.mLayoutBg)
    public LinearLayout mLayoutBg;

    @ViewInject(id = R.id.mMoveView)
    public View mMoveView;

    @ViewInject(id = R.id.mTvTools, click = true)
    public TextView mTvTools;

    public TextView mlastView;

    @ViewInject(id = R.id.mTvApps, click = true)
    public TextView mTvApps;

    @ViewInject(id = R.id.mListView, itemClick = true)
    public ListView mListView;

    private List<PanelMenuEntity> mTools;

    private List<PanelMenuEntity> mApps;

    private PanelMenuSelect mPanelMenuSelect;

    private PanelMenuSelectAdapter mToolsAdapter;

    private PanelMenuSelectAdapter mAppsAdapter;

    private LoadingAppsAsynctask mLoadingAppsAsynctask;

    List<ToolsDBModel> mToolsDBModelList;

    private int position;

    private int width;
    private int height;

    public SelectPanelMenuDialog(Context mActivity, PanelMenuSelect mPanelMenuSelect, FastDB mFastDb) {
        super(mActivity, R.layout.dialog_panelmenu_select);
        this.mPanelMenuSelect = mPanelMenuSelect;

        mTools = new ArrayList<>();
        mApps = new ArrayList<>();

        mLoadingAppsAsynctask=new LoadingAppsAsynctask(this,mActivity);
          mToolsDBModelList= mFastDb.queryAll(ToolsDBModel.class);

        for (ToolsDBModel entity : mToolsDBModelList) {
            LUtils.i(entity.toString());
            try {
                Class clazz = Class.forName(entity.getClassName());
                Constructor constructor = clazz.getConstructor(Context.class);
                mTools.add((PanelMenuEntity) constructor.newInstance(mActivity));
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
        mToolsAdapter = new PanelMenuSelectAdapter(mTools, mActivity);
        mAppsAdapter = new PanelMenuSelectAdapter(mApps, mActivity);
        mListView.setAdapter(mToolsAdapter);
        mListView.setOnItemClickListener(this);

        mlastView=mTvTools;
        ViewGroup.LayoutParams layoutParams=mMoveView.getLayoutParams();
        layoutParams.width=width/2;
        mLoadingAppsAsynctask.execute(mActivity);
    }

    @Override
    public Dialog createDialog() {
        width = WindowUtils.getWIndowWidth(mActivity) / 5 * 4;
        height = WindowUtils.getWindowHeigh(mActivity) / 10 * 9;
        Dialog dialog = DialogUtils.createDialogCenter(mActivity, mRootView, R.style.Select_PannelEntity_Dialog, Gravity.CENTER,
                width, height, true);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if(v==mTvApps){//1
            mListView.setAdapter(mAppsAdapter);
            ViewUtils.cursorMoveBtnItemAnimation(mlastView, mTvApps, mMoveView);
            mTvApps.setTextColor(ResUtils.getColor(mActivity,R.color.color_orange));
            mTvTools.setTextColor(ResUtils.getColor(mActivity,R.color.color_text_gray));
            mlastView=mTvApps;
        }else if(v==mTvTools){//-1
            mListView.setAdapter(mToolsAdapter);
            ViewUtils.cursorMoveBtnItemAnimation(mlastView, mTvTools, mMoveView);
            mTvTools.setTextColor(ResUtils.getColor(mActivity, R.color.color_orange));
            mTvApps.setTextColor(ResUtils.getColor(mActivity,R.color.color_text_gray));
            mlastView=mTvTools;
        }
    }


    public void show(int position) {
        this.position = position;
        mDialog.show();
        ViewUtils.cursorMoveBtnItemAnimation(mlastView, mlastView, mMoveView);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mPanelMenuSelect != null) {
            if(mlastView==mTvApps){
                mPanelMenuSelect.panelMenuSelect(this.position, mApps.get(position),PanelMenuEntity.OTHER_APP);
            }else if(mlastView==mTvTools){
                mTools.get(position).mClassName=mToolsDBModelList.get(position).getClassName();
                mPanelMenuSelect.panelMenuSelect(this.position, mTools.get(position),PanelMenuEntity.TOOLS);
            }
        }
        this.mDialog.dismiss();
    }

    @Override
    public void loadAppFinish(List<OtherAppsEntity> list) {
        mApps.addAll( list);
    }

    public interface PanelMenuSelect {
        public void panelMenuSelect(int position, PanelMenuEntity panelMenuEntity,int type);
    }
}
