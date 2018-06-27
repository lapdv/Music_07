package com.mobile.lapdv.mymusic.screen.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BasePlayerActivity;
import com.mobile.lapdv.mymusic.callback.OnGetListDataListener;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.SearchRepository;
import com.mobile.lapdv.mymusic.screen.offline.DownLoadManager;
import com.mobile.lapdv.mymusic.screen.playmusic.PlayMusicActivity;
import com.mobile.lapdv.mymusic.screen.search.adapter.SearchAdapter;
import com.mobile.lapdv.mymusic.utils.EditTextUtil;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.utils.GlideUtils;
import com.mobile.lapdv.mymusic.utils.music.AudioPlayer;
import com.mobile.lapdv.mymusic.widget.MusicVisualizer;
import com.mobile.lapdv.mymusic.widget.ToolBarApp;

import java.util.List;

/**
 * Created by lap on 14/05/2018.
 */

public class SearchActivity extends BasePlayerActivity implements
        SearchContract.View, ToolBarApp.OnCallBackSearch,
        SearchAdapter.OnSearchListener {

    private SearchContract.Presenter mPresenter;
    private boolean mIsShowingKeyboard;
    private ToolBarApp mToolBarApp;
    private RecyclerView mRecyclerViewTrack;
    private SearchAdapter mSearchAdapter;
    private ProgressBar mProgressBar;
    private ImageView mImageTrack, mImageNext, mImagePlay, mImagePrevios;
    private TextView mTextTitle, mTextUser;
    private View mLayoutBottom;
    private MusicVisualizer mMusicVisualizer;

    @Override
    protected void initView() {
        mLayoutBottom = findViewById(R.id.layout_bottom_control);
        mLayoutBottom.setOnClickListener(this);
        mImageTrack = mLayoutBottom.findViewById(R.id.image_mini_track);
        mImagePlay = mLayoutBottom.findViewById(R.id.image_play_pause);
        mImagePlay.setOnClickListener(this);
        mImageNext = mLayoutBottom.findViewById(R.id.image_action_next);
        mImageNext.setOnClickListener(this);
        mImagePrevios = mLayoutBottom.findViewById(R.id.image_previous);
        mImagePrevios.setOnClickListener(this);
        mTextUser = mLayoutBottom.findViewById(R.id.text_track_user);
        mTextTitle = mLayoutBottom.findViewById(R.id.text_tilte_track);
        mMusicVisualizer = mLayoutBottom.findViewById(R.id.visualizer);
        mProgressBar = findViewById(R.id.progress_loadding);
        mPresenter = new SearchPresenter(SearchRepository.getInstance(this));
        mPresenter.onAttach(this);
        mToolBarApp = findViewById(R.id.toolbar_app);
        mRecyclerViewTrack = findViewById(R.id.recycler_track);
        mRecyclerViewTrack.setLayoutManager(new LinearLayoutManager(this));
        initToolBar();
    }

    private void initToolBar() {
        mToolBarApp.setOnClickItemIconToolBar(new ToolBarApp.OnClickItemToolBar() {
            @Override
            public void onItemRight() {
                mToolBarApp.clearEditText();
            }

            @Override
            public void onItemLeft() {
                if (mIsShowingKeyboard) {
                    EditTextUtil.hideSoftKeyboard(SearchActivity.this);
                } else {
                    onBackPressed();
                }
            }
        });
        mToolBarApp.setOnCallBackSearch(this);
        mToolBarApp.settingSearch(true);
    }

    @Override
    protected void initData() {
        mSearchAdapter = new SearchAdapter(this);
        mSearchAdapter.setOnSearchListener(this);
        mSearchAdapter.setTrackOnItemClick(new OnGetListDataListener<Track>() {
            @Override
            public void onItemClick(List<Track> list, int position) {
                gotoPlayMusicActivity(list, position);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public int getContainerId() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_bottom_control:
                openActivity(PlayMusicActivity.class);
                break;
            case R.id.image_action_next:
                next();
                break;
            case R.id.image_previous:
                prev();
                break;
            case R.id.image_play_pause:
                playpause();
                mImagePlay.setImageResource(!AudioPlayer.get().isPausing() ?
                        R.drawable.ic_pause : R.drawable.ic_play);
                break;
        }
    }

    @Override
    public void getSearchSuccess(List<Track> trackList) {
        if (EmptyUtils.isNotEmpty(trackList)) {
            mSearchAdapter.setData(trackList);
            mRecyclerViewTrack.setAdapter(mSearchAdapter);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void getSearchFailure(String messages) {
        Toast.makeText(this, messages, Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void getInputSearch(String input) {
        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.getSearch(input);
        EditTextUtil.hideSoftKeyboard(SearchActivity.this);
    }

    @Override
    public void onAddFavorite(Track track) {
        if (EmptyUtils.isNotEmpty(track)) {
            mPresenter.addFavorite(track);
            Toast.makeText(this, R.string.string_title_add_favorite,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDowload(Track track) {
        if (track.isDownloadable()) {
            DownLoadManager.getInstance().requestDownload(this, track);
        } else {
            Toast.makeText(this, R.string.string_is_downloadable
                    , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void updateView(Track song) {
        if (EmptyUtils.isNotEmpty(song)) {
            mLayoutBottom.setVisibility(View.VISIBLE);
            mMusicVisualizer.setVisibility(!AudioPlayer.get().isPausing() ? View.VISIBLE : View.INVISIBLE);
            mMusicVisualizer.setColor(getResources().getColor(R.color.color_white));
            GlideUtils.loadImage(this, mImageTrack, song.getAvatarUrl());
            mTextTitle.setText(song.getTitle());
            mTextUser.setText(song.getUsername());
            mImagePlay.setImageResource(!AudioPlayer.get().isPausing() ?
                    R.drawable.ic_pause : R.drawable.ic_play);
        }
        super.updateView(song);
    }
}
