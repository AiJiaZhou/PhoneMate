package com.phonemate.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.amap.api.location.AMapLocalDayWeatherForecast;
import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.LocationManagerProxy;
import com.phonemate.PMApplication;
import com.phonemate.R;
import com.phonemate.Receiver.AppLockReceiver;
import com.phonemate.Receiver.UpdateFloatViewReceiver;
import com.phonemate.Receiver.UpdatePanelReceiver;
import com.phonemate.activity.AppLockActivity;
import com.phonemate.controlpannel.FloatViewClickListener;
import com.phonemate.floatmenu.FloatView;
import com.phonemate.global.GlobalUtils;
import com.phonemate.model.AppLockEntity;
import com.phonemate.model.WeatherModel;
import com.phonemate.utils.AppTaskUtils;
import com.phonemate.utils.NotificationUtils;
import com.phonemate.utils.SettingUtils;
import com.rxx.fast.FastDB;
import com.rxx.fast.utils.LUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/17 16:46
 * 修改人：colorful
 * 修改时间：15/11/17 16:46
 * 修改备注：
 */
public class CoreService extends Service implements UpdateFloatViewReceiver.UpdateFLoatViewListener,
        UpdatePanelReceiver.UpdatePanelViewListener, AppLockReceiver.AppLockListener, AMapLocalWeatherListener {

    private final long interval = 1000 * 60 * 60 * 2;
    private final int ONGOING_NOTIFICATION = 04101124;

    private FloatView mFLoatView;

    private UpdateFloatViewReceiver mUpdateFLoatViewReceiver;
    private IntentFilter mUpdateFloatViewIntentFilter;

    private UpdatePanelReceiver mUpdatePanelViewReceiver;
    private IntentFilter mUpdadePanelViewIntentFilter;

    private FloatViewClickListener mClickListener;

    private APPTaskHandler mAppTaskHandler;

    private APPTaskTimerTask mTimerTask;

    private Timer mTimer;

    private ActivityManager mActivityManager;

    private List<AppLockEntity> mList;

    private String mUnlockPack;
    private FastDB mDb;

    private IntentFilter mAppLockmAppLockIntentFilter;
    private AppLockReceiver mAppLockmAppLockReceiver;
    private Service mService;

    private List<WeatherModel> mWeatherList;

    protected PMApplication mApplication;

    /**
     * 高德获取天气
     */
    private LocationManagerProxy locationManagerProxy;

    @Override
    public void onCreate() {
        super.onCreate();
        mService = this;
        mApplication= (PMApplication) this.getApplication();
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        mDb = ((PMApplication) getApplicationContext()).fastDB;
        mList = mDb.queryAll(AppLockEntity.class);

        mFLoatView = new FloatView(this);
        mClickListener = new FloatViewClickListener((PMApplication) this.getApplication());
        mFLoatView.setOnClickListener(mClickListener);

        mUpdateFLoatViewReceiver = new UpdateFloatViewReceiver(this);
        mUpdateFloatViewIntentFilter = new IntentFilter();
        mUpdateFloatViewIntentFilter.addAction(GlobalUtils.BROADCAST_RECEIVER_UPDATE_FLOAT_VIEW);
        registerReceiver(mUpdateFLoatViewReceiver, mUpdateFloatViewIntentFilter);

        mUpdatePanelViewReceiver = new UpdatePanelReceiver(this);
        mUpdadePanelViewIntentFilter = new IntentFilter();
        mUpdadePanelViewIntentFilter.addAction(GlobalUtils.BROADCAST_RECEIVER_UPDATE_PANEL_VIEW);
        registerReceiver(mUpdatePanelViewReceiver, mUpdadePanelViewIntentFilter);

        mAppLockmAppLockIntentFilter = new IntentFilter();
        mAppLockmAppLockIntentFilter.addAction(GlobalUtils.BROADCAST_RECEIVER_APP_LOCK_CHANGE);
        mAppLockmAppLockReceiver = new AppLockReceiver(this);
        registerReceiver(mAppLockmAppLockReceiver, mAppLockmAppLockIntentFilter);

        if (SettingUtils.isOpenAppLock(CoreService.this)) {
            startAppLockTimer();
        }
        //初始化高德获取天气
        locationManagerProxy = LocationManagerProxy.getInstance(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (SettingUtils.isOpenFloatView(this)) {
            showFLoatView();
        } else {
            dissmissFLoatView();
        }
        updateWeather();
        return super.onStartCommand(intent, START_STICKY, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUpdateFLoatViewReceiver);
        unregisterReceiver(mUpdatePanelViewReceiver);
        unregisterReceiver(mAppLockmAppLockReceiver);
    }

    public void showFLoatView() {
        if (mFLoatView.getStatus() == FloatView.STATUS_DISSMISS) {
            mFLoatView.showView();
        }

        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.phonemate", "com.phonemate.activity.HomeActivity");
        intent.setComponent(comp);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Notification notification = NotificationUtils.getNotification(this, 0l,
                intent, R.mipmap.icon_app, getString(R.string.coreservice_notification_title),
                getString(R.string.coreservice_notification_message),
                getString(R.string.coreservice_notification_message), 0,
                Notification.FLAG_INSISTENT);
        startForeground(ONGOING_NOTIFICATION, notification);
    }

    public void dissmissFLoatView() {
        if (mFLoatView.getStatus() == FloatView.STATUS_SHOWING) {
            mFLoatView.dismissView();
        }
        stopForeground(true);
    }


    public void startAppLockTimer() {
        endAppLockTimer();

        mAppTaskHandler = new APPTaskHandler();
        mTimerTask = new APPTaskTimerTask();
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 1000, 300);
    }

    public void endAppLockTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mAppTaskHandler != null) {
            mAppTaskHandler.removeMessages(0);
        }
    }

    /**
     * 实时天气
     */
    @Override
    public void onWeatherLiveSearched(AMapLocalWeatherLive aMapLocalWeatherLive) {
        if (aMapLocalWeatherLive != null && aMapLocalWeatherLive.getAMapException().getErrorCode() == 0) {
            SettingUtils.setisGetLiveWeather(mService, true);
            WeatherModel weatherModel = new WeatherModel();
            weatherModel.setCity(aMapLocalWeatherLive.getCity());//城市
            weatherModel.setWeather(aMapLocalWeatherLive.getWeather());//天气情况
            weatherModel.setWindDir(aMapLocalWeatherLive.getWindDir());//风向
            weatherModel.setWindPower(aMapLocalWeatherLive.getWindPower());//风力
            weatherModel.setHumidity(aMapLocalWeatherLive.getHumidity());//空气湿度
            weatherModel.setReportTime(aMapLocalWeatherLive.getReportTime());//数据发布时间
            weatherModel.setTemperature(aMapLocalWeatherLive.getTemperature());

            mWeatherList = mDb.queryAll(WeatherModel.class);
            if (mWeatherList == null || mWeatherList.size() == 0) {//还未存放数据
                mWeatherList = new ArrayList<>();
                mWeatherList.add(weatherModel);
            } else {//已经存放数据
                mWeatherList.remove(0);
                mWeatherList.add(0, weatherModel);
            }

//            mDb.deleteAll(WeatherModel.class);
//            mDb.insert(weatherModel);
//            LUtils.i("实时天气:", weatherModel.toString());
        } else {
            if (aMapLocalWeatherLive != null) {
                LUtils.i("获取天气数据失败:", aMapLocalWeatherLive.getAMapException().getErrorMessage());
                LUtils.i("获取天气数据失败:", aMapLocalWeatherLive.getAMapException().getErrorCode() + "");
            }
            SettingUtils.setisGetLiveWeather(mService, false);
        }
        locationManagerProxy.requestWeatherUpdates(LocationManagerProxy.WEATHER_TYPE_FORECAST, this);
    }

    /**
     * 未来三天天气预报
     */
    @Override
    public void onWeatherForecaseSearched(AMapLocalWeatherForecast aMapLocalWeatherForecast) {
        if (aMapLocalWeatherForecast != null && aMapLocalWeatherForecast.getWeatherForecast() != null) {
            int i = 0;
            for (AMapLocalDayWeatherForecast weather : aMapLocalWeatherForecast.getWeatherForecast()) {
                WeatherModel weatherModel = new WeatherModel();
                if (i == 0) {
                    if (mWeatherList == null || mWeatherList.size() == 0) {
                        mWeatherList = new ArrayList<>();
                    } else {
                        weatherModel = mWeatherList.get(0);
                    }
                }
                weatherModel.setWeek(weather.getWeek());

                weatherModel.setWeatherDay(weather.getDayWeather());
                weatherModel.setWeatherNight(weather.getNightWeather());

                weatherModel.setTemperatureMax(weather.getDayTemp());
                weatherModel.setTemperatureMin(weather.getNightTemp());

                weatherModel.setCity(weather.getCity());//城市

                weatherModel.setWindDirDay(weather.getDayWindDir());//风向
                weatherModel.setWindDirNight(weather.getNightWindDir());//风向

                weatherModel.setWindPowerDay(weather.getDayWindPower());//风力
                weatherModel.setWindPowerNight(weather.getNightWindPower());//风力

                if (mWeatherList.size() > i) {
                    mWeatherList.remove(i);
                    mWeatherList.add(i, weatherModel);
                } else {
                    mWeatherList.add(weatherModel);
                }
                i++;
            }
        }
        if (mWeatherList != null) {
            mDb.deleteAll(WeatherModel.class);
            for (WeatherModel weatherModel : mWeatherList) {
                mDb.insert(weatherModel);
            }
        }
        mFLoatView.updateWeather();
        SettingUtils.setWeatherTime(mService, System.currentTimeMillis());
    }

    /**
     * 更新天气
     */
    public void updateWeather() {
        LUtils.i("更新天气消息到达");
//        if (System.currentTimeMillis() - SettingUtils.getWeatherTime(mService) > interval) {
        locationManagerProxy.requestWeatherUpdates(LocationManagerProxy.WEATHER_TYPE_LIVE, this);
//        }
    }

    @Override
    public void updateFloatview() {
        mFLoatView.updateView();
    }

    @Override
    public void updatePanelView() {
        mClickListener.mPanelControl.updateView();
    }

    @Override
    public void appLockChangeListener(boolean value) {
        if (value) {
            mList = mDb.queryAll(AppLockEntity.class);
            startAppLockTimer();
        } else {
            endAppLockTimer();
        }
    }


    private final class APPTaskTimerTask extends TimerTask {
        @Override
        public void run() {
            mAppTaskHandler.sendEmptyMessage(0);
        }
    }

    private final class APPTaskHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mList != null && mList.size() > 0) {
                String packname = null;
                String className =null;
                if (android.os.Build.VERSION.SDK_INT < 21) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = mActivityManager.getRunningTasks(1).get(0);
                    if (runningTaskInfo != null && runningTaskInfo.numRunning > 0) {
                         packname = mActivityManager.getRunningTasks(1).get(0).topActivity
                                .getPackageName();
                         className = mActivityManager.getRunningTasks(1).get(0).topActivity
                                .getClassName();
                    }

                } else {
                    if (AppTaskUtils.isNoSwitch(mService)) {
                       UsageEvents.Event  event= AppTaskUtils.getEvent(mService);
                        if(event==null){
                            return ;
                        }
                         packname = event.getPackageName();
                         className = event.getClassName();

                    } else {
                        //打开设置
                        if (AppTaskUtils.isNoOption(mService)) {
                            AppTaskUtils.oepnSetting(mService);
                        }
                    }
                }
                if(TextUtils.isEmpty(packname) || TextUtils.isEmpty(className)){
                   return ;
                }

                if(packname.endsWith("launcher") && mApplication.screenManager.getSize()>0){
                    mApplication.screenManager.popAllActivity();
                }
                LUtils.i("当前栈顶"+packname);
                LUtils.i("当前栈顶"+className);
                //如果当前栈顶的是解锁界面不采取任何操作
                if (AppLockActivity.class.getName().equals(className)) {
                    return;
                }
                //当前栈顶应用没有改变
                if (packname.equals(mUnlockPack)) {
                    return;
                }
                //当前栈顶应用改变
                mUnlockPack = packname;
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getPackName().equals(packname)) {
                        Intent intent = new Intent(CoreService.this, AppLockActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("packName", packname);
                        CoreService.this.startActivity(intent);
                    }
                }
            }
        }
    }
}
