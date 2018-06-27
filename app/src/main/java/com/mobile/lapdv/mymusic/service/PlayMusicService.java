package com.mobile.lapdv.mymusic.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.screen.main.MainActivity;
import com.mobile.lapdv.mymusic.screen.search.SearchActivity;
import com.mobile.lapdv.mymusic.utils.Constant;
import com.mobile.lapdv.mymusic.utils.music.AudioPlayer;
import com.mobile.lapdv.mymusic.utils.music.MediaSessionManager;

import java.util.ArrayList;

/**
 * Created by lap on 16/05/2018.
 */

public class PlayMusicService extends Service {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private ArrayList<Track> mTrackList;
    private int mPosition;
    private Track mTrack;
    private static final int NOTIFICATION_ID = 1111111;
    private static final int ORDER_ACTION_PREVIOUS = 0;
    private static final int ORDER_ACTION_PLAY_PAUSE = 1;
    private static final int ORDER_ACTION_NEXT = 2;
    private PendingIntent pendingIntentOpenApp;
    private PendingIntent nextPendingIntent;
    private PendingIntent prevPendingIntent;
    private PendingIntent ptPlayPause;
    private NotificationCompat.Builder mBuilder;
    private Bitmap mBitmap;
    private NotificationManagerCompat mNotificationManager;

    /**
     * Return this instance of PlayMusicService so clients can call public methods
     */
    public class PlayBinder extends Binder {
        public PlayMusicService getService() {
            return PlayMusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = NotificationManagerCompat.from(this);
        createNotificationChannel();
        AudioPlayer.get().init(this);
        MediaSessionManager.get().init(this);
    }

    private void createNotificationChannel() {
        CharSequence name = "Timber";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            manager.createNotificationChannel(mChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case Actions.ACTION_STOP:
                    stop();
                    break;
                case Actions.MAIN_ACTION:
                    initNotifycation();
                    break;
                case Actions.ACTION_START_FORE_GROUND:
                    handleAudioPlayer(intent);
                    initNotifycation();
                    break;
                case Actions.PLAY_ACTION:
                    playpause();
                    updateNotification();
                    break;
                case Actions.NEXT_ACTION:
                    next();
                    updateNotification();
                    break;
                case Actions.PREV_ACTION:
                    prev();
                    updateNotification();
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    private void initNotifycation() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Actions.MAIN_ACTION);
        pendingIntentOpenApp = PendingIntent.getActivity(getApplicationContext(), 0,
                intent, 0);

        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_app);
        Intent actionNextIntent = new Intent(getApplicationContext(), PlayMusicService.class);
        actionNextIntent.setAction(Actions.NEXT_ACTION);
        nextPendingIntent = PendingIntent.getService(getApplicationContext(),
                0, actionNextIntent, 0);

        Intent actionPrevIntent = new Intent(getApplicationContext(), PlayMusicService.class);
        actionPrevIntent.setAction(Actions.PREV_ACTION);
        prevPendingIntent = PendingIntent.getService(getApplicationContext(),
                0, actionPrevIntent, 0);

        Intent actionPlayIntent = new Intent(getApplicationContext(), PlayMusicService.class);
        actionPlayIntent.setAction(Actions.PLAY_ACTION);
        ptPlayPause = PendingIntent.getService(getApplicationContext(), 0,
                actionPlayIntent, 0);
        updateNotification();
    }

    public void updateNotification() {
        if (AudioPlayer.get().isPausing()) {
            mNotificationManager.notify(NOTIFICATION_ID, buildNotification());
            stopForeground(false);
        } else {
            startForeground(NOTIFICATION_ID, buildNotification());
        }
    }

    private Notification buildNotification() {
        int playButtonResId = !AudioPlayer.get().isPausing() ?
                R.drawable.ic_pause : R.drawable.ic_play;
        mBuilder = new NotificationCompat.Builder(getApplicationContext(), "")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentTitle(getTrackName())
                .setContentText(getUserName())
                .setColor(getResources().getColor(R.color.color_md_amber_900))
                .setLargeIcon(mBitmap)
                .setSmallIcon(R.drawable.ic_app)
                .setContentIntent(pendingIntentOpenApp)
                .addAction(R.drawable.ic_previous, "", prevPendingIntent)
                .addAction(playButtonResId, "", ptPlayPause)
                .addAction(R.drawable.ic_next, "", nextPendingIntent)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(ORDER_ACTION_PREVIOUS,
                                ORDER_ACTION_PLAY_PAUSE, ORDER_ACTION_NEXT));
        Notification notification = mBuilder.build();
        return notification;
    }

    private String getTrackName() {
        synchronized (this) {
            return AudioPlayer.get().getTrackModels().getTitle();
        }
    }

    private String getUserName() {
        synchronized (this) {
            return AudioPlayer.get().getTrackModels().getUsername();
        }
    }

    private void handleAudioPlayer(Intent intent) {
        if (intent != null) {
            if (intent.getExtras().containsKey(Track.class.getName())) {
                mTrackList = intent.getExtras().getParcelableArrayList(Track.class.getName());
                mPosition = intent.getExtras().getInt(Constant.POSITION);
                AudioPlayer.get().setTrackModels(mTrackList);
                AudioPlayer.get().setPosition(mPosition);
                play();
            }
        }
    }

    private void stop() {
        AudioPlayer.get().stopPlayer();
    }

    public Track getSong() {
        return AudioPlayer.get().getTrackModels();
    }

    public Track getTrackBySearch() {
        return AudioPlayer.get().getTrack();
    }

    public void play() {
        AudioPlayer.get().play();
    }

    public void playpause() {
        AudioPlayer.get().playPause();
    }

    public void next() {
        AudioPlayer.get().next();
    }

    public void prev() {
        AudioPlayer.get().prev();
    }
}
