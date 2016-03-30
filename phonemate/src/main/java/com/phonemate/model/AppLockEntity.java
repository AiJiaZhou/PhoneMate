package com.phonemate.model;

import android.graphics.drawable.Drawable;

import com.rxx.fast.db.table.Property;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/30 20:26
 * 修改人：colorful
 * 修改时间：15/11/30 20:26
 * 修改备注：
 */
public class AppLockEntity {
    private int id;
    private boolean isLock;
    private String packName;
    private String className;
    private String appName;
    @Property(isSave = false)
    private String appType;
    @Property(isSave = false)
    private Drawable appDrawable;

    public boolean getIsLock() {
        return isLock;
    }

    public void setIsLock(boolean isLock) {
        this.isLock = isLock;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
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

    public Drawable getAppDrawable() {
        return appDrawable;
    }

    public void setAppDrawable(Drawable appDrawable) {
        this.appDrawable = appDrawable;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppLockEntity that = (AppLockEntity) o;

        return !(packName != null ? !packName.equals(that.packName) : that.packName != null);


    }
}
