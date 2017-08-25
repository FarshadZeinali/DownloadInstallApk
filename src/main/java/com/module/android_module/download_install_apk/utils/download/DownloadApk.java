package com.module.android_module.download_install_apk.utils.download;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Farshad Zeinali
 * @version 1.0.0.0
 * @since 1503676292840
 * <p>
 *     Email:farshad.zeinaly@gmail.com
 * </p>
 */

public class DownloadApk
{
    private static DownloadApk download_apk=new DownloadApk();
    private DownloadApk(){}
    public static DownloadApk getInstance()
    {
        return download_apk;
    }
    public Object[] connect(String link, String params)
    {
        try
        {
            if(params!=null && !params.trim().isEmpty())
            {
                link+=params;
            }
            URL connection_url=new URL(link);
            HttpURLConnection httpURLConnection= (HttpURLConnection) connection_url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(60000);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + "utf-8");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();
            return new Object[]{httpURLConnection.getContentLength(),httpURLConnection.getInputStream()};
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
