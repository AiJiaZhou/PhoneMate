package com.rxx.fast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rxx.fast.db.sqlite.DBConfig;
import com.rxx.fast.db.sqlite.DBHelper;
import com.rxx.fast.db.sqlite.SqlInfo;
import com.rxx.fast.db.sqlite.SqlInfoBuilder;
import com.rxx.fast.db.table.ColumnPropertie;
import com.rxx.fast.db.table.TableInfo;
import com.rxx.fast.utils.CursorUtils;
import com.rxx.fast.utils.DBUtils;
import com.rxx.fast.utils.LUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: RanCQ
 * Date: 2015/9/16
 */
public class FastDB {

    private  final String TAG="FastDB";

    /**存放不同数据库连接,使用数据库dnName作为键*/
    private  static Map<String,FastDB> fastDBMap=new HashMap<String,FastDB>();

    /**数据库操作类*/
    private SQLiteDatabase db;
    private DBConfig dbConfig;


    public SQLiteDatabase getSQLiteDatabase(){
        return db;
    }

    /**构造*/
    private  FastDB(DBConfig dbConfig){
        this.dbConfig=dbConfig;
        if(dbConfig==null){
            throw  new RuntimeException("DBConfig is null");
        }
        if(dbConfig.context ==null){
            throw  new RuntimeException("Context is null");
        }
        if(dbConfig.targetDirectory==null||dbConfig.targetDirectory.trim().length()<=0){//创建在非sd卡
            this.db=new DBHelper(dbConfig,dbConfig.dbOperateListener).getWritableDatabase();
        }else{//创建在sd卡
            this.db= DBUtils.createDbFileOnSDCard(dbConfig.targetDirectory,dbConfig.dbName);
        }
    }



    /**初始化*/
    public static FastDB getInstance(DBConfig dbConfig){
        FastDB fastDB = fastDBMap.get(dbConfig.dbName);
        if (fastDB == null) {
            fastDB = new FastDB(dbConfig);
            fastDBMap.put(dbConfig.dbName, fastDB);
        }
        return fastDB;
    }

    /**传入context，其余使用默认*/
    public static  FastDB create(Context context,DBHelper.DBOperateListener dbOperateListener){
        DBConfig dbConfig=new DBConfig(context,dbOperateListener);
        return create(dbConfig);
    }
    /**传入context，指定数据库名称，其余使用默认*/
    public static FastDB create(Context context,String dbName,DBHelper.DBOperateListener dbOperateListener){
        DBConfig dbConfig=new DBConfig(context,dbName,dbOperateListener);
        return create(dbConfig);
    }

    /**传入context，指定数据库名称，指定版本号,其余使用默认*/
    public static FastDB create(Context context,String dbName,int dbVersion,DBHelper.DBOperateListener dbOperateListener){
        DBConfig dbConfig=new DBConfig(context,dbName,dbVersion,dbOperateListener);
        return create(dbConfig);
    }

    /**传入context，指定数据库名称，指定版本号,其余使用默认*/
    public static FastDB create(Context context,String dbName,String targetDirectory,DBHelper.DBOperateListener dbOperateListener){
        DBConfig dbConfig=new DBConfig(context,dbName,targetDirectory,dbOperateListener);
        return create(dbConfig);
    }
    /**传入context，指定数据库名称，指定版本号,创建在sd卡上,其余使用默认*/
    public static FastDB  create(Context context,String dbName,int dbVersion,String targetDirectory,DBHelper.DBOperateListener dbOperateListener){
        DBConfig dbConfig=new DBConfig(context,dbName,dbVersion,targetDirectory,dbOperateListener);
        return create(dbConfig);
    }
    /**指定配置创建数据库*/
    public static FastDB create(DBConfig dbConfig){
        return getInstance(dbConfig);
    }

    /**执行sql语句,可以替代insert,delete,update语句*/
    public void execSQLInfo(SqlInfo sqlInfo){
        LUtils.e("fastdb:",sqlInfo.toString());
        db.execSQL(sqlInfo.getSql(), sqlInfo.getBindArgsAsArray());
    }


    /**执行sql语句,可以替代insert,delete,update语句
     * @param sqlInfo*/
    public Cursor querrySQLInfo(SqlInfo sqlInfo){
        LUtils.e("querrySQLInfoL", sqlInfo.toString());
      return  db.rawQuery(sqlInfo.getSql(), sqlInfo.getBindArgsAsStringArray());
    }




    //====================start 数据库表的增删改查操作=======================

    /**插入,使用db.execSQL代替insert*/
    public void insert(Object object){
        createTable(object.getClass());
        execSQLInfo(SqlInfoBuilder.buildInsertSqlInfo(object));
    }

    /**插入数据
     * @param object
     * @return  the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert2(Object object){
        if(object==null){
            throw  new RuntimeException("insert error,because the save model is null");
        }
        createTable(object.getClass());
        TableInfo tableInfo=TableInfo.getTableInfo(object.getClass());
        ContentValues contentValues=new ContentValues();
        if(tableInfo.getId().getDataType()==String.class) {//判断主键是否自增
            contentValues.put(tableInfo.getId().getColumnName(), tableInfo.getId().getValue(object).toString());
        }
        for(Map.Entry<String, ColumnPropertie> entry:tableInfo.propertyMap.entrySet()){
            contentValues.put(entry.getValue().getColumnName(), entry.getValue().getValue(object).toString());
        }
        return db.insert(tableInfo.getTableName(), "NULL", contentValues);
    }
    /**
     * 删除指定的表
     *
     * @param clazz
     */
    public void dropTable(Class<?> clazz) {
        createTable(clazz);
        TableInfo table = TableInfo.getTableInfo(clazz);
        dropTable(table.getClassName());
    }

    /**删除指定表
     *
     * @param tableName
     */
    public void dropTable(String tableName){
        db.execSQL("DROP TABLE " + tableName);
    }

    /**删除所有表*/
    public void dropAllTable(){
        Cursor cursor=db.rawQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name != 'sqlite_sequence'", null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                dropTable(cursor.getString(0));
            }
        }
        if(cursor!= null) {
            cursor.close();
            cursor=null;
        }
    }

    /**
     * 删除,使用db.execSQL代替delete
     *
     * @param object,主键不能为空
     */
    public void delete(Object object){
        createTable(object.getClass());
        execSQLInfo(SqlInfoBuilder.buildDeleteSqlInfo(object));
    }

    /**
     * 根据ID删除数据
     *
     * @param clazz
     * @param id
     */
    public void delete(Class clazz,Object id){
        createTable(clazz);
        execSQLInfo(SqlInfoBuilder.buildDeleteSqlInfo(clazz, id));
    }

    /**
     *
     * @param clazz
     * @param whereClause id=? or name=?
     *                    条件为空的时候 将会删除所有的数据
     * @param values {id,name}
     */
    public void delete(Class clazz, String whereClause, Object [] values){
        createTable(clazz);
        execSQLInfo(SqlInfoBuilder.buildDeleteSqlInfo(clazz, whereClause, values));
    }
    public void deleteAll(Class clazz){
        createTable(clazz);
        execSQLInfo(SqlInfoBuilder.buildDeleteSqlInfo(clazz, null, null));
    }

    /**修改,使用db.execSQL代替update*/
    public void update(Object object){
        createTable(object.getClass());
        execSQLInfo(SqlInfoBuilder.buildUpdateSqlInfo(object));
    }

    /**修改,使用db.execSQL代替update*/
    public void update(Class clazz,String updateStr,Object [] updateStrValues,String strWhere,Object [] whereValues){
        createTable(clazz);
        execSQLInfo(SqlInfoBuilder.buildUpdateSqlInfo(clazz, updateStr, updateStrValues, strWhere, whereValues));
    }

    /**
     * 跟新
     * @param clazz
     * @param updateColumn {name,ge}
     * @param updateStrValues {"name",23}
     * @param whereColumn {id,sex}
     * @param whereValues {2001,男} 使用AND链接
     */
    public void update(Class clazz,String [] updateColumn,Object [] updateStrValues,String [] whereColumn,Object [] whereValues){
        createTable(clazz);
        execSQLInfo(SqlInfoBuilder.buildUpdateSqlInfo(clazz, updateColumn, updateStrValues, whereColumn, whereValues));
    }


    /**id查询*/
    public <T>T queryById(Class<T> clazz,Object id) {
        createTable(clazz);
        Cursor cursor = querrySQLInfo(SqlInfoBuilder.buildQuerySqlInfo(clazz, id));
        try {//为了关闭游标使用try
            if (cursor != null && cursor.moveToNext()) {
                return CursorUtils.getEntity(cursor, clazz);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
            cursor = null;
        }
        return null;
    }


    /**
     * 查询所有数据
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T>  queryAll(Class<T> clazz){
        createTable(clazz);
        List<T> list = null;
        Cursor cursor = querrySQLInfo(SqlInfoBuilder.buildQuerySqlInfo(clazz, null));
        if (cursor != null) {
            list = new ArrayList();
            while (cursor.moveToNext()) {
                list.add(CursorUtils.getEntity(cursor, clazz));
            }
        }
        cursor.close();
        cursor = null;
        return list;
    }

    /**
     * 条件查询
     *
     * @param clazz
     * @param whereStr sex=? or name=?
     * @param values {18,"name"}
     * @return
     */
    public <T> List<T> queryByWhere(Class<T> clazz,String whereStr,String [] values){
        createTable(clazz);
        List<T> list=new ArrayList<>();
        Cursor cursor=querrySQLInfo(SqlInfoBuilder.buildQuerySqlInfo(clazz,whereStr,values));
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(CursorUtils.getEntity(cursor, clazz));
            }
        }
        cursor.close();
        cursor = null;
        return list;
    }

    /**
     * 条件查询
     *
     * @param clazz
     * @param whereStr sex=? or name=?
     * @param values {18,"name"}
     * @param orderBy 排序 ID desc
     * @return
     */
    public <T> List<T> queryByWhere(Class<T> clazz,String whereStr,String [] values,String orderBy){
        createTable(clazz);
        List<T> list=new ArrayList<>();
        Cursor cursor=querrySQLInfo(SqlInfoBuilder.buildQuerySqlInfo(clazz,whereStr,values,orderBy));
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(CursorUtils.getEntity(cursor, clazz));
            }
        }
        cursor.close();
        cursor = null;
        return list;
    }

    public Cursor querryBySql(String sql,String [] values){
       return db.rawQuery(sql,values);
    }

    //====================end 数据库表的增删改查操作=======================

    /**根据sql语句创建表*/
    public void createTable(Class<?> clazz){
        if(!DBUtils.isTableExist(db,TableInfo.getTableInfo(clazz).getTableName())){//不存在就创建表
            db.execSQL(SqlInfoBuilder.buildCreateTableSqlInfo(clazz));
        }
    }

}
