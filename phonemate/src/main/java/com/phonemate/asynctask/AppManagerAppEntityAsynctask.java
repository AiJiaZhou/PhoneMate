package com.phonemate.asynctask;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;

import com.phonemate.PMApplication;
import com.phonemate.model.AppLockEntity;
import com.phonemate.model.AppManagerAppEntity;
import com.phonemate.utils.DateUtil;
import com.phonemate.utils.PackageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: RanCQ
 * Date: 2015/9/12
 */
public class AppManagerAppEntityAsynctask extends AsyncTask<Context, Void, List<AppManagerAppEntity>> {
    private LoadAppsFinish loadAppsFinish;
    private Context mContext;
    private List<AppLockEntity> mlist;
    private PackageManager packageManager;

    public AppManagerAppEntityAsynctask(LoadAppsFinish loadAppsFinish, Context mContext) {
        this.loadAppsFinish = loadAppsFinish;
        this.mContext = mContext;
        mlist = ((PMApplication) mContext.getApplicationContext()).fastDB.queryAll(AppLockEntity.class);
        packageManager=mContext.getPackageManager();
    }

    @Override
    protected List<AppManagerAppEntity> doInBackground(Context... params) {
        List<ResolveInfo> list = PackageUtils.getAppInformation(mContext);
        List<AppManagerAppEntity> otherAppsEntityList = null;
        if (list != null && list.size() > 0) {
            otherAppsEntityList = new ArrayList<>();
            for (ResolveInfo resolveInfo : list) {
                String packageName = resolveInfo.activityInfo.packageName;
                String name = resolveInfo.loadLabel(mContext.getPackageManager()).toString();
                String className = resolveInfo.activityInfo.name;
                PackageInfo packageInfo=null;
                try {
                    packageInfo=packageManager.getPackageInfo(packageName, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String time= DateUtil.longToString(packageInfo.lastUpdateTime,"yyyy-MM-dd HH:mm:ss");
                String installTime=DateUtil.longToString(packageInfo.firstInstallTime,"yyyy-MM-dd HH:mm:ss");
                String version=packageInfo.versionName;
                Drawable drawable=null;
                try {
                    drawable=packageManager.getApplicationIcon(packageName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                AppManagerAppEntity appLockEntity = new AppManagerAppEntity();

                appLockEntity.appDrawable=drawable;
                appLockEntity.appClass=className;
                appLockEntity.appName=name;
                appLockEntity.appPackage=packageName;
                appLockEntity.appUpdateTime =time;
                appLockEntity.appInstallTime =installTime;
                appLockEntity.appSize="";
                appLockEntity.appVersion=version;
                try {
                    if ((mContext.getPackageManager().getPackageInfo(packageName, 0).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        //第三方应用
                        appLockEntity.appType=appLockEntity.TYPE_OTHRES;
                    } else {
                        //系统应用
                        appLockEntity.appType=appLockEntity.TYPE_SYSTEM;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                otherAppsEntityList.add(appLockEntity);
            }
        }
        return otherAppsEntityList;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPostExecute(List<AppManagerAppEntity> list) {
        if (loadAppsFinish != null) {
            loadAppsFinish.loadAppFinish(list);
        }
    }

    public interface LoadAppsFinish {
        public void loadAppFinish(List<AppManagerAppEntity> list);
    }
}
