package com.phonemate.model.panelmodel;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.controlpannel.PanelControl;
import com.phonemate.utils.MemoryUtils;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.SettingUtils;
import com.phonemate.utils.WindowUtils;
import com.phonemate.widget.CircleProgressView;
import com.rxx.fast.utils.LUtils;


/**
 * User: RanCQ
 * Date: 2015/8/17
 * QQ  :392663986
 * TEL : 15310880724
 */
public class ClearMemoryEntity extends PanelMenuEntity implements CircleProgressView.onAttachedToWindowListener {

    public static final String NAME="内存清理";
    public static final String CLASS_NAME="com.phonemate.model.panelmodel.ClearMemoryEntity";
    public static final int ICON_ON= R.mipmap.icon_panelmenu_memory_clear;
    public static final Object [] INSERT_VALUES={ICON_ON,CLASS_NAME,NAME };

    private CircleProgressView circleProgressView;
    private TextView mTvPercent;
    private TextView mTvName;
    private int totalMemery;
    private int availMemory;
    private int mSize;
    private LinearLayout mLayoutBg;
    private ControlMemoryProgressHandler controlMemoryProgressHandler;

    public ClearMemoryEntity(Context context) {
        super(context);
    }

    @Override
    public void onClick(AdapterView<?> parent, View view, int position, long id) {
        new ControlMemoryProgressThred().start();
        MemoryUtils.clear(context);
    }

    @Override
    public void init() {
        menuIcon= ResUtils.getDrawable(context,ICON_ON);
        menuName=NAME;
        isNeedCloseCoreDialog=false;
        controlMemoryProgressHandler=new ControlMemoryProgressHandler();

        mRootView = View.inflate(context, R.layout.item_clearmemory_view, null);
        circleProgressView = (CircleProgressView) mRootView.findViewById(R.id.circleprogress);
        mTvPercent = (TextView) mRootView.findViewById(R.id.mTvPercent);
        mTvName = (TextView) mRootView.findViewById(R.id.mTvName);
        mLayoutBg=(LinearLayout)mRootView.findViewById(R.id.mLayoutBg);
        mSize =  (int) ((WindowUtils.getWIndowWidth(context) * PanelControl.mSizeScore * SettingUtils.getPanelSize(context)) /3);
        LUtils.i("clear"+mSize);
        GridView.LayoutParams layoutParams = new GridView.LayoutParams(mSize, mSize);//设置ItemView的大小
        mRootView.setLayoutParams(layoutParams);
        circleProgressView.setOnWindowFocusChangedLintener(this);
        mTvName.setText(NAME);
        update();
    }

    public void update() {
        totalMemery = (int) MemoryUtils.getTotalMemory(context);
        availMemory = (int) MemoryUtils.getAvailMemory(context);
        circleProgressView.setProgressTotal(totalMemery);
        circleProgressView.setProgress(availMemory);
        mTvPercent.setText((int) ((totalMemery-availMemory) * 100f / totalMemery)+"%" );
    }

    public void update(int availMemory) {
        circleProgressView.setProgress(availMemory);
        mTvPercent.setText((int) ((totalMemery-availMemory) * 100f / totalMemery) +"%");
    }

    @Override
    public void onWindowFocusChanged() {
        LUtils.i(this.getClass().getSimpleName(), "onWindowFocusChanged");
        update();
    }

    /**线程来改变进度条数据 */
    class ControlMemoryProgressThred extends Thread{
        @Override
        public void run() {
            super.run();
            int availMemory=(int) MemoryUtils.getAvailMemory(context);
            int totalMemery=(int) MemoryUtils.getTotalMemory(context);
            while(availMemory<totalMemery){
                availMemory+=(0.01*totalMemery);
                controlMemoryProgressHandler.sendEmptyMessage(availMemory);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            controlMemoryProgressHandler.sendEmptyMessage(-1);
        }
    }

    public void updateView(){
        mSize =  (int) ((WindowUtils.getWIndowWidth(context) * PanelControl.mSizeScore * SettingUtils.getPanelSize(context)) /3);
        GridView.LayoutParams layoutParams = new GridView.LayoutParams(mSize, mSize);//设置ItemView的大小
        mRootView.setLayoutParams(layoutParams);
    }

    /**控制进度条handler*/
    class ControlMemoryProgressHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==-1){
                update();
            }else{
                update(msg.what);
            }
        }
    }
}
