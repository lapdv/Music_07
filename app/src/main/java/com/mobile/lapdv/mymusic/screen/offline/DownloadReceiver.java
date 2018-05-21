package com.mobile.lapdv.mymusic.screen.offline;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.mobile.lapdv.mymusic.R;

/**
 * Created by lap on 17/05/2018.
 */

public class DownloadReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 455;

    /**
     * show notifi when downloaded !
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownLoadManager.validDownload()) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_app)
                            .setContentText(context
                                    .getString(R.string.string_download_completed));
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            Toast.makeText(context, R.string.string_download_succes, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.string_download_false, Toast.LENGTH_SHORT).show();
        }
    }
}
