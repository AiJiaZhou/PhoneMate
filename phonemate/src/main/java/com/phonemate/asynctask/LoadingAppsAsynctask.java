package com.phonemate.asynctask;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Build;

import com.phonemate.model.panelmodel.OtherAppsEntity;
import com.phonemate.utils.PackageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: RanCQ
 * Date: 2015/9/12
 */
public class LoadingAppsAsynctask extends AsyncTask<Context,Void,List<ResolveInfo>>{
    private LoadAppsFinish loadAppsFinish;
    private Context mContext;
    public LoadingAppsAsynctask(LoadAppsFinish loadAppsFinish, Context mContext) {
        this.loadAppsFinish = loadAppsFinish;
        this.mContext=mContext;
    }

    @Override
    protected List<ResolveInfo> doInBackground(Context... params) {
           return PackageUtils.getAppInformation(mContext);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPostExecute(List<ResolveInfo> list) {
        List<OtherAppsEntity> otherAppsEntityList=null;
        if(list!=null && list.size()>0){
            otherAppsEntityList=new ArrayList<>();
                for(ResolveInfo resolveInfo:list){
                    String packageName=resolveInfo.activityInfo.packageName;
                    String name=resolveInfo.loadLabel(mContext.getPackageManager()).toString();
                    String className=resolveInfo.activityInfo.name;
                    OtherAppsEntity coreMenuSelectorModel=new OtherAppsEntity(mContext,packageName,className,name);
                    otherAppsEntityList.add(coreMenuSelectorModel);
                }
        }
        if(loadAppsFinish!=null) {
            loadAppsFinish.loadAppFinish(otherAppsEntityList);
        }
    }

    public interface  LoadAppsFinish{
        public void loadAppFinish(List<OtherAppsEntity> list);
    }
}
