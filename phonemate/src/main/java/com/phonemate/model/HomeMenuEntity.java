package com.phonemate.model;

import com.phonemate.R;
import com.phonemate.activity.SettingActivity;
import com.phonemate.activity.AppLockSettingActivity;
import com.phonemate.activity.FloatViewSettingActivity;
import com.phonemate.activity.PanelSettingActivity;
import com.phonemate.activity.AppManagerActivity;
import com.phonemate.activity.WeatherDetailActivity;

/**
 * 项目名称：PhoneMate
 * 类描述：首页菜单
 * 创建人：colorful
 * 创建时间：15/11/16 14:57
 * 修改人：colorful
 * 修改时间：15/11/16 14:57
 * 修改备注：
 */
public class HomeMenuEntity {
    public static final int mMenuNum=6;
    public static final String [] mMenuName={"天气详情","悬浮菜单","应用锁",
            "面板管理","软件管理","关于"};
    public static final int [] mMenuIcon={R.mipmap.icon_homemenu_weather,R.mipmap.icon_homemenu_floatview,
            R.mipmap.icon_homemenu_applock, R.mipmap.icon_homemenu_floatmenu,
            R.mipmap.icon_homemenu_app,R.mipmap.icon_homemenu_about};
    public static final Class [] mMenuAction={
            WeatherDetailActivity.class,FloatViewSettingActivity.class, AppLockSettingActivity.class,
            PanelSettingActivity.class, AppManagerActivity.class,SettingActivity.class};
}
