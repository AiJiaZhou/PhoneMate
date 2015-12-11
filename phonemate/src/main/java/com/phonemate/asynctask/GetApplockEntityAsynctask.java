package com.phonemate.asynctask;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Build;

import com.phonemate.PMApplication;
import com.phonemate.model.AppLockEntity;
import com.phonemate.utils.PackageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: RanCQ
 * Date: 2015/9/12
 */
public class GetApplockEntityAsynctask extends AsyncTask<Context, Void, List<AppLockEntity>> {
    private LoadAppsFinish loadAppsFinish;
    private Context mContext;
    private List<AppLockEntity> mlist;

    public GetApplockEntityAsynctask(LoadAppsFinish loadAppsFinish, Context mContext) {
        this.loadAppsFinish = loadAppsFinish;
        this.mContext = mContext;
        mlist = ((PMApplication) mContext.getApplicationContext()).fastDB.queryAll(AppLockEntity.class);
    }

    @Override
    protected List<AppLockEntity> doInBackground(Context... params) {
        List<ResolveInfo> list = PackageUtils.getAppInformation(mContext);
        List<AppLockEntity> otherAppsEntityList = null;
        if (list != null && list.size() > 0) {
            otherAppsEntityList = new ArrayList<>();
            for (ResolveInfo resolveInfo : list) {
                String packageName = resolveInfo.activityInfo.packageName;
                String name = resolveInfo.loadLabel(mContext.getPackageManager()).toString();
                String className = resolveInfo.activityInfo.name;

                AppLockEntity appLockEntity = new AppLockEntity();
                appLockEntity.setClassName(className);
                appLockEntity.setAppName(name);
                appLockEntity.setPackName(packageName);
                try {
                    appLockEntity.setAppDrawable(mContext.getPackageManager().getApplicationIcon(packageName));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (mlist.contains(appLockEntity)) {
                    appLockEntity.setIsLock(true);
                } else {
                    appLockEntity.setIsLock(false);
                }
                try {
                    if ((mContext.getPackageManager().getPackageInfo(packageName, 0).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        //第三方应用
                        appLockEntity.setAppType("第三方应用");
                    } else {
                        //系统应用
                        appLockEntity.setAppType("系统应用");
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
    protected void onPostExecute(List<AppLockEntity> list) {
        if (loadAppsFinish != null) {
            loadAppsFinish.loadAppFinish(list);
        }
    }

    public interface LoadAppsFinish {
        public void loadAppFinish(List<AppLockEntity> list);
    }
}
