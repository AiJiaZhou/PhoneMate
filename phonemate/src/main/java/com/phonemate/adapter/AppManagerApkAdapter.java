package com.phonemate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.model.AppManagerApkEntity;
import com.rxx.fast.adapter.FastAdapter;
import com.rxx.fast.adapter.FastViewHolder;
import com.rxx.fast.view.ViewInject;

import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/3 12:24
 * 修改人：colorful
 * 修改时间：15/12/3 12:24
 * 修改备注：
 */
public class AppManagerApkAdapter extends FastAdapter<AppManagerApkEntity> {


    public AppManagerApkAdapter(List<AppManagerApkEntity> mList, Context mContext) {
        super(mList, mContext, R.layout.item_app_manager_apk_view);
    }

    @Override
    public void bingHolder(FastViewHolder fastViewHolder, int position, ViewGroup parent) {
        ViewHolder holder = (ViewHolder) fastViewHolder;
        holder.mCBStatus.setChecked(mList.get(position).isChoose);
        holder.mCBStatus.setTag(position);
        holder.mTvName.setText(mList.get(position).appName);
        holder.mTvSize.setText("大小:"+mList.get(position).appSize+"M");
        holder.mTvVersion.setText("V:"+mList.get(position).appVersion);
        holder.mIvIcon.setImageDrawable(mList.get(position).appDrawable);
    }

    @Override
    public FastViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends FastViewHolder implements View.OnClickListener{

        @ViewInject(id = R.id.mTvName)
        private TextView mTvName;

        @ViewInject(id = R.id.mIvIcon)
        private ImageView mIvIcon;

        @ViewInject(id = R.id.mTvVersion)
        private TextView mTvVersion;

        @ViewInject(id = R.id.mTvSize)
        private TextView mTvSize;

        @ViewInject(id = R.id.mCBStatus,click = true)
        private CheckBox mCBStatus;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            mList.get(position).isChoose=!mList.get(position).isChoose;
        }

    }

}
