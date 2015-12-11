package com.rxx.fast.db.sqlite;

import android.text.TextUtils;

import com.rxx.fast.db.table.ColumnPropertie;
import com.rxx.fast.db.table.TableInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 构建SqlInfo类
 * User: RanCQ
 * Date: 2015/9/16
 */
public class SqlInfoBuilder {

    /**根据实体类构建插入sql语句*/
    public final static SqlInfo buildInsertSqlInfo(Object object){
        if(object==null){
            throw  new RuntimeException("insert error,because the save model is null");
        }
        TableInfo tableInfo=TableInfo.getTableInfo(object.getClass());
        SqlInfo sqlInfo=new SqlInfo();
        StringBuffer insertStr=new StringBuffer();//sql语句
        StringBuffer standStr=new StringBuffer();//问号占位符
        insertStr.append("INSERT INTO ").append(tableInfo.getTableName()).append(" (");
        standStr.append("(");
        if(tableInfo.getId().getDataType()==String.class) {//判断主键是否自增
            insertStr.append(tableInfo.getId().getColumnName()).append(",");
            standStr.append("?,");
            sqlInfo.addValue(tableInfo.getId().getValue(object));
        }
        Collection<ColumnPropertie> collection=tableInfo.propertyMap.values();
        for(ColumnPropertie columnPropertie:collection){
            insertStr.append(columnPropertie.getColumnName()).append(",");
            standStr.append("?,");
            sqlInfo.addValue(columnPropertie.getValue(object));
        }
        standStr.deleteCharAt(standStr.length() - 1);
        insertStr.deleteCharAt(insertStr.length() - 1);
        insertStr.append(") VALUES ").append(standStr.toString()).append(")");
        sqlInfo.setSql(insertStr.toString());
        return sqlInfo;
    }

    /**
     * 构建Updatesql
     * @param object
     * @return
     */
    public final static SqlInfo buildUpdateSqlInfo(Object object){
        SqlInfo sqlInfo=new SqlInfo();
        if(object==null){
            throw new RuntimeException("update error,the ["+object.getClass()+"]'s is null ");
        }
        TableInfo tableInfo=TableInfo.getTableInfo(object.getClass());
        Object idValue=tableInfo.getId().getValue(object);
        if(idValue==null){
            throw new RuntimeException("update error,the ["+object.getClass()+"]'s id value null ");
        }
        String strWhere=tableInfo.getId().getColumnName()+"=?";
        Object []  whereValues={idValue};

        StringBuffer strUpdate=new StringBuffer();
        Object []  updateValues=new Object[tableInfo.propertyMap.size()];
        int i=0;
        for(Map.Entry<String,ColumnPropertie> entry:tableInfo.propertyMap.entrySet()){
            strUpdate.append(entry.getValue().getColumnName())
                     .append("=?,");
            updateValues[i++]=(entry.getValue().getValue(object));
        }
        strUpdate.deleteCharAt(strUpdate.length() - 1);
        return buildUpdateSqlInfo(tableInfo.getTableName(),strUpdate.toString(),updateValues,strWhere,whereValues);
    }

    /**
     * 构建Updatesql 语句
     * @param clazz
     * @param strUpdate 待更新字段 eg:name=?,age=?
     * @param updateValues {"name",12}
     * @param strWhere 条件 id=? AND sex=?
     * @param whereValues {1,"男"}
     * @return
     */
    public static SqlInfo buildUpdateSqlInfo(Class clazz,String strUpdate,Object [] updateValues,
                                             String strWhere,Object [] whereValues) {
        if(clazz==null){
            throw new RuntimeException("update error,the ["+clazz+"] is null ");
        }
        TableInfo tableInfo=TableInfo.getTableInfo(clazz);
        return buildUpdateSqlInfo(tableInfo.getTableName(),strUpdate,updateValues,strWhere,whereValues);
    }

    /**
     * 构建更新sql语句
     * @param clazz
     * @param updateColumn
     * @param updateStrValues
     * @param whereColumn
     * @param whereValues
     * @return
     */
    public static SqlInfo buildUpdateSqlInfo(Class clazz,String [] updateColumn,Object [] updateStrValues,
                                             String [] whereColumn,Object [] whereValues) {
        if(clazz==null){
            throw new RuntimeException("update error,the ["+clazz+"] is null ");
        }

        if(updateColumn==null||updateColumn.length==0){
            throw new RuntimeException("update error,the updateColumn is null");
        }

        if(updateStrValues==null || updateStrValues.length==0){
            throw new RuntimeException("update error,the update column value is null");
        }

        StringBuffer strUpdate=new StringBuffer();
        for(String str:updateColumn){
            strUpdate.append(str).append("=?,");
        }

        StringBuffer strWhere=new StringBuffer();
        for(String str:whereColumn){
            strWhere.append(str).append("=? AND ");
        }
        strWhere.delete((strWhere.length()-4),(strWhere.length()-1));
        strUpdate.deleteCharAt(strUpdate.length()-1);
        TableInfo tableInfo=TableInfo.getTableInfo(clazz);
        return buildUpdateSqlInfo(tableInfo.getTableName(),strUpdate.toString(),updateStrValues,strWhere.toString(),whereValues);
    }


    /**
     * 构建Updatesql 语句
     * @param tableName
     * @param strUpdate 待更新字段 eg:name=?,age=?
     * @param updateValues {"name",12}
     * @param strWhere 条件 id=? AND sex=?
     * @param whereValues {1,"男"}
     * @return
     */
    public static SqlInfo buildUpdateSqlInfo(String tableName,String strUpdate,Object [] updateValues,
                                             String strWhere,Object [] whereValues) {
        if(TextUtils.isEmpty(tableName)){
            throw new RuntimeException("update error,the ["+tableName+"] is null ");
        }

        if(TextUtils.isEmpty(strUpdate)){
                throw  new RuntimeException("update error,the need update field is null");
        }

        SqlInfo sqlInfo=new SqlInfo();
        StringBuffer updateStringBuffer=new StringBuffer();
        updateStringBuffer.append("UPDATE ").append(tableName)
                          .append(" SET ").append(strUpdate)
                          .append(" WHERE ").append(strWhere);
        sqlInfo.setSql(updateStringBuffer.toString());
        if(updateValues!=null){
            sqlInfo.addAllValue(Arrays.asList(updateValues));
        }

        if(whereValues!=null){
            sqlInfo.addAllValue(Arrays.asList(whereValues));
        }
        return sqlInfo;
    }

    /**
     * 构建删除sql语句，传入的对象的ID值不能为空
     * @param object
     * @return
     */
    public final static SqlInfo buildDeleteSqlInfo(Object object){
        if(object==null){
            throw  new  RuntimeException("delete error,the delete model is null");
        }
        TableInfo tableInfo=TableInfo.getTableInfo(object.getClass());
        Object idvalue = tableInfo.getId().getValue(object);
        if(idvalue == null){
            throw  new  RuntimeException("delete error,the model get id is null");
        }
        StringBuffer deleteStr=new StringBuffer();
        deleteStr.append("DELETE FROM ")
                 .append(tableInfo.getTableName())
                 .append(" WHERE ")
                 .append(tableInfo.getId().getColumnName())
                 .append("=?");
        SqlInfo sqlInfo=new SqlInfo();
        sqlInfo.setSql(deleteStr.toString());
        sqlInfo.addValue(idvalue);
        return sqlInfo;
    }

    /**
     * 构建删除sql语句
     * @param clazz
     * @return
     */
    public final static SqlInfo buildDeleteSqlInfo(Class clazz,Object id){
        if(clazz==null){
            throw  new RuntimeException("delete error,the clazz is null");
        }
        if(id==null){
            throw  new  RuntimeException("delete error,id is null");
        }
        TableInfo tableInfo=TableInfo.getTableInfo(clazz);
        StringBuffer deleteStr=new StringBuffer();
        deleteStr.append("DELETE FROM ")
                .append(tableInfo.getTableName())
                .append(" WHERE ")
                .append(tableInfo.getId().getColumnName())
                .append("=?");
        SqlInfo sqlInfo=new SqlInfo();
        sqlInfo.setSql(deleteStr.toString());
        sqlInfo.addValue(id);
        return sqlInfo;
    }

    /**
     * 构建删除sql语句
     * @param clazz
     * @return
     */
    public final static SqlInfo buildDeleteSqlInfo(Class clazz,String strWhere,Object [] values){
        if(clazz==null){
            throw  new RuntimeException("delete error,the clazz is null");
        }
        TableInfo tableInfo=TableInfo.getTableInfo(clazz);
        StringBuffer deleteStr=new StringBuffer();
        deleteStr.append("DELETE FROM ")
                 .append(tableInfo.getTableName());
        if(!TextUtils.isEmpty(strWhere)){
            deleteStr .append(" WHERE ")
                      .append(strWhere);
        }
        SqlInfo sqlInfo=new SqlInfo();
        sqlInfo.setSql(deleteStr.toString());
        if(values!=null&&values.length>0){
            sqlInfo.addAllValue(Arrays.asList(values));
        }
        return sqlInfo;
    }

    /**根据clazz创建表*/
    public final static String buildCreateTableSqlInfo(Class<?> clazz){
        if(clazz==null){
            throw  new RuntimeException("get SqlInfo error,the clazz is null");
        }
        TableInfo tableInfo=TableInfo.getTableInfo(clazz);
        StringBuffer sqlBuffer=new StringBuffer();
        sqlBuffer.append("CREATE TABLE IF NOT EXISTS ")
                 .append(tableInfo.getTableName())
                 .append(" (");
        Class<?> primaryClazz = tableInfo.getId().getDataType();
        if(primaryClazz == int.class || primaryClazz==Integer.class
                || primaryClazz == long.class || primaryClazz == Long.class){
            sqlBuffer.append(tableInfo.getId().getColumnName()).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        }else{
            sqlBuffer.append(tableInfo.getId().getColumnName()).append(" TEXT PRIMARY KEY,");
        }

        Collection<ColumnPropertie> collection=tableInfo.propertyMap.values();
        for(ColumnPropertie property:collection){
            sqlBuffer.append(property.getColumnName());
            Class<?> dataType=property.getDataType();
            if(dataType == int.class || dataType==Integer.class//
                    || dataType == long.class || dataType == Long.class){
                sqlBuffer.append(" INTEGER");
            }else  if(dataType == float.class || dataType==Float.class
                    || dataType == double.class || dataType == Double.class){
                sqlBuffer.append(" REAL");
            }else  if(dataType == boolean.class || dataType==Boolean.class){
                sqlBuffer.append(" NUMERIC");
            }else  if(dataType == Date.class ){
                sqlBuffer.append(" TEXT");
            }else if(dataType == String.class){
                sqlBuffer.append(" TEXT");
            }
            sqlBuffer.append(",");
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        sqlBuffer.append(" )");
        return sqlBuffer.toString();
    }

    public static SqlInfo buildQuerySqlInfo(Class<?> clazz,String whereStr,String [] values){
        if(clazz==null){
            throw  new RuntimeException("query error,the clazz "+clazz+" is null");
        }
        //SELECT NAME,
        TableInfo tableInfo=TableInfo.getTableInfo(clazz);
        StringBuffer queryStr=new StringBuffer(getSelectSqlHeader(tableInfo));
        SqlInfo sqlInfo=new SqlInfo();
        if(whereStr!=null && !TextUtils.isEmpty(whereStr)){
            queryStr.append(" WHERE ").append(whereStr);
            sqlInfo.addAllValue(Arrays.asList(values));
        }
        sqlInfo.setSql(queryStr.toString());
        return sqlInfo;
    }

    public static SqlInfo buildQuerySqlInfo(Class<?> clazz,String whereStr,String [] values,String orderBy){
        if(clazz==null){
            throw  new RuntimeException("query error,the clazz "+clazz+" is null");
        }
        //SELECT NAME,
        TableInfo tableInfo=TableInfo.getTableInfo(clazz);
        StringBuffer queryStr=new StringBuffer(getSelectSqlHeader(tableInfo));
        SqlInfo sqlInfo=new SqlInfo();
        if(whereStr!=null&&!TextUtils.isEmpty(whereStr)){
            queryStr.append(" WHERE ").append(whereStr);
            sqlInfo.addAllValue(Arrays.asList(values));
        }
        queryStr.append(" ORDER BY "+orderBy);
        sqlInfo.setSql(queryStr.toString());
        return sqlInfo;
    }

    public static SqlInfo buildQuerySqlInfo(Class<?> clazz, Object id) {
        if(clazz==null){
            throw  new RuntimeException("query error,the clazz "+clazz+" is null");
        }
        TableInfo tableInfo=TableInfo.getTableInfo(clazz);
        StringBuffer queryStr=new StringBuffer(getSelectSqlHeader(tableInfo));
        SqlInfo sqlInfo=new SqlInfo();
        if(id!=null&&!TextUtils.isEmpty(id.toString())){
            queryStr.append(" WHERE ")
                    .append(tableInfo.getId().getColumnName())
                    .append("=?");
            sqlInfo.addValue(id);
        }
        sqlInfo.setSql(queryStr.toString());
        return sqlInfo;
    }

    public static String getSelectSqlHeader(TableInfo tableInfo){
        StringBuffer queryStr=new StringBuffer();
        queryStr.append("SELECT ");
        queryStr.append(tableInfo.getId().getColumnName()).append(",");
        for(Map.Entry<String,ColumnPropertie> entry:tableInfo.propertyMap.entrySet()){
            queryStr.append(entry.getValue().getColumnName()).append(",");
        }
        queryStr.deleteCharAt(queryStr.length() - 1);
        queryStr.append(" FROM ")
                .append(tableInfo.getTableName());

        return queryStr.toString();
    }
}
