package com.phonemate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.phonemate.R;
import com.phonemate.model.FloatViewSettingSelectColorEntity;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.adapter.BaseRecyclerView;
import com.rxx.fast.adapter.BaseRecyclerViewHolder;
import com.rxx.fast.view.ViewInject;

import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/17 20:40
 * 修改人：colorful
 * 修改时间：15/11/17 20:40
 * 修改备注：
 */
public class FloatViewSettingColorAdapter
        extends BaseRecyclerView<FloatViewSettingSelectColorEntity,FloatViewSettingColorAdapter.ViewHolder>{

    public FloatViewSettingColorAdapter(List<FloatViewSettingSelectColorEntity> mList, Context mContext) {
        super(mList, mContext, R.layout.item_floatview_setting_select_color_view);
    }

    @Override
    public ViewHolder createViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public void baseOnBingViewHolder(ViewHolder holder, int position,
                                     FloatViewSettingSelectColorEntity floatViewSettingSelectColorEntity) {
        holder.mView.setBackgroundColor(floatViewSettingSelectColorEntity.mColors[position]);
        if(floatViewSettingSelectColorEntity.isSelect){
            holder.mViewSelect.setVisibility(View.VISIBLE);
        }else{
            holder.mViewSelect.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends BaseRecyclerViewHolder{

        @ViewInject(id = R.id.mView)
        public View mView;
        @ViewInject(id = R.id.mViewSelect)
        public View mViewSelect;
        @ViewInject(id = R.id.mLayoutFrameLayout)
        public FrameLayout mLayoutFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(
                    (WindowUtils.getWIndowWidth(itemView.getContext())/FloatViewSettingSelectColorEntity.mMaxNum)
            ,ViewGroup.LayoutParams.MATCH_PARENT);
            mLayoutFrameLayout.setLayoutParams(layoutParams);
        }
    }
}
