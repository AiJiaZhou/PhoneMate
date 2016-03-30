package com.phonemate.model.panelmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;

import com.phonemate.adapter.PanelMenuAdapter;

/**
 * 项目名称：PhoneMate
 * 类描述：自定义控制面板菜单
 * 创建人：colorful
 * 创建时间：15/11/19 11:46
 * 修改人：colorful
 * 修改时间：15/11/19 11:46
 * 修改备注：
 */
public abstract  class PanelMenuEntity {

    /**第三方应用*/
    public static final int OTHER_APP=1;
    /**小工具*/
    public static final int TOOLS=2;
    /**插入语句*/
    public  static final String  INSERT_SQL="INSERT INTO com_phonemate_model_ToolsDBModel(type,drawableIcon,className,name) VALUES (2,?,?,?)";

    /**持有的 context*/
    public Context context;

    /**控制面板显示菜单个数*/
    public static final int CORE_MENU_NUM=9;

    /** 第三方跳转Service和Activity的Action*/
    public int menuAction;
    /**名称*/
    public String menuName;
    /**图标*/
    public Drawable menuIcon;
    /**显示的位置*/
    public int menuPosition;
    /**点击事件*/
    public abstract void onClick(AdapterView<?> parent, View view, int position, long id);
    /**初始化*/
    public abstract void init();

    /**全类名*/
    public String mClassName;

    /**所属包名*/
    public String mPackName;

    /**是否需要关闭控制面板*/
    public boolean isNeedCloseCoreDialog;

    public View mRootView;

    public PanelMenuEntity(Context context) {
        this.context = context;
        mClassName=this.getClass().getName();
        initData();
    }

    public void initData(){
        init();
    }

    public void updateView(){

    }
    public PanelMenuAdapter getDirectorAdapter(){
        return null;
    }

    /**插入第一次默认选择小工具数据*/
    public static void insertToolsDbModel(SQLiteDatabase db){
        Object [] insertValuew={
                AppReturnEntity.INSERT_VALUES,
                BlueToothEntity.INSERT_VALUES,
                CameraEntity.INSERT_VALUES,
                ClearMemoryEntity.INSERT_VALUES,
                DataTrafficEntity.INSERT_VALUES,
                FlashLightEntity.INSERT_VALUES,
                GoControlEntity.INSERT_VALUES,
                GoLuncherEntity.INSERT_VALUES,
                LockScreenEntity.INSERT_VALUES,
                NotificationBarEntity.INSERT_VALUES,
                RecentApps.INSERT_VALUES,
                RingerEntity.INSERT_VALUES,
                SystemSettingEntity.INSERT_VALUES,
                VibrateEntity.INSERT_VALUES,
                WifiMenuEntity.INSERT_VALUES,
                ScreenCapture.INSERT_VALUES,
        };
        db.beginTransaction();
        int i=0;
        for(Object  objects: insertValuew){
            db.execSQL(PanelMenuEntity.INSERT_SQL, (Object[]) objects);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
