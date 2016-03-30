package com.phonemate.utils;

import android.content.Context;

import com.phonemate.activity.applock.AppLock;
import com.phonemate.global.GlobalUtils;

/**
 * 
 * 设置类
 * 
 * @author Ran
 *
 * 时间: 2015年7月8日
 */
public class SettingUtils {

	/**获取实时天气是否成功*/
	private static final String SETTING_IS_GET_LIVE_WEATHER="IS_GET_WEATHER_OK";
	public static boolean isGetLiveWeather(Context context){
		return  PreferenceUtils.getInstance(context).getBoolean(SETTING_IS_GET_LIVE_WEATHER,true);
	}
	public static void  setisGetLiveWeather(Context context,boolean  value){
		PreferenceUtils.getInstance(context).saveBoolean(SETTING_IS_GET_LIVE_WEATHER,value);
	}

	/**是否打开悬浮助手*/
	private static final String SETTING_IS_SHOW_FLOAT_VIEW="IS_SHOW_FLOAT_VIEW";
	public static boolean isOpenFloatView(Context context){
		return  PreferenceUtils.getInstance(context).getBoolean(SETTING_IS_SHOW_FLOAT_VIEW,true);
	}
	public static void  setIsOpenFloatView(Context context,boolean  value){
		PreferenceUtils.getInstance(context).saveBoolean(SETTING_IS_SHOW_FLOAT_VIEW,value);
	}

	private  final static String APP_KEY_FIRST_USE="app_key_first_use";//是否是该版本第一次使用
	public static boolean appIsFirstUse(Context context){
		return PreferenceUtils.getInstance(context).getBoolean(APP_KEY_FIRST_USE, true);
	}
	public static  void setAppIsFirstUse(Context context,boolean value){
		PreferenceUtils.getInstance(context).saveBoolean(APP_KEY_FIRST_USE, value);
	}

	//是否是该版本第一次使用
	private final static String KEY_FIRST_USE="key_first_use_";
	public static boolean isFirstUse(Context context){
		return PreferenceUtils.getInstance(context).getBoolean(KEY_FIRST_USE+CommonUtils.getVersonCode(context), true);
	}
	public static  void setFirstUse(Context context,boolean value){
		PreferenceUtils.getInstance(context).saveBoolean(KEY_FIRST_USE+CommonUtils.getVersonCode(context), value);
	}


	//是否是该版本第一次使用
	private final static String KEY_SHOW_README_DIALOG="key_show_read_me_dialog";
	public static boolean isSHowReadMeDialog(Context context){
		return PreferenceUtils.getInstance(context).getBoolean(KEY_SHOW_README_DIALOG+CommonUtils.getVersonCode(context), true);
	}
	public static  void setIsSHowReadMeDialog(Context context,boolean value){
		PreferenceUtils.getInstance(context).saveBoolean(KEY_SHOW_README_DIALOG+CommonUtils.getVersonCode(context), value);
	}

	//QQqun
	public final static String KEY_QQ="key_qq";
	public static String getQQ(Context context){
		return PreferenceUtils.getInstance(context).getString(KEY_QQ, "");
	}
	public static void setQQ(Context context,String value){
		PreferenceUtils.getInstance(context).saveString(KEY_QQ, value);
	}
	//QQ群key

	public final static String KEY_QQ_KEY="key_qq_key";
	public static String getQQKEY(Context context){
		return PreferenceUtils.getInstance(context).getString(KEY_QQ_KEY, "");
	}
	public static void setQQKEY(Context context,String value){
		PreferenceUtils.getInstance(context).saveString(KEY_QQ_KEY, value);
	}

	//悬浮窗颜色
	public final static String KEY_FLOATVIEW_COLOR="key_floatview_color";
	public static int getFLoatViewColor(Context context){
		return PreferenceUtils.getInstance(context).getInt(KEY_FLOATVIEW_COLOR, 5);
	}
	public static void setFLoatViewColor(Context context,int value){
		PreferenceUtils.getInstance(context).saveInt(KEY_FLOATVIEW_COLOR, value);
	}
	//悬浮窗透明度
	public final static String KEY_FLOATVIEW_TRANS="key_floatview_trans";
	public static float getFLoatViewTrans(Context context){
		return PreferenceUtils.getInstance(context).getfloat(KEY_FLOATVIEW_TRANS, 0);
	}
	public static void setFLoatViewTrans(Context context,float value){
		PreferenceUtils.getInstance(context).savefloat(KEY_FLOATVIEW_TRANS, value);
	}
	//悬浮窗尺寸
	public final static String KEY_FLOATVIEW_SIZE="key_floatview_size";
	public static float getFLoatViewSize(Context context){
		return PreferenceUtils.getInstance(context).getfloat(KEY_FLOATVIEW_SIZE, 1);
	}
	public static void setFLoatViewSize(Context context,float value){
		PreferenceUtils.getInstance(context).savefloat(KEY_FLOATVIEW_SIZE, value);
	}
	//悬浮窗圆角
	public final static String KEY_FLOATVIEW_RADIUS="key_floatview_radius";
	public static float getFLoatViewRadius(Context context){
		return PreferenceUtils.getInstance(context).getfloat(KEY_FLOATVIEW_RADIUS, 1);
	}
	public static void setFLoatViewRadius(Context context,float value){
		PreferenceUtils.getInstance(context).savefloat(KEY_FLOATVIEW_RADIUS, value);
	}

	//控制面板透明度
	public final static String KEY_PANEL_TRANS="key_panel_trans";
	public static float getPanelTrans(Context context){
		return PreferenceUtils.getInstance(context).getfloat(KEY_PANEL_TRANS, 0);
	}
	public static void setPanelTrans(Context context,float value){
		PreferenceUtils.getInstance(context).savefloat(KEY_PANEL_TRANS, value);
	}
	//控制面板窗尺寸
	public final static String KEY_PANEL_SIZE="key_panel_size";
	public static float getPanelSize(Context context){
		return PreferenceUtils.getInstance(context).getfloat(KEY_PANEL_SIZE, 1.25f);
	}
	public static void setPanelSize(Context context,float value){
		PreferenceUtils.getInstance(context).savefloat(KEY_PANEL_SIZE, value);
	}
	//控制面板窗圆角
	public final static String KEY_PANEL_RADIUS="key_panel_radius";
	public static float getPanelRadius(Context context){
		return PreferenceUtils.getInstance(context).getfloat(KEY_PANEL_RADIUS, 1);
	}
	public static void setPanelRadius(Context context,float value){
		PreferenceUtils.getInstance(context).savefloat(KEY_PANEL_RADIUS, value);
	}
	//控制面板窗颜色
	public final static String KEY_PANNEL_COLOR="key_panel_color";
	public static int getPanelColor(Context context){
		return PreferenceUtils.getInstance(context).getInt(KEY_PANNEL_COLOR, 5);
	}
	public static void setPanelColor(Context context,int value){
		PreferenceUtils.getInstance(context).saveInt(KEY_PANNEL_COLOR, value);
	}

	//应用锁相关
	//是否开启应用锁
	public final static String KEY_IS_OPEN_APP_LOCK="key_is_open_app_lock";
	public static boolean isOpenAppLock(Context context){
		return PreferenceUtils.getInstance(context).getBoolean(KEY_IS_OPEN_APP_LOCK, false);
	}
	public static void setIsOpenAppLock(Context context,boolean value){
		PreferenceUtils.getInstance(context).saveBoolean(KEY_IS_OPEN_APP_LOCK, value);
	}

	//是否设置了应用锁密码
	public final static String KEY_IS_SETTING_APP_LOCK_PASSWORD="key_is_setting_app_lock_password";
	public static boolean isSettingAppLockPassword(Context context){
		return PreferenceUtils.getInstance(context).getBoolean(KEY_IS_SETTING_APP_LOCK_PASSWORD, false);
	}
	public static void SetIsSettingAppLockPassword(Context context,boolean value){
		PreferenceUtils.getInstance(context).saveBoolean(KEY_IS_SETTING_APP_LOCK_PASSWORD, value);
	}

	//应用锁密码
	public final static String KEY_APP_LOCK_PASSWORD="key_app_lock_password";
	public static String getAppLockPassword(Context context){
		return PreferenceUtils.getInstance(context).getString(KEY_APP_LOCK_PASSWORD, null);
	}
	public static void setAppLockPassword(Context context,String value){
		SetIsSettingAppLockPassword(context,true);
		PreferenceUtils.getInstance(context).saveString(KEY_APP_LOCK_PASSWORD, value);
	}

	//应用锁类型
	public final static String KEY_APP_LOCK_TYPE="key_app_lock_password";
	public static int getAppLockType(Context context){
		return PreferenceUtils.getInstance(context).getInt(KEY_APP_LOCK_TYPE, AppLock.APP_LOCK_TYPE_NUM);
	}
	public static void setAppLockType(Context context,int value){
		SetIsSettingAppLockPassword(context,true);
		PreferenceUtils.getInstance(context).saveInt(KEY_APP_LOCK_TYPE, value);
	}


	//是否开启了应用锁界面
	public final static String KEY_OPEN_APP_LOCK_ACTIVITY="key_is_open_lock_activity";
	public static boolean isOpenAppLockActiviy(Context context){
		return PreferenceUtils.getInstance(context).getBoolean(KEY_OPEN_APP_LOCK_ACTIVITY, false);
	}
	public static synchronized  void setIsOpenAppLockActiviy(Context context,boolean value){
		PreferenceUtils.getInstance(context).saveBoolean(KEY_OPEN_APP_LOCK_ACTIVITY, value);
	}

	//上次更新天气时间
	public final static String  KEY_GET_WEATHER_TIME="key_get_weather_tine";
	public static long getWeatherTime(Context context){
		return PreferenceUtils.getInstance(context).getLong(KEY_GET_WEATHER_TIME, 0l);
	}
	public static synchronized  void setWeatherTime(Context context,long value){
		PreferenceUtils.getInstance(context).saveLong(KEY_GET_WEATHER_TIME, value);
	}


	//start===红包设置界面===
	//是否自动抢红包
	public final static String  KEY_HB_ISOPEN="key_hb_isopen";
	public static boolean HBIsOpen(Context context){
		return PreferenceUtils.getInstance(context).getBoolean(KEY_HB_ISOPEN, false);
	}
	public static synchronized  void setHbIspen(Context context,boolean value){
		PreferenceUtils.getInstance(context).saveBoolean(KEY_HB_ISOPEN, value);
	}

	//后台抢红包
	public final static String  KEY_HB_BACKGROUND="key_hb_background";
	public static boolean isHBBg(Context context){
		return PreferenceUtils.getInstance(context).getBoolean(KEY_HB_BACKGROUND, false);
	}
	public static synchronized  void setisHBBg(Context context,boolean value){
		PreferenceUtils.getInstance(context).saveBoolean(KEY_HB_BACKGROUND, value);
	}


	//自动回复
	public final static String  KEY_HB_AO_AS="key_hb_ao_as";
	public static boolean isAutoAnswer(Context context){
		return PreferenceUtils.getInstance(context).getBoolean(KEY_HB_AO_AS, false);
	}
	public static synchronized  void setisAutoAnswer(Context context,boolean value){
		PreferenceUtils.getInstance(context).saveBoolean(KEY_HB_AO_AS, value);
	}

	//自动回复类型
	public final static String  KEY_HB_AO_AS_TYPE="key_hb_ao_as_type";
	public static int isAutoAnswerType(Context context){
		return PreferenceUtils.getInstance(context).getInt(KEY_HB_AO_AS_TYPE, GlobalUtils.AUTO_ANSWER_TYPE_MONEY);
	}
	public static synchronized  void setisAutoAnswerType(Context context,int value){
		PreferenceUtils.getInstance(context).saveInt(KEY_HB_AO_AS_TYPE, value);
	}

	//自动回复DIY内容
	public final static String  KEY_HB_AO_AS_CONNTENT="key_hb_ao_as_content";
	public static String getAutoAnswerContent(Context context){
		return PreferenceUtils.getInstance(context).getString(KEY_HB_AO_AS_CONNTENT, GlobalUtils.AUTO_ANSWER_TYPE_DIY_CONTENT);
	}
	public static synchronized  void setAutoAnswerContent(Context context,String value){
		PreferenceUtils.getInstance(context).saveString(KEY_HB_AO_AS_CONNTENT, value);
	}
	//=======end =======

}
