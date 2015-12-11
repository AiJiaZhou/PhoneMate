package com.rxx.fast.utils;

import android.database.Cursor;

import com.rxx.fast.db.table.ColumnPropertie;
import com.rxx.fast.db.table.TableInfo;

/**
 * User: RanCQ
 * Date: 2015/9/20
 */
public class CursorUtils {
    /**
     * 构建实体类
     *
     * @param cursor
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T>T getEntity(Cursor cursor,Class<T> clazz){
        try {
            if (cursor != null) {
                TableInfo tableInfo = TableInfo.getTableInfo(clazz);
                int columnSiz = cursor.getColumnCount();
                if (columnSiz > 0) {
                    T t = clazz.newInstance();
                    for (int i = 0; i < columnSiz; i++) {//遍历所以的column名和值
                        String columnName = cursor.getColumnName(i);
                        ColumnPropertie columnPropertie = tableInfo.propertyMap.get(columnName);
                        if (columnPropertie == null) {//主键属性
                            columnPropertie = tableInfo.getId();
                        }
                        columnPropertie.setValue(t,cursor.getString(i));
                    }
                    return t;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
