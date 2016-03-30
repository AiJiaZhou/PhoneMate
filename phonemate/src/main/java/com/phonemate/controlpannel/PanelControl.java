package com.phonemate.controlpannel;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.phonemate.PMApplication;
import com.phonemate.R;
import com.phonemate.adapter.PanelMenuAdapter;
import com.phonemate.model.CoreMenuSelectedDbModel;
import com.phonemate.model.PanelBgColorEntity;
import com.phonemate.model.panelmodel.DirectorEntity;
import com.phonemate.model.panelmodel.GoBackEntity;
import com.phonemate.model.panelmodel.NullEntity;
import com.phonemate.model.panelmodel.OtherAppsEntity;
import com.phonemate.model.panelmodel.PanelMenuEntity;
import com.phonemate.model.panelmodel.SwitchEntity;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.SettingUtils;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.dialog.DialogHolder;
import com.rxx.fast.dialog.DialogUtils;
import com.rxx.fast.utils.LUtils;
import com.rxx.fast.view.ViewInject;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/18 18:07
 * 修改人：colorful
 * 修改时间：15/11/18 18:07
 * 修改备注：
 */
public class PanelControl extends DialogHolder implements AdapterView.OnItemClickListener{

    @ViewInject(id = R.id.mGridView)
    public GridView mGridView;

    @ViewInject(id = R.id.mLayoutFrameLayout)
    public FrameLayout mLayoutFrameLayout;

    private List<PanelMenuEntity> mMenuList;
    public PanelMenuAdapter mMenuAdapter;

    private int mSize;
    public static float mSizeScore=0.5f;
    private float mSizeZoom=1.0f;

    /**
     * 颜色
     */
    private int mDefaultColor = 0xFFFFFFFF;
    private float mColorZoom=1.0f;
    /**
     * 默认圆角
     */
    private int mRadius = 20; // 8dp 圆角半径
    private float mRadiusZoom=1.0f;

    /**
     * 背景
     */
    private GradientDrawable mBgDrawable;

    private  WindowManager.LayoutParams mDialogLayoutParams;

    private Stack<ListAdapter> mAdapterStack;

    private List<CoreMenuSelectedDbModel> mMenuSelectedDbModelList;

    /**
     *  E push(E item)
     *  把项压入堆栈顶部。
     *  E pop()
     *  移除堆栈顶部的对象，并作为此函数的值返回该对象。
     *  E peek()
     *  查看堆栈顶部的对象，但不从堆栈中移除它。
     *  boolean empty()
     *  测试堆栈是否为空。
     *  int search(Object o)
     *  返回对象在堆栈中的位置，以 1 为基数。
     */

    public PanelControl(Context mActivity) {
        super(mActivity, R.layout.include_pannelcontrol_view);
        mAdapterStack=new Stack();
        mSize= (int) (WindowUtils.getWIndowWidth(mActivity)*mSizeScore);
        mDialogLayoutParams=mDialog.getWindow().getAttributes();
        mBgDrawable = new GradientDrawable();//创建drawable
        mMenuList=new ArrayList<>();

        //添加测试数据
        mMenuSelectedDbModelList =( (PMApplication)mActivity.getApplicationContext()).fastDB.queryAll(CoreMenuSelectedDbModel.class);
        for (int i = 0; i < 9; i++) {
            mMenuList.add(new NullEntity(mActivity));
        }
        //用户有选择
        for (int i=0; i < mMenuSelectedDbModelList.size(); i++) {
            LUtils.i("菜单:"+mMenuSelectedDbModelList.get(i).toString());
            if (mMenuSelectedDbModelList.get(i).getType() == PanelMenuEntity.OTHER_APP) {
                OtherAppsEntity otherAppsEntity=new OtherAppsEntity(mActivity, mMenuSelectedDbModelList.get(i).getPackageName()
                        , mMenuSelectedDbModelList.get(i).getClassName(), mMenuSelectedDbModelList.get(i).getAppName());
                mMenuList.remove(mMenuSelectedDbModelList.get(i).getPosition());
                mMenuList.add(mMenuSelectedDbModelList.get(i).getPosition(),otherAppsEntity);
            } else {
                try {
                    Class clazz = Class.forName(mMenuSelectedDbModelList.get(i).getClassName());
                    Constructor constructor = clazz.getConstructor(Context.class);
                    mMenuList.remove(mMenuSelectedDbModelList.get(i).getPosition());
                    mMenuList.add(mMenuSelectedDbModelList.get(i).getPosition(),(PanelMenuEntity) constructor.newInstance(mActivity));
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



        mMenuAdapter=new PanelMenuAdapter(mMenuList,mActivity);
        mAdapterStack.push(mMenuAdapter);
        mGridView.setAdapter(mAdapterStack.peek());
        mGridView.setOnItemClickListener(this);
        updateView();
    }

    public void showPanelView(){
        if(!mDialog.isShowing()){
            mMenuSelectedDbModelList =( (PMApplication)mActivity.getApplicationContext()).fastDB.queryAll(CoreMenuSelectedDbModel.class);
            for (int i=0; i < mMenuSelectedDbModelList.size(); i++) {
                if (mMenuSelectedDbModelList.get(i).getType() == PanelMenuEntity.OTHER_APP) {
                    OtherAppsEntity otherAppsEntity = new OtherAppsEntity(mActivity, mMenuSelectedDbModelList.get(i).getPackageName()
                            , mMenuSelectedDbModelList.get(i).getClassName(), mMenuSelectedDbModelList.get(i).getAppName());
                    if(!mMenuSelectedDbModelList.get(i).getClassName().equals(mMenuList.get(mMenuSelectedDbModelList.get(i).getPosition()).mClassName)){
                        mMenuList.remove(mMenuSelectedDbModelList.get(i).getPosition());
                        mMenuList.add(mMenuSelectedDbModelList.get(i).getPosition(), otherAppsEntity);
                    }

                } else {
                    try {
                        if(!mMenuSelectedDbModelList.get(i).getClassName().equals(mMenuList.get(mMenuSelectedDbModelList.get(i).getPosition()).mClassName)){
                            Class clazz = Class.forName(mMenuSelectedDbModelList.get(i).getClassName());
                            Constructor constructor = clazz.getConstructor(Context.class);
                            mMenuList.remove(mMenuSelectedDbModelList.get(i).getPosition());
                            mMenuList.add(mMenuSelectedDbModelList.get(i).getPosition(), (PanelMenuEntity) constructor.newInstance(mActivity));
                        }
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
            mMenuAdapter.notifyDataSetChanged();
            mDialog.show();
            MobclickAgent.onResume(mActivity);
        }
    }

    @Override
    public Dialog createDialog() {
        Dialog dialog= DialogUtils.createDialogCenter(mActivity, mRootView,
                R.style.Pannel_Style_Dialog,Gravity.CENTER,
                (int) (mSize*mSizeZoom),(int)  (mSize*mSizeZoom),true);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public void updateView(){
        mRadiusZoom= SettingUtils.getPanelRadius(mActivity);
        mSizeZoom=SettingUtils.getPanelSize(mActivity);
        mColorZoom=SettingUtils.getPanelTrans(mActivity);

        mDefaultColor= PanelBgColorEntity.mColors[SettingUtils.getPanelColor(mActivity)];
        //背景
        mBgDrawable.setColor(mDefaultColor);
        //圆角
        mBgDrawable.setCornerRadius(ResUtils.dp2px(mRadius * mRadiusZoom, mActivity));
        //描边
        mBgDrawable.setStroke(0, mDefaultColor);
        ResUtils.setBackDrawable(mLayoutFrameLayout,mBgDrawable);
        //尺寸
        mDialogLayoutParams.width= (int) (mSize*mSizeZoom);
        mDialogLayoutParams.height=mDialogLayoutParams.width;
        mDialog.getWindow().setAttributes(mDialogLayoutParams);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mMenuList.get(position).isNeedCloseCoreDialog) {
            mDialog.dismiss();
        }

        if(mMenuList.get(position).getClass()==GoBackEntity.class){//回退

        } else if(mMenuList.get(position).getClass()==SwitchEntity.class){//开关

        }else if(mMenuList.get(position).getClass()== DirectorEntity.class){//文件夹

        }else {
            mMenuList.get(position).onClick(parent, view, position, id);
            mMenuAdapter.notifyDataSetChanged();
        }
        MobclickAgent.onResume(mActivity);
    }
}
