package com.phonemate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phonemate.PMApplication;
import com.phonemate.R;
import com.phonemate.model.FloatviewClickDbEntity;
import com.phonemate.model.panelmodel.PanelMenuEntity;
import com.rxx.fast.FastDB;
import com.rxx.fast.adapter.FastAdapter;
import com.rxx.fast.adapter.FastViewHolder;
import com.rxx.fast.view.ViewInject;

import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/26 17:13
 * 修改人：colorful
 * 修改时间：15/11/26 17:13
 * 修改备注：
 */
public class PanelMenuClickAdapter extends FastAdapter<PanelMenuEntity> {
    private int selectPosition = -1;
    private FastDB mDb;
    private int type;
    public PanelMenuClickAdapter(List<PanelMenuEntity> mList, Context mContext,int type) {
        super(mList, mContext, R.layout.item_panelmenu_click_view);
        this.type=type;
        mDb=((PMApplication)mContext.getApplicationContext()).fastDB;
    }

    @Override
    public void bingHolder(FastViewHolder fastViewHolder, int position, ViewGroup parent) {
        ViewHolder holder = (ViewHolder) fastViewHolder;
        if (position == selectPosition) {
            holder.mCBMenuStatus.setChecked(true);
        } else {
            holder.mCBMenuStatus.setChecked(false);
        }
        holder.mCBMenuStatus.setTag(position);
        holder.mTvMenuName.setText(mList.get(position).menuName);
    }

    public void setSelect(int position) {
        mDb.delete(FloatviewClickDbEntity.class,"type=?",new String[]{type+""});
        if (selectPosition == position) {
            selectPosition = -1;
        } else {
            selectPosition = position;
            PanelMenuEntity entity=mList.get(position);
            FloatviewClickDbEntity dbEntity=new FloatviewClickDbEntity();
            dbEntity.setClassName(entity.mClassName);
            dbEntity.setAppName(entity.menuName);
            dbEntity.setPackageName(entity.mPackName);
            dbEntity.setType(type);
            mDb.insert(dbEntity);
        }
        PanelMenuClickAdapter.this.notifyDataSetChanged();
    }

    @Override
    public FastViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends FastViewHolder implements View.OnClickListener {

        @ViewInject(id = R.id.mLayou)
        public LinearLayout mLayou;

        @ViewInject(id = R.id.mTvMenuName)
        public TextView mTvMenuName;

        @ViewInject(id = R.id.mCBMenuStatus, click = true)
        public CheckBox mCBMenuStatus;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            setSelect(position);
        }
    }
}
