package com.phonemate.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.model.HomeMenuEntity;
import com.phonemate.utils.ResUtils;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/16 15:03
 * 修改人：colorful
 * 修改时间：15/11/16 15:03
 * 修改备注：
 */
public class HomeMenuAdapter extends BaseAdapter{


    @Override
    public int getCount() {
        return HomeMenuEntity.mMenuNum;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView=View.inflate(parent.getContext(), R.layout.item_homemenu_view,null);
        AbsListView.LayoutParams layoutParams=new AbsListView.LayoutParams(parent.getWidth()/3,parent.getHeight()/2);
        convertView.setLayoutParams(layoutParams);
        TextView tvMenu= (TextView) convertView.findViewById(R.id.mTvMenu);
        ImageView ivMenu= (ImageView) convertView.findViewById(R.id.mIvMenu);
        tvMenu.setText(HomeMenuEntity.mMenuName[position]);
        ivMenu.setImageDrawable(ResUtils.getDrawable(parent.getContext(),HomeMenuEntity.mMenuIcon[position]));
        return convertView;
    }
}
