package com.mobile.lapdv.mymusic.screen.playmusic;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BaseActivity;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.FavoriteRepository;
import com.mobile.lapdv.mymusic.screen.offline.DownLoadManager;
import com.mobile.lapdv.mymusic.service.Actions;
import com.mobile.lapdv.mymusic.service.PlayMusicService;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.utils.GlideUtils;
import com.mobile.lapdv.mymusic.utils.PreferencesUtils;
import com.mobile.lapdv.mymusic.utils.Utilities;
import com.mobile.lapdv.mymusic.utils.music.AudioPlayer;
import com.mobile.lapdv.mymusic.utils.music.MusicMode;
import com.mobile.lapdv.mymusic.utils.music.OnEventsPlayerListener;
import com.mobile.lapdv.mymusic.widget.ToolBarApp;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by lap on 10/05/2018.
 */

public class PlayMusicActivity extends BaseActivity implements OnEventsPlayerListener,
        SeekBar.OnSeekBarChangeListener {
    private ToolBarApp mToolBar;
    private ImageView mImageTrack;
    private TextView mTextTitleTrack, mTextDurationStart, mTextDurationEnd;
    private PopupMenu mPopupMenu;
    private SeekBar mProgressMusic;
    private SeekBar mProgressVolume;
    private boolean isDraggingProgress;
    private AudioManager mAudioManager;
    private ProgressBar mProgressBarLoading;
    private PlayMusicService mPlayMusicService;
    private ImageView mImageReplay;
    private ImageView mImageRandom;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayMusicService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void initView() {
        mToolBar = findViewById(R.id.tool_bar);
        mProgressBarLoading = findViewById(R.id.progress_loadding);
        mImageTrack = findViewById(R.id.image_track);
        mTextTitleTrack = findViewById(R.id.text_tilte_track);
        mTextDurationStart = findViewById(R.id.text_duration_start);
        mTextDurationEnd = findViewById(R.id.text_duration_end);
        mProgressMusic = findViewById(R.id.seekbar_music);
        mProgressVolume = findViewById(R.id.seekbar_volume);
        mProgressMusic.setOnSeekBarChangeListener(this);
        mProgressVolume.setOnSeekBarChangeListener(this);
        findViewById(R.id.image_previous).setOnClickListener(this);
        findViewById(R.id.image_play).setOnClickListener(this);
        findViewById(R.id.image_next).setOnClickListener(this);
        mImageRandom = findViewById(R.id.image_random);
        mImageRandom.setOnClickListener(this);
        mImageReplay = findViewById(R.id.image_replay);
        mImageReplay.setOnClickListener(this);
        initToolBar();
        intPopupMenu();
        initVolume();
    }

    private void initToolBar() {
        mToolBar.setIconToolBarLeft(getResources().getDrawable(R.drawable.ic_back));
        mToolBar.setIconToolBarRight(getResources().getDrawable(R.drawable.ic_more));
        mToolBar.setOnClickItemIconToolBar(new ToolBarApp.OnClickItemToolBar() {
            @Override
            public void onItemRight() {
                if (mPopupMenu != null) mPopupMenu.show();
            }

            @Override
            public void onItemLeft() {
                onBackPressed();
            }
        });
    }

    private void intPopupMenu() {
        mPopupMenu = new PopupMenu(this, mToolBar.getIconToolBarRight());
        mPopupMenu.inflate(R.menu.custom_menu);
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_dowload:
                        //TODO test
                        if (!mPlayMusicService.getSong().isDownloadable()) {
                            DownLoadManager.getInstance().requestDownload(PlayMusicActivity.this,
                                    mPlayMusicService.getSong());
                            mPopupMenu.getMenu().getItem(0).setIcon(R.drawable.ic_download);
                        } else {
                            Toast.makeText(PlayMusicActivity.this,
                                    getString(R.string.string_is_downloadable), LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.action_favorite:
                        if (EmptyUtils.isNotEmpty(mPlayMusicService.getSong())) {
                            FavoriteRepository.getInstance(PlayMusicActivity.this)
                                    .addFavorite(mPlayMusicService.getSong());
                            Toast.makeText(PlayMusicActivity.this,
                                    R.string.string_title_add_favorite, LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void initVolume() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mProgressVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        mProgressVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    @Override
    protected void initData() {
        AudioPlayer.get().addOnPlayEventListener(this);
    }

    private void bindViewData(Track track) {
        if (EmptyUtils.isNotEmpty(track)) {
            GlideUtils.loadImage(this, mImageTrack, track.getAvatarUrl());
            mTextTitleTrack.setText(!TextUtils.isEmpty(track.getTitle())
                    ? track.getTitle() : "");
            mToolBar.setTextCenterToolbar(!TextUtils.isEmpty(track.getUsername())
                    ? track.getUsername() : "");
            mTextDurationEnd.setText(Utilities.milliSecondsToTimer(track.getDuration()));
        }
    }

    private void play() {
        AudioPlayer.get().playPause();
        ((ImageView) findViewById(R.id.image_play))
                .setImageResource(!AudioPlayer.get().isPausing() ?
                        R.drawable.ic_pause : R.drawable.ic_play);
    }

    private void next() {
        PreferencesUtils.savePlayMode(0);
        mProgressMusic.setProgress(0);
        mPlayMusicService.next();
    }

    private void prev() {
        PreferencesUtils.savePlayMode(0);
        mProgressMusic.setProgress(0);
        mPlayMusicService.prev();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_play_music;
    }

    @Override
    public int getContainerId() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_random:
                if (PreferencesUtils.getPlayMode() == MusicMode.PlayMode.SINGLE) {
                    switchPlayMode(MusicMode.PlayMode.RANDOM);
                } else {
                    switchPlayMode(MusicMode.PlayMode.SINGLE);
                }
                mImageRandom.setImageResource(PreferencesUtils.getPlayMode()
                        == MusicMode.PlayMode.SINGLE ? R.drawable.ic_random : R.drawable.ic_un_random);
                break;
            case R.id.image_previous:
                prev();
                break;
            case R.id.image_play:
                play();
                break;
            case R.id.image_next:
                next();
                break;
            case R.id.image_replay:
                if (PreferencesUtils.getPlayMode() == MusicMode.PlayMode.LOOP) {
                    switchPlayMode(MusicMode.PlayMode.SINGLE);
                } else {
                    switchPlayMode(MusicMode.PlayMode.LOOP);
                }
                mImageReplay.setImageResource(PreferencesUtils.getPlayMode()
                        == MusicMode.PlayMode.SINGLE ? R.drawable.ic_replay : R.drawable.ic_un_replay);
                break;
        }
    }

    private void switchPlayMode(int mode) {
        PreferencesUtils.savePlayMode(mode);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        System.out.println("seekbar" + seekBar + i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar == mProgressMusic) {
            isDraggingProgress = true;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == mProgressMusic) {
            isDraggingProgress = false;
            if (AudioPlayer.get().isPlaying() || AudioPlayer.get().isPausing()) {
                int progress = seekBar.getProgress();
                AudioPlayer.get().seekTo(progress);
            } else {
                seekBar.setProgress(0);
            }
        } else if (seekBar == mProgressVolume) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(),
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }
    }

    private BroadcastReceiver mVolumeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mProgressVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Actions.ACTION_CHANGE_VOLUME);
        registerReceiver(mVolumeReceiver, filter);
        DownLoadManager.registerReceiver(this);
    }

    @Override
    public void onChange(Track music) {
        bindViewData(music);
    }

    @Override
    public void onPlayerStart() {
        ((ImageView) findViewById(R.id.image_play))
                .setImageResource(!AudioPlayer.get().isPausing() ?
                        R.drawable.ic_pause : R.drawable.ic_play);
    }

    @Override
    public void onPlayerPause() {
        ((ImageView) findViewById(R.id.image_play))
                .setImageResource(!AudioPlayer.get().isPausing() ?
                        R.drawable.ic_pause : R.drawable.ic_play);
    }

    @Override
    public void onPublish(int progress) {
        if (!isDraggingProgress) {
            mProgressMusic.setProgress(progress / 1000);
            mTextDurationStart.setText(Utilities.getProgressDuration(progress));
            onResume();
        }
    }

    @Override
    public void onBufferingUpdate(int percent) {
        if (percent != 0) {
            mProgressMusic.setSecondaryProgress(mProgressMusic.getMax() * 100 / percent);
            onResume();
        }
    }

    @Override
    public void getDuration(int duration) {
        mProgressMusic.setMax(duration / 1000);
        mTextDurationEnd.setText(Utilities.getProgressDuration(duration));
    }

    @Override
    protected void onDestroy() {
        AudioPlayer.get().removeOnPlayEventListener(this);
        unbindService(mServiceConnection);
        unregisterReceiver(mVolumeReceiver);
        DownLoadManager.unregisterReceiver(this);
        super.onDestroy();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayMusicService.PlayBinder mPlayBinder = (PlayMusicService.PlayBinder) iBinder;
            mPlayMusicService = mPlayBinder.getService();
            if (mPlayMusicService.getSong() != null) {
                bindViewData(mPlayMusicService.getSong());
            } else {
                bindViewData(mPlayMusicService.getTrackBySearch());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mPlayMusicService = null;
        }
    };
}
