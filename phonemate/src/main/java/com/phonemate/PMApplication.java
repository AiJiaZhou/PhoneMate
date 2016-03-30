package com.phonemate;

import android.app.Application;

import com.phonemate.model.CoreMenuSelectedDbModel;
import com.phonemate.model.ToolsDBModel;
import com.phonemate.model.panelmodel.PanelMenuEntity;
import com.phonemate.utils.MessageUtils;
import com.phonemate.utils.ScreenManager;
import com.phonemate.utils.SettingUtils;
import com.rxx.fast.FastDB;
import com.rxx.fast.utils.LUtils;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/11 19:13
 * 修改人：colorful
 * 修改时间：15/11/11 19:13
 * 修改备注：
 */
public class PMApplication extends Application{
    public FastDB fastDB;
    public ScreenManager screenManager;
    @Override
    public void onCreate() {
        super.onCreate();
        fastDB =FastDB.create(this, null);

        LUtils.isDebug=true;
        MessageUtils.init(this);
        //第一次执行执行插入语句
        if(SettingUtils.isFirstUse(this)) {

            fastDB.createTable(ToolsDBModel.class);
            //插入小工具数据
            PanelMenuEntity.insertToolsDbModel(fastDB.getSQLiteDatabase());

            //插入默认选择的数据
            fastDB.createTable(CoreMenuSelectedDbModel.class);
            CoreMenuSelectedDbModel.insertToolsDbModel(fastDB.getSQLiteDatabase());

            SettingUtils.setFirstUse(this, false);

        }

        screenManager=ScreenManager.getScreenManager();
    }
}
