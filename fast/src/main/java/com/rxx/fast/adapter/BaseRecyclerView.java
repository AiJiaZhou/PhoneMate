package com.rxx.fast.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名称：newzeroshop
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/10 14:45
 * 修改人：colorful
 * 修改时间：15/11/10 14:45
 * 修改备注：
 */
public abstract class BaseRecyclerView<T, V extends BaseRecyclerViewHolder> extends RecyclerView.Adapter<V> implements View.OnClickListener, View.OnLongClickListener {
    protected List<T> mList;
    protected Context mContext;
    protected OnItemClickListener<T> mClickListener;
    protected OnItemLongClickListener<T> mLongClickListener;
    protected int viewId;
    protected RecyclerView mRecyclerView = null;

    public static final int HEADER_VIEW=-1;
    public static final int FOOTER_VIEW=1;
    public static final int CONTENT_VIEW=0;

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mRecyclerView == null) {
            mRecyclerView = (RecyclerView) parent;
        }
        View itemView = LayoutInflater.from(mContext).inflate(viewId, parent, false);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        return createViewHolder(itemView);
    }

    public abstract V createViewHolder(View v);

    public abstract void baseOnBingViewHolder(V holder, int position, T t);

    public BaseRecyclerView(List<T> mList, Context mContext, int viewId) {
        this.mContext = mContext;
        this.mList = mList;
        this.viewId = viewId;
    }

    public void setOnItemClickListener(OnItemClickListener<T> mClickListener) {
        this.mClickListener = mClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> mLongClickListener) {
        this.mLongClickListener = mLongClickListener;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        holder.itemView.setTag(position);
        baseOnBingViewHolder(holder, position, mList.get(position));
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getParent() == mRecyclerView) {
            if (mClickListener != null) {
                int position = (int) v.getTag();
                RecyclerView parent = (RecyclerView) v.getParent();
                mClickListener.onItemClick(parent, v, position, mList.get(position));
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mLongClickListener != null) {
            int position = (int) v.getTag();
            RecyclerView parent = (RecyclerView) v.getParent();
            return mLongClickListener.onItemLongClick(parent, v, position, mList.get(position));
        }
        return false;
    }

    public interface OnItemClickListener<T> {
        public void onItemClick(RecyclerView recyclerView, View view, int position, T t);
    }

    public interface OnItemLongClickListener<T> {
        public boolean onItemLongClick(RecyclerView recyclerView, View view, int position, T t);
    }
}
