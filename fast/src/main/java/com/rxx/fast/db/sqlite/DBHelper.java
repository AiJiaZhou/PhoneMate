package com.rxx.fast.db.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * User: RanCQ
 * Date: 2015/9/16
 */
public class DBHelper extends SQLiteOpenHelper{
    DBOperateListener dbOperateListener;
    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(DBConfig dbConfig,DBOperateListener dbOperateListener){
        this(dbConfig.context.getApplicationContext(), dbConfig.dbName, null, dbConfig.dbVersion);
        this.dbOperateListener= dbOperateListener;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(dbOperateListener!=null){
            dbOperateListener.create(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(dbOperateListener!=null){
            dbOperateListener.update(db);
        }
    }

    /**定义数据库创建和更新事件*/
    public interface DBOperateListener {
        void create(SQLiteDatabase db);
        void update(SQLiteDatabase db);
    }

}
