package com.module.android_module.download_install_apk.utils.io;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author Farshad Zeinali
 * @version 1.0.0.0
 * @since 1503676349399
 * <p>
 *     Email:farshad.zeinaly@gmail.com
 * </p>
 */

public class IO
{
    private File file=null;
    private FileOutputStream fileOutputStream=null;
    private String device_download_apk_temp_path=null;
    private String device_download_apk_path=null;

    public String getDeviceDownloadApkPath()
    {
        if(device_download_apk_path==null || device_download_apk_path.trim().isEmpty())
        {
            setDefaultDeviceDownloadApkPath();
        }
        return device_download_apk_path;
    }

    public void setDeviceDownloadApkPath(String device_download_apk_path) {
        this.device_download_apk_path = device_download_apk_path;
        setDeviceDownloadApkTempPath(new File(device_download_apk_path).getParent()+"/.temp_apk");
    }

    public String getDeviceDownloadApkTempPath()
    {
        if(device_download_apk_temp_path==null || device_download_apk_temp_path.trim().isEmpty())
        {
            setDefaultDeviceDownloadApkTempPath();
        }
        return device_download_apk_temp_path;
    }

    private void setDeviceDownloadApkTempPath(String device_download_apk_temp_path) {
        this.device_download_apk_temp_path = device_download_apk_temp_path;
    }

    private void setDefaultDeviceDownloadApkPath()
    {
        setDeviceDownloadApkPath(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    private void setDefaultDeviceDownloadApkTempPath()
    {
        setDeviceDownloadApkPath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/.temp_apk");
    }


    public void openFileOutputStream()throws Exception
    {
        file=new File(getDeviceDownloadApkTempPath());
        if(!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        removeFile();
        fileOutputStream=new FileOutputStream(file,true);
    }
    public void writeByteToFile(byte[] bytes,int off, int len)throws Exception
    {
        fileOutputStream.write(bytes,off,len);
        fileOutputStream.flush();
    }

    public void copyDownloadedFile()throws Exception
    {
        file.renameTo(new File(getDeviceDownloadApkPath()));
        removeFile();
    }
    public void closeFileOutputStream()throws Exception
    {
        if(fileOutputStream!=null)
        {
            fileOutputStream.close();
        }
    }
    public void removeFile()
    {
        if(file!=null && file.exists())
        {
            file.delete();
        }
    }
}
