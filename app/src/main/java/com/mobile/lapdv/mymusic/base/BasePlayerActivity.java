package com.mobile.lapdv.mymusic.base;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.service.PlayMusicService;
import com.mobile.lapdv.mymusic.utils.music.AudioPlayer;
import com.mobile.lapdv.mymusic.utils.music.OnEventsPlayerListener;

/**
 * Created by lap on 19/05/2018.
 */

public abstract class BasePlayerActivity extends BaseActivity
        implements ServiceConnection, OnEventsPlayerListener {
    private PlayMusicService mPlayMusicService;

    @Override
    protected void initData() {
        AudioPlayer.get().addOnPlayEventListener(this);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        PlayMusicService.PlayBinder mPlayBinder = (PlayMusicService.PlayBinder) iBinder;
        mPlayMusicService = mPlayBinder.getService();
        if (mPlayMusicService.getSong() != null) {
            updateView(mPlayMusicService.getSong());
        }
    }

    protected void updateView(Track song) {
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mPlayMusicService = null;
    }

    @Override
    protected void onResume() {
        if (mPlayMusicService != null) {
            updateView(mPlayMusicService.getSong());
        } else {
            Intent intent = new Intent(this, PlayMusicService.class);
            bindService(intent, this, BIND_AUTO_CREATE);
        }
        super.onResume();
    }

    @Override
    public void onChange(Track music) {
        updateView(music);
    }

    protected void next() {
        if (mPlayMusicService != null) {
            mPlayMusicService.next();
            updateView(mPlayMusicService.getSong());
        }
    }

    protected void prev() {
        if (mPlayMusicService != null) {
            mPlayMusicService.prev();
            updateView(mPlayMusicService.getSong());
        }
    }

    protected void playpause() {
        if (mPlayMusicService != null) {
            mPlayMusicService.playpause();
            updateView(mPlayMusicService.getSong());
        }
    }

    @Override
    public void onPlayerStart() {
        updateView(mPlayMusicService.getSong());
    }

    @Override
    public void onPlayerPause() {
        updateView(mPlayMusicService.getSong());
    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onBufferingUpdate(int percent) {

    }

    @Override
    public void getDuration(int duration) {

    }
}
