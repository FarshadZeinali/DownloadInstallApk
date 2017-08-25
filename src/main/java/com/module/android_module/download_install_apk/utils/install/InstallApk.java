package com.module.android_module.download_install_apk.utils.install;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.module.android_module.download_install_apk.utils.application_info.ApplicationInfo;

import java.io.File;


/**
 * @author Farshad Zeinali
 * @version 1.0.0.0
 * @since 1503676328024
 * <p>
 *     Email:farshad.zeinaly@gmail.com
 * </p>
 */
public class InstallApk
{
    private static InstallApk install_apk=new InstallApk();
    private InstallApk(){}
    public static InstallApk getInstance(){
        return install_apk;
    }
    public boolean isApkInstallable(Context context,String apk_path)
    {
        File apk_file=new File(apk_path);
        if(!apk_file.exists())
        {
            return false;
        }
        String[] downloaded_apk_information= ApplicationInfo.getInstance().getArchiveApplicationInformation(context,apk_path);
        String[] installed_apk_information= ApplicationInfo.getInstance().getArchiveApplicationInformation(context,downloaded_apk_information[0]);
        if(installed_apk_information[0]!=null)
        {
            return !compareApplicationsInformation(downloaded_apk_information,installed_apk_information);
        }
        return true;
    }

    public void install(Context context,String apk_path)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apk_path)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private boolean compareApplicationsInformation(String[] downloaded_apk_information,String[] installed_apk_information)
    {
      try
      {
          if(!downloaded_apk_information[0].equals(installed_apk_information[0]))
          {
              return false;
          }
          String[] downloaded_apk_version_numbers=downloaded_apk_information[2].split("\\.");
          String[] installed_apk_version_numbers=downloaded_apk_information[2].split("\\.");
          for (int i = 0; i < downloaded_apk_version_numbers.length; i++) {
              if (Integer.parseInt(downloaded_apk_version_numbers[i]) < Integer.parseInt(installed_apk_version_numbers[i])) {
                  return true;
              }
              else if (Integer.parseInt(downloaded_apk_version_numbers[i])>Integer.parseInt(installed_apk_version_numbers[i])) {
                  return false;
              }
          }
      }
      catch (Exception ex){}
        return false;
    }
}
