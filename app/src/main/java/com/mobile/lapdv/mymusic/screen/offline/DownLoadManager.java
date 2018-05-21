package com.mobile.lapdv.mymusic.screen.offline;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.mobile.lapdv.mymusic.data.local.database.MusicDB;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.utils.ConfigApi;

import java.io.File;

/**
 * Created by lap on 17/05/2018.
 */

public class DownLoadManager {

    public static final String DIRECTORYNAME = "MyMusic";
    private static DownLoadManager sDownloadManager;
    private static DownloadReceiver mDownloadReceiver;
    private static DownloadManager sDownloadManagerSystem;
    private static long mDownloadId;

    private DownLoadManager() {
    }

    @SuppressLint("WrongConstant")
    public static DownLoadManager getInstance() {
        if (null == sDownloadManager) {
            sDownloadManager = new DownLoadManager();
            createFolder();
        }
        return sDownloadManager;
    }

    public static void registerReceiver(Context context) {
        mDownloadReceiver = new DownloadReceiver();
        context.registerReceiver(mDownloadReceiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public static void unregisterReceiver(Context context) {
        if (mDownloadReceiver != null && mDownloadReceiver.isOrderedBroadcast()) {
            context.unregisterReceiver(mDownloadReceiver);
        }
    }

    public static void requestDownload(Context context, Track track) {
        String url = ConfigApi.getUrlDownload(track.getUri());
        String test = "https://api.soundcloud.com/tracks/259412502/download?client_id=a7Ucuq0KY8Ksn8WzBG6wj4x6pcId6BpU";
        sDownloadManagerSystem = (DownloadManager)
                context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(test));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(track.getUsername());
        request.setDescription(track.getTitle());
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(getRoot(), track.getTitle() + "." + "mp3");
        mDownloadId = sDownloadManagerSystem.enqueue(request);
    }

    public static boolean validDownload() {
        Cursor c = sDownloadManagerSystem
                .query(new DownloadManager.Query().setFilterById(mDownloadId));
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                return true;
            }
        }
        return false;
    }


    private static File createFolder() {
        File filePath = null;
        try {
            filePath = new File(getRoot());
            if (!filePath.exists()) {
                if (!filePath.mkdirs()) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static String getRoot() {
        return new StringBuilder(Environment.DIRECTORY_DOWNLOADS)
                .append("/").append(DIRECTORYNAME).append("/").toString();
    }
}
