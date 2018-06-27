package com.mobile.lapdv.mymusic.utils.music;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.utils.PreferencesUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AudioPlayer {

    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PLAYING = 2;
    private static final int STATE_PAUSE = 3;
    private static final long TIME_UPDATE = 300L;
    private MediaPlayer mediaPlayer;
    private Context context;
    private Handler handler;
    private int state = STATE_IDLE;
    private List<Track> mTrackModels;
    private Track mTrack;
    private final List<OnEventsPlayerListener> listeners = new ArrayList<>();
    private int position;

    public static AudioPlayer get() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static AudioPlayer instance = new AudioPlayer();
    }

    private AudioPlayer() {
    }

    public void setTrackModels(List<Track> mTrackModels) {
        this.mTrackModels = mTrackModels;
    }

    public void setTrackModel(Track track) {
        this.mTrack = track;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
        mediaPlayer = new MediaPlayer();
        handler = new Handler(Looper.getMainLooper());
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer media) {
                mediaPlayer.stop();
                next();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (isPreparing()) {
                    startPlayer();
                }
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
                for (OnEventsPlayerListener listener : listeners) {
                    listener.onBufferingUpdate(percent);
                }
            }
        });
    }

    public void addOnPlayEventListener(OnEventsPlayerListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeOnPlayEventListener(OnEventsPlayerListener listener) {
        listeners.remove(listener);
    }


    public void startPlayer() {
        if (!isPreparing() && !isPausing()) {
            return;
        }
        mediaPlayer.start();
        state = STATE_PLAYING;
        handler.post(mPublishRunnable);
        for (OnEventsPlayerListener listener : listeners) {
            listener.getDuration(mediaPlayer.getDuration());
            listener.onPlayerStart();
        }
    }

    private Runnable mPublishRunnable = new Runnable() {
        @Override
        public void run() {
            if (isPlaying()) {
                for (OnEventsPlayerListener listener : listeners) {
                    listener.onPublish(mediaPlayer.getCurrentPosition());
                }
            }
            handler.postDelayed(this, TIME_UPDATE);
        }
    };

    public void seekTo(int sec) {
        if (isPlaying() || isPausing()) {
            mediaPlayer.seekTo(sec * 1000);
        }
    }

    public Track getTrack() {
        if (mTrack == null) {
            return null;
        }
        return mTrack;
    }

    public Track getTrackModels() {
        if (mTrackModels == null || mTrackModels.isEmpty()) {
            return null;
        }
        return mTrackModels.get(getPlayPosition());
    }

    public int getPlayPosition() {
        if (this.position < 0 || this.position >= mTrackModels.size()) {
            this.position = 0;
        }
        return this.position;
    }

    public void play() {
        Track track = getTrackModels();
        if (mTrackModels.isEmpty() || track == null) {
            return;
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(track.getUri());
            mediaPlayer.prepareAsync();
            state = STATE_PREPARING;
            for (OnEventsPlayerListener listener : listeners) {
                listener.onChange(track);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playPause() {
        if (isPreparing()) {
            stopPlayer();
        } else if (isPlaying()) {
            pausePlayer();
        } else if (isPausing()) {
            startPlayer();
        } else {
            play();
        }
    }

    public void next() {
        if (mTrackModels == null || mTrackModels.isEmpty()) {
            return;
        }
        int mode = MusicMode.valueOf(PreferencesUtils.getPlayMode());
        switch (mode) {
            case MusicMode.PlayMode.RANDOM:
                position = new Random().nextInt(mTrackModels.size());
                break;
            case MusicMode.PlayMode.LOOP:
                break;
            case MusicMode.PlayMode.SINGLE:
                position = position + 1;
                break;
        }
        play();
    }

    public void prev() {
        if (mTrackModels.isEmpty()) {
            return;
        }
        int mode = MusicMode.valueOf(PreferencesUtils.getPlayMode());
        switch (mode) {
            case MusicMode.PlayMode.RANDOM:
                position = new Random().nextInt(mTrackModels.size());
                break;
            case MusicMode.PlayMode.LOOP:
                break;
            case MusicMode.PlayMode.SINGLE:
                position = position - 1;
                break;
        }
        play();
    }

    public void stopPlayer() {
        if (isIdle()) {
            return;
        }
        pausePlayer();
        mediaPlayer.reset();
        state = STATE_IDLE;
    }

    public void pausePlayer() {
        pausePlayer(true);
    }

    public void pausePlayer(boolean abandonAudioFocus) {
        if (!isPlaying()) {
            return;
        }
        mediaPlayer.pause();
        state = STATE_PAUSE;
        for (OnEventsPlayerListener listener : listeners) {
            listener.onPlayerPause();
        }
        handler.removeCallbacks(mPublishRunnable);
    }

    public void stop() {
        AudioPlayer.get().stopPlayer();
    }

    public boolean isPreparing() {
        return state == STATE_PREPARING;
    }

    public boolean isPlaying() {
        return state == STATE_PLAYING;
    }

    public boolean isPausing() {
        return state == STATE_PAUSE;
    }

    public boolean isIdle() {
        return state == STATE_IDLE;
    }
}

