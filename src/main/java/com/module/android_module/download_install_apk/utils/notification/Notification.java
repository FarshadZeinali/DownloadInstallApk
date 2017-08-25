package com.module.android_module.download_install_apk.utils.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.module.android_module.download_install_apk.R;

/**
 * @author Farshad Zeinali
 * @version 1.0.0.0
 * @since 1503676380415
 * <p>
 *     Email:farshad.zeinaly@gmail.com
 * </p>
 */
public class Notification implements Runnable
{
    private NotificationManager notification_manager=null;
    private NotificationCompat.Builder builder=null;
    private int notify_id=0;
    private int progress=0;
    public void showNotification(Context context,String title,String text,int notification_icon,int notify_id)
    {
        if(notification_icon<0)
        {
            notification_icon=R.mipmap.notification;
        }
        notification_manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title).setContentText(text).setSmallIcon(notification_icon);
        builder.setProgress(100,0,false);
        notification_manager.notify(notify_id,builder.build());
    }

    public void updateNotification(int notify_id,int progress)
    {
        if(progress>this.progress)
        {
            this.notify_id=notify_id;
            this.progress=progress;
            new Thread(this).start();
        }
    }

    public void cancelNotification(int notify_id)
    {
        notification_manager.cancel(notify_id);
    }

    @Override
    public void run()
    {
        if(progress<100)
        {
            builder.setProgress(100,progress,false);
            notification_manager.notify(notify_id,builder.build());
        }
        else
        {
            cancelNotification(notify_id);
        }
    }
}
