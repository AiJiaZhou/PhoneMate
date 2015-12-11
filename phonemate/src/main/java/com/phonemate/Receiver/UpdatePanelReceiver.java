package com.phonemate.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/17 22:22
 * 修改人：colorful
 * 修改时间：15/11/17 22:22
 * 修改备注：
 */
public class UpdatePanelReceiver extends BroadcastReceiver{

    private UpdatePanelViewListener mListener;

    public UpdatePanelReceiver(UpdatePanelViewListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(mListener!=null){
            mListener.updatePanelView();
        }

    }
    public interface  UpdatePanelViewListener{
        public void updatePanelView();
    }
}
