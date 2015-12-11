package com.rxx.fast.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.IOException;

/**
 * User: RanCQ
 * Date: 2015/9/16
 */
public class DBUtils {

    /**
     * 在SD卡的指定目录上创建文件
     * @param sdcardPath 创建路径
     * @param dbfilename 数据库名称
     * @return
     */
    public static SQLiteDatabase createDbFileOnSDCard(String sdcardPath,String dbfilename) {
        File file = new File(sdcardPath, dbfilename);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    return SQLiteDatabase.openOrCreateDatabase(file, null);
                }
            } catch (IOException exception) {
                throw new RuntimeException("数据库文件创建失败",exception);
            }
        } else {
            return SQLiteDatabase.openOrCreateDatabase(file, null);
        }
        return null;
    }


    /**检查表是否存在*/
    public static boolean  isTableExist(SQLiteDatabase db,String tableName){
         String sql="SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name='"
              + tableName +"' ";
        LUtils.i("tableIsExist:",sql);
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
                cursor = null;
            }
        }
        return false;
    }
}
