package com.phonemate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.model.panelmodel.PanelMenuEntity;
import com.rxx.fast.adapter.FastAdapter;
import com.rxx.fast.adapter.FastViewHolder;
import com.rxx.fast.view.ViewInject;

import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/25 16:04
 * 修改人：colorful
 * 修改时间：15/11/25 16:04
 * 修改备注：
 */
public class PanelMenuSelectAdapter extends FastAdapter<PanelMenuEntity>{
    public PanelMenuSelectAdapter(List<PanelMenuEntity> mList, Context mContext) {
        super(mList, mContext, R.layout.item_select_core_menu_view);
    }

    @Override
    public void bingHolder(FastViewHolder fastViewHolder, int position, ViewGroup parent) {
        ViewHolder holder=(ViewHolder) fastViewHolder;
        holder.mIvMenuIcon.setImageDrawable(mList.get(position).menuIcon);
        holder.mTvMenuName.setText(mList.get(position).menuName);
    }

    @Override
    public FastViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends FastViewHolder{

        @ViewInject(id = R.id.mIvMenuIcon)
        public ImageView mIvMenuIcon;

        @ViewInject(id = R.id.mTvMenuName)
        public TextView mTvMenuName;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
