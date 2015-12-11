package com.rxx.fast;

import android.app.Application;
import android.os.Environment;
import android.test.ApplicationTestCase;

import com.rxx.fast.model.SqlTestDemo;
import com.rxx.fast.utils.LUtils;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testCreateSqlite() {
        FastDB fastDB = FastDB.create(mContext, "fast.txt", Environment.getExternalStorageDirectory() + "/",null);
        List<SqlTestDemo> list = fastDB.queryByWhere(SqlTestDemo.class,"id>?",new String[]{"1000"});
       if(list!=null) {
           for (SqlTestDemo testDemo : list) {
               LUtils.e(testDemo.toString());
           }
       }
    }
}