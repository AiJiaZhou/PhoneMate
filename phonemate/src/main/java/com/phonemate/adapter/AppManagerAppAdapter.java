package com.phonemate.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.model.AppManagerAppEntity;
import com.phonemate.utils.ResUtils;
import com.rxx.fast.adapter.FastAdapter;
import com.rxx.fast.adapter.FastViewHolder;
import com.rxx.fast.view.ViewInject;

import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/2 16:25
 * 修改人：colorful
 * 修改时间：15/12/2 16:25
 * 修改备注：
 */
public class AppManagerAppAdapter extends FastAdapter<AppManagerAppEntity>{

    private Drawable drawableOn;
    private Drawable drawableOff;
    public AppManagerAppAdapter(List<AppManagerAppEntity> mList, Context mContext) {
        super(mList, mContext, R.layout.item_app_manager_app_view);
        drawableOn= ResUtils.getDrawable(mContext,R.mipmap.icon_app_uninstall_on);
        drawableOff= ResUtils.getDrawable(mContext,R.mipmap.icon_app_uninstall_off);
    }

    @Override
    public void bingHolder(FastViewHolder fastViewHolder, int position, ViewGroup parent) {
        ViewHolder holder= (ViewHolder) fastViewHolder;

        holder.mLayoutOprea.setTag(position);
        holder.mTvName.setText(mList.get(position).appName);
        holder.mTvDetail.setText(mList.get(position).appUpdateTime);
        holder.mIvIcon.setImageDrawable(mList.get(position).appDrawable);

        if(mList.get(position).appType==mList.get(position).TYPE_SYSTEM){
            holder.mIvOprea.setImageDrawable(drawableOff);
            holder.mTvOprea.setTextColor(ResUtils.getColor(mContext,R.color.color_text_title));
            holder.mTvType.setText("系统应用");
        }else{
            holder.mTvOprea.setTextColor(ResUtils.getColor(mContext,R.color.color_green));
            holder.mIvOprea.setImageDrawable(drawableOn);
            holder.mTvType.setText("第三方应用");
        }
    }

    @Override
    public FastViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends FastViewHolder implements View.OnClickListener{
        @ViewInject(id = R.id.mIvIcon)
        public ImageView mIvIcon;

        @ViewInject(id = R.id.mTvName)
        public TextView mTvName;

        @ViewInject(id = R.id.mTvDetail)
        public TextView mTvDetail;

        @ViewInject(id = R.id.mLayoutOprea,click = true)
        public LinearLayout mLayoutOprea;



        @ViewInject(id = R.id.mIvOprea)
        public ImageView mIvOprea;

        @ViewInject(id = R.id.mTvOprea)
        public TextView mTvOprea;

        @ViewInject(id = R.id.mTvType)
        public TextView mTvType;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void onClick(View v) {
            int position= (int) v.getTag();
            if(mList.get(position).appType==AppManagerAppEntity.TYPE_SYSTEM){
                return ;
            }else {
                Uri uri = Uri.parse("package:" + mList.get(position).appPackage);
                Intent deleteIntent =new Intent(Intent.ACTION_DELETE, uri);// = new Intent();
                deleteIntent.setType(Intent.ACTION_DELETE);

                deleteIntent.setData(uri);
                mContext.startActivity(deleteIntent);
            }
        }
    }
}
