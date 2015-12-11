package com.rxx.fast.db.sqlite;

import android.content.Context;

/**
 * User: RanCQ
 * Date: 2015/9/16
 */
public class DBConfig {
    /**上下文*/
    public Context context = null;
    /**数据库名字*/
    public String dbName = "rxx.db";
    /**数据库版本*/
    public int dbVersion = 1;
    /**数据库文件在sd卡中的目录，为空表示存放在data应用目录下*/
    public String targetDirectory=null;

    /**数据库操作事件*/
    public DBHelper.DBOperateListener dbOperateListener;

    public DBConfig(Context mContext,DBHelper.DBOperateListener dbOperateListener) {
        this.context = mContext;
        this.dbOperateListener=dbOperateListener;
    }
    public DBConfig(Context mContext, String mDbName,DBHelper.DBOperateListener dbOperateListener) {
        this.context = mContext;
        this.dbName = mDbName;
        this.dbOperateListener=dbOperateListener;
    }
    public DBConfig(Context mContext, String mDbName, int dbVersion,DBHelper.DBOperateListener dbOperateListener) {
        this.context = mContext;
        this.dbName = mDbName;
        this.dbVersion = dbVersion;
        this.dbOperateListener=dbOperateListener;
    }
    public DBConfig(Context mContext, String mDbName, String targetDirectory,DBHelper.DBOperateListener dbOperateListener) {
        this.context = mContext;
        this.dbName = mDbName;
        this.targetDirectory = targetDirectory;
        this.dbOperateListener=dbOperateListener;
    }
    public DBConfig(Context mContext, String mDbName, int dbVersion ,String targetDirectory,DBHelper.DBOperateListener dbOperateListener) {
        this.context = mContext;
        this.dbName = mDbName;
        this.dbVersion = dbVersion;
        this.targetDirectory = targetDirectory;
        this.dbOperateListener=dbOperateListener;
    }


}
