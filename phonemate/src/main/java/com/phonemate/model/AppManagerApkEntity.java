package com.phonemate.model;

import android.graphics.drawable.Drawable;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/2 16:17
 * 修改人：colorful
 * 修改时间：15/12/2 16:17
 * 修改备注：
 */
public class AppManagerApkEntity {
    public Drawable appDrawable;
    public String appName;
    public String appSize;
    public String appVersion;
    public String appPackage;
    public String appClass;
    public String appPath;
    public boolean isChoose=false;

    @Override
    public String toString() {
        return "AppManagerApkEntity{" +
                "appDrawable=" + appDrawable +
                ", appName='" + appName + '\'' +
                ", appSize='" + appSize + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appPackage='" + appPackage + '\'' +
                ", appClass='" + appClass + '\'' +
                '}';
    }
}
