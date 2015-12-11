package com.rxx.fast.adapter;

import android.view.View;

import com.rxx.fast.view.ViewUtils;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * 项目名称：newzeroshop
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/10 14:50
 * 修改人：colorful
 * 修改时间：15/11/10 14:50
 * 修改备注：
 */
public class BaseRecyclerViewHolder extends ViewHolder{

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        ViewUtils.initViews(this, itemView);
    }
}
