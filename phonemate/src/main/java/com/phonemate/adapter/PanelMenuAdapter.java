package com.phonemate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.controlpannel.PanelControl;
import com.phonemate.model.panelmodel.PanelMenuEntity;
import com.phonemate.utils.SettingUtils;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.adapter.FastAdapter;
import com.rxx.fast.adapter.FastViewHolder;
import com.rxx.fast.view.ViewInject;

import java.util.List;

/**
 * User: RanCQ
 * Date: 2015/8/17
 * QQ  :392663986
 * TEL : 15310880724
 */
public class PanelMenuAdapter extends FastAdapter<PanelMenuEntity> {
    private int convertViewSize=-1;
    public PanelMenuAdapter(List<PanelMenuEntity> mList, Context mContext) {
        super(mList, mContext, R.layout.item_panelmenu_view);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        mList.get(position).updateView();
        if (mList.get(position).mRootView == null) {
            convertView = View.inflate(mContext, mViewId, null);
            viewHolder = (ViewHolder) onCreateViewHolder(convertView);
                convertViewSize = (int) ((WindowUtils.getWIndowWidth(mContext) * PanelControl.mSizeScore * SettingUtils.getPanelSize(mContext)
                        - parent.getPaddingBottom() * 2)
                        /3);
            GridView.LayoutParams layoutParams = new GridView.LayoutParams(convertViewSize, convertViewSize);//设置ItemView的大小
            convertView.setLayoutParams(layoutParams);
            viewHolder.mIvIcon.setImageDrawable(mList.get(position).menuIcon);
            viewHolder.mTvName.setText(mList.get(position).menuName);
        } else {
            convertView= mList.get(position).mRootView;
        }
        return convertView;
    }

    @Override
    public void bingHolder(FastViewHolder fastViewHolder, int position,ViewGroup parent) {

    }

    @Override
    public FastViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }
    class ViewHolder extends FastViewHolder{

        @ViewInject(id = R.id.mLayoutBg)
        private LinearLayout mLayoutBg;

        @ViewInject(id = R.id.mIvIcon)
        private ImageView mIvIcon;

        @ViewInject(id = R.id.mTvName)
        private TextView mTvName;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
