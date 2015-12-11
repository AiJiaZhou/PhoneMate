package com.phonemate.model;

import com.rxx.fast.db.table.Property;

/**
 * 项目名称：PhoneMate
 * 类描述：长按和短按实体类
 * 创建人：colorful
 * 创建时间：15/11/29 14:10
 * 修改人：colorful
 * 修改时间：15/11/29 14:10
 * 修改备注：
 */
public class FloatviewClickDbEntity {
    @Property(isSave = false)
    public static final int LONG_CLICK =-1;
    @Property(isSave = false)
    public static final int DOUBLE_CLICK =1;
    private int id;
    private String packageName;
    private String className;
    private String appName;

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    @Override
    public String toString() {
        return "FloatviewClickDbEntity{" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", appName='" + appName + '\'' +
                ", type=" + type +
                '}';
    }
}
