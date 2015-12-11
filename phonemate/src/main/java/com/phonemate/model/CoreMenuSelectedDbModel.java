package com.phonemate.model;

import android.database.sqlite.SQLiteDatabase;

import com.phonemate.model.panelmodel.AppReturnEntity;
import com.phonemate.model.panelmodel.ClearMemoryEntity;
import com.phonemate.model.panelmodel.FlashLightEntity;
import com.phonemate.model.panelmodel.GoControlEntity;
import com.phonemate.model.panelmodel.GoLuncherEntity;
import com.phonemate.model.panelmodel.NotificationBarEntity;
import com.phonemate.model.panelmodel.RecentApps;
import com.phonemate.model.panelmodel.SystemSettingEntity;
import com.rxx.fast.db.table.Property;
import com.rxx.fast.utils.LUtils;

/**
 * 需要显示在悬浮面板对应存放在数据库的实体类
 * User: RanCQ
 * Date: 2015/9/22
 *
 */
public class CoreMenuSelectedDbModel {

    /**插入语句*/
    /**
     *  private int id;
     private String packageName;
     private String className;
     private String appName;
     private int position;
     private int type;
     */
    @Property(isSave = false)
    public  static final String  INSERT_SQL="INSERT INTO com_phonemate_model_CoreMenuSelectedDbModel(packageName,className,appName,position,type) VALUES (\"com.phonemate.model.panelmodel\",?,?,?,\"2\")";

    private int id;
    private String packageName;
    private String className;
    private String appName;
    private int position;
    private int type;

    @Override
    public String toString() {
        return "CoreMenuSelectedDbModel{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", appName='" + appName + '\'' +
                ", position=" + position +
                ", type=" + type +
                '}';
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**插入工具数据*/
    public static void insertToolsDbModel(SQLiteDatabase db){
        String [] [] insertValuew={
                {AppReturnEntity.CLASS_NAME,AppReturnEntity.NAME,"0"},
                {FlashLightEntity.CLASS_NAME,FlashLightEntity.NAME,"1"},
                {GoLuncherEntity.CLASS_NAME,GoLuncherEntity.NAME,"2"},
                {RecentApps.CLASS_NAME,RecentApps.NAME,"3"},
                {GoControlEntity.CLASS_NAME,GoControlEntity.NAME,"4"},
                {AppReturnEntity.CLASS_NAME,AppReturnEntity.NAME,"5"},
                {ClearMemoryEntity.CLASS_NAME,ClearMemoryEntity.NAME,"6"},
                {NotificationBarEntity.CLASS_NAME,NotificationBarEntity.NAME,"7"},
                {SystemSettingEntity.CLASS_NAME,SystemSettingEntity.NAME,"8"},
        };
        //packageName,className,appName,position,
        db.beginTransaction();
        int i=0;
        for(Object  objects: insertValuew){
            LUtils.i("执行插入", i++ + "");
            db.execSQL(INSERT_SQL, (Object[]) objects);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
