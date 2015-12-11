package com.rxx.fast.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 
 * @author Ran
 *
 * 时间: 2015年8月20日
 * @param <E>
 */
public abstract class FastAdapter<E> extends android.widget.BaseAdapter {
    protected List<E> mList;
    protected Context mContext;
    protected int mViewId;

    public FastAdapter(List<E> mList, Context mContext, int mViewId) {
        this.mList = mList;
        this.mContext = mContext;
        this.mViewId=  mViewId;
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public E getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FastViewHolder t = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, mViewId, null);
            t=onCreateViewHolder(convertView);
            convertView.setTag(t);
        } else {
            t = (FastViewHolder) convertView.getTag();
        }
        bingHolder(t,position,parent);
        return convertView;
    }

    /**实现数据和View绑定*/
    public abstract void bingHolder(FastViewHolder fastViewHolder, int position,ViewGroup parent);

    /**需要的时候创建ViewHolder*/
    public abstract FastViewHolder onCreateViewHolder(View view);

}
