package com.module.android_module.download_install_apk;

import android.content.Context;
import android.content.Intent;

import com.module.android_module.download_install_apk.service.DownloadApkService;
import com.module.android_module.download_install_apk.utils.install.InstallApk;


/**
 * @author Farshad Zeinali
 * @version 1.0.0.0
 * @since 1503676089386
 * <p>
 *     Email:farshad.zeinaly@gmail.com
 * </p>
 */

public class DownloadInstallApkManager
{
    public void startDownloadInstall(Context context,String url, String apk_path,int notification_icon)
    {
        if(InstallApk.getInstance().isApkInstallable(context,apk_path))
        {
            InstallApk.getInstance().install(context,apk_path);
        }
        else
        {
            Intent intent=new Intent(context, DownloadApkService.class);
            intent.putExtra("url",url);
            intent.putExtra("apk_path",apk_path);
            intent.putExtra("notification_icon",String.valueOf(notification_icon));
            context.startService(intent);
        }
    }
}
