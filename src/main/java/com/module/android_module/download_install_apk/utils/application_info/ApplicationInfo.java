package com.module.android_module.download_install_apk.utils.application_info;

import android.content.Context;
import android.content.pm.PackageInfo;

/**
 * @author Farshad Zeinali
 * @version 1.0.0.0
 * @since 1503676272952
 * <p>
 *     Email:farshad.zeinaly@gmail.com
 * </p>
 */

public class ApplicationInfo
{
    private static ApplicationInfo application_info=new ApplicationInfo();
    private ApplicationInfo(){}
    public static ApplicationInfo getInstance()
    {
        return application_info;
    }

    public String[] getArchiveApplicationInformation(Context context,String apk_path)
    {
        try {
            return getApplicationInformation(context.getPackageManager().getPackageArchiveInfo(apk_path, 0));
        } catch (Exception ex) {
            return new String[]{null, ex.toString()};
        }
    }

    public String[] getApplicationInformation(Context context, String package_name) {
        try {
            return getApplicationInformation(context.getPackageManager().getPackageInfo(package_name, 0));
        } catch (Exception ex) {
            return new String[]{null, ex.toString()};
        }
    }

    private String[] getApplicationInformation(PackageInfo package_info)throws Exception
    {
        return
                new String[]
                        {
                                package_info.packageName,
                                String.valueOf(package_info.versionCode),
                                package_info.versionName,
                                String.valueOf(package_info.firstInstallTime),
                                String.valueOf(package_info.lastUpdateTime)
                        };
    }
}
