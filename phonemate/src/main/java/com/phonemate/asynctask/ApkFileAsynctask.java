package com.phonemate.asynctask;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;

import com.phonemate.model.AppManagerApkEntity;
import com.rxx.fast.utils.LUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/3 09:47
 * 修改人：colorful
 * 修改时间：15/12/3 09:47
 * 修改备注：
 */
public class ApkFileAsynctask extends AsyncTask<Void, Void, List<AppManagerApkEntity>> {

    private File rootFile = null;
    private ApkFilter mFileFilter;
    private List<File> mApkList;
    private  ApkScanningFinishListener mListener;
    private PackageManager mPackageManager;
    public ApkFileAsynctask(ApkScanningFinishListener mListener,PackageManager mPackageManager) {
        this.mListener=mListener;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rootFile = Environment.getExternalStorageDirectory();
        }
        mFileFilter=new ApkFilter();
        mApkList=new ArrayList<>();
        this.mPackageManager=mPackageManager;
    }

    @Override
    protected List<AppManagerApkEntity> doInBackground(Void... params) {
        if (rootFile == null) {
            return null;
        } else {
            getAllFiles(rootFile);
        }
        if(mApkList!=null && mApkList.size()>0) {
            List<AppManagerApkEntity> list = new ArrayList<>();
            for (File file : mApkList) {
                AppManagerApkEntity apkEntity = new AppManagerApkEntity();

                PackageInfo info = mPackageManager.getPackageArchiveInfo(file.getAbsolutePath(),
                        PackageManager.GET_ACTIVITIES);
                if (info != null) {
                    ApplicationInfo appInfo = info.applicationInfo;
                    appInfo.sourceDir = file.getAbsolutePath();
                    appInfo.publicSourceDir = file.getAbsolutePath();

                    apkEntity.appClass=appInfo.className;
                    apkEntity.appName=mPackageManager.getApplicationLabel(appInfo).toString();
                    apkEntity.appPackage=appInfo.packageName;
                    apkEntity.appSize=String.format("%.2f",file.length()/1024f/1024);
                    apkEntity.appVersion=info.versionName;
                    apkEntity.appPath=file.getAbsolutePath();
                    try {
                        apkEntity.appDrawable= appInfo.loadIcon(mPackageManager);
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }
                }
                LUtils.i(apkEntity.toString());
                list.add(apkEntity);

            }
            return list;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<AppManagerApkEntity> files) {
        super.onPostExecute(files);
        if(mListener!=null){
            mListener.scanningFilish(files);
        }
    }

    // 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
    private void getAllFiles(File root) {
        File files[] = root.listFiles(mFileFilter);
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    getAllFiles(f);
                } else {
                    mApkList.add(f);
                }
            }
        }
    }

    public interface  ApkScanningFinishListener{
        public void scanningFilish(List<AppManagerApkEntity> list);
    }

    class ApkFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            if (pathname.isHidden()) {//过滤隐藏文件
                return false;
            }

            if (pathname.isDirectory()) {//文件夹符合
                return true;
            } else {
                if (pathname.getName().endsWith(".apk")) {//文件是 apk 的符合
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

}
