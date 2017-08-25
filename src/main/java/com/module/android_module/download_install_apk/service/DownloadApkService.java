package com.module.android_module.download_install_apk.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.module.android_module.download_install_apk.R;
import com.module.android_module.download_install_apk.utils.download.DownloadApk;
import com.module.android_module.download_install_apk.utils.io.IO;
import com.module.android_module.download_install_apk.utils.notification.Notification;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Farshad Zeinali
 * @version 1.0.0.0
 * @since 1503676177017
 * <p>
 *     Email:farshad.zeinaly@gmail.com
 * </p>
 */

public class DownloadApkService extends Service implements Runnable
{
    private Map<String,String[]> thread_apk_path=new HashMap<>();
    private IO io=null;
    private int notification_progress=0;
    private boolean is_service_start=false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if(intent!=null && intent.getStringExtra("url")!=null && intent.getStringExtra("apk_path")!=null)
        {
            String[] download_information=new String[3];
            download_information[0]=intent.getStringExtra("url");
            download_information[1]=intent.getStringExtra("apk_path");
            download_information[2]=intent.getStringExtra("notification_icon");
            thread_apk_path.put(String.valueOf(startId),download_information);
            is_service_start=true;
            Thread thread=new Thread(this);
            thread.setName(String.valueOf(startId));
            thread.start();
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy()
    {
        stopService();
        super.onDestroy();
    }

    @Override
    public void run()
    {
        if(!is_service_start)
        {
            stopService();
            return;
        }
        int notify_id=new Random().nextInt(Integer.MAX_VALUE);
        Notification notification=new Notification();
        io=new IO();
        String[] information=thread_apk_path.get(Thread.currentThread().getName());
        try
        {
            notification.showNotification(getApplicationContext(),getApplicationContext().getResources().getString(R.string.notification_title),getApplicationContext().getResources().getString(R.string.notification_text),Integer.parseInt(information[2]),notify_id);
            Object[] connection_information=DownloadApk.getInstance().connect(information[0],null);
            InputStream inputStream= (InputStream) connection_information[1];
            int file_length= (int) connection_information[0];
            long total_length=0;
            int count;
            byte[] temp_bytes=new byte[1024];
            io.setDeviceDownloadApkPath(information[1]);
            io.openFileOutputStream();
            while ((count= inputStream.read(temp_bytes,0,temp_bytes.length))>0) {
                io.writeByteToFile(temp_bytes,0,count);
                total_length+=count;
                notification_progress=(int) ((total_length*100)/file_length);
                notification.updateNotification(notify_id,notification_progress);
            }
            inputStream.close();
            io.closeFileOutputStream();
            io.copyDownloadedFile();
        }
        catch (Exception ex)
        {
            System.err.println(ex.toString());
            notification.cancelNotification(notify_id);
        }
        finally {
            stopService();
        }
    }


    private void stopService()
    {
        is_service_start=false;
        stopSelf();
    }
}
