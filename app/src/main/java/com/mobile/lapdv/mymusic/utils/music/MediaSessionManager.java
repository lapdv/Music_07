package com.mobile.lapdv.mymusic.utils.music;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.mobile.lapdv.mymusic.service.PlayMusicService;

/**
 * Created by lap on 16/05/2018.
 */

public class MediaSessionManager {

    private static final String TAG = "MediaSessionManager";

    private static final long MEDIA_SESSION_ACTIONS = PlaybackStateCompat.ACTION_PLAY
            | PlaybackStateCompat.ACTION_PAUSE
            | PlaybackStateCompat.ACTION_PLAY_PAUSE
            | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
            | PlaybackStateCompat.ACTION_STOP
            | PlaybackStateCompat.ACTION_SEEK_TO;

    private PlayMusicService playService;
    private MediaSessionCompat mediaSession;

    public static MediaSessionManager get() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static MediaSessionManager instance = new MediaSessionManager();
    }

    private MediaSessionManager() {
    }

    public void init(PlayMusicService playService) {
        this.playService = playService;
        setupMediaSession();
    }

    private void setupMediaSession() {
        mediaSession = new MediaSessionCompat(playService, TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
                | MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS);
        mediaSession.setCallback(callback);
        mediaSession.setActive(true);
    }

    private MediaSessionCompat.Callback callback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            AudioPlayer.get().playPause();
        }

        @Override
        public void onPause() {
            AudioPlayer.get().playPause();
        }

        @Override
        public void onSkipToNext() {
            // AudioPlayer.get().next();
        }

        @Override
        public void onSkipToPrevious() {
            //   AudioPlayer.get().prev();
        }

        @Override
        public void onStop() {
            AudioPlayer.get().stopPlayer();
        }

        @Override
        public void onSeekTo(long pos) {
            AudioPlayer.get().seekTo((int) pos);
        }
    };
}
