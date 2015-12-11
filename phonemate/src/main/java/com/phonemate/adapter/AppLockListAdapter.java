package com.phonemate.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.model.AppLockEntity;
import com.phonemate.utils.ResUtils;
import com.rxx.fast.adapter.FastAdapter;
import com.rxx.fast.adapter.FastViewHolder;
import com.rxx.fast.view.ViewInject;

import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/30 20:23
 * 修改人：colorful
 * 修改时间：15/11/30 20:23
 * 修改备注：
 */
public class AppLockListAdapter extends FastAdapter<AppLockEntity>{

    private Drawable drawableOff;
    private Drawable drawableOn;
    public AppLockListAdapter(List<AppLockEntity> mList, Context mContext) {
        super(mList, mContext, R.layout.item_app_lock_applist_view);
        drawableOff= ResUtils.getDrawable(mContext,R.mipmap.ic_applock_off);
        drawableOn= ResUtils.getDrawable(mContext,R.mipmap.ic_applock_on);
    }

    @Override
    public void bingHolder(FastViewHolder fastViewHolder, int position, ViewGroup parent) {
        ViewHolder holder= (ViewHolder) fastViewHolder;
        holder.mIvIcon.setImageDrawable(mList.get(position).getAppDrawable());
        holder.mTvName.setText(mList.get(position).getAppName());
        holder.mTvType.setText(mList.get(position).getAppType());
        if(mList.get(position).getIsLock()){
            holder.mIvStatus.setImageDrawable(drawableOn);
        }else{
            holder.mIvStatus.setImageDrawable(drawableOff);
        }
    }

    @Override
    public FastViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends FastViewHolder{

        @ViewInject(id = R.id.mIvIcon)
        public ImageView mIvIcon;

        @ViewInject(id = R.id.mTvName)
        public TextView mTvName;

        @ViewInject(id = R.id.mTvType)
        public TextView mTvType;

        @ViewInject(id = R.id.mIvStatus)
        public ImageView mIvStatus;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
