package com.mobile.lapdv.mymusic.screen.track;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BaseActivity;
import com.mobile.lapdv.mymusic.base.BasePlayerActivity;
import com.mobile.lapdv.mymusic.callback.OnGetListDataListener;
import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.GenreRepository;
import com.mobile.lapdv.mymusic.screen.playmusic.PlayMusicActivity;
import com.mobile.lapdv.mymusic.screen.track.adapter.TrackMoreAdapter;
import com.mobile.lapdv.mymusic.utils.Constant;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.utils.GlideUtils;
import com.mobile.lapdv.mymusic.utils.music.AudioPlayer;
import com.mobile.lapdv.mymusic.widget.MusicVisualizer;
import com.mobile.lapdv.mymusic.widget.ToolBarApp;

import java.util.List;

/**
 * Created by lap on 15/05/2018.
 */

public class TrackMoreActivity extends BasePlayerActivity implements
        TrackMoreContract.View {

    private ToolBarApp mToolBarApp;
    private RecyclerView mRecyclerView;
    private Genre mGenre;
    private TrackMoreContract.Presenter mPresenter;
    private TrackMoreAdapter mTrackMoreAdapter;
    private GridLayoutManager mGridLayoutManager;
    private boolean isScrolling;
    private int currentItems, totalItems, scrollItems;
    private ImageView mImageTrack, mImageNext, mImagePlay, mImagePrevios;
    private TextView mTextTitle, mTextUser;
    private View mLayoutBottom;
    private MusicVisualizer mMusicVisualizer;

    @Override
    protected void initView() {
        mPresenter = new TrackMorePresenter(GenreRepository.getInstance());
        mPresenter.onAttach(this);
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
        mToolBarApp = findViewById(R.id.toolbar_app);
        mRecyclerView = findViewById(R.id.recycler_track);
        mGridLayoutManager = new GridLayoutManager(this, Constant.SPAN_TWO_COUNT);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mTrackMoreAdapter.getDataItem(position) == null ?
                        Constant.SPAN_TWO_COUNT : Constant.SPAN_ONE_COUNT;
            }
        });
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        initToolBar();
    }

    private void initToolBar() {
        mToolBarApp.setOnClickItemIconToolBar(new ToolBarApp.OnClickItemToolBar() {
            @Override
            public void onItemRight() {

            }

            @Override
            public void onItemLeft() {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        if (getIntent() != null && getIntent().getExtras().containsKey(Constant.BUNDLE_GENRE)) {
            mGenre = getIntent().getExtras().getParcelable(Constant.BUNDLE_GENRE);
            bindViewData(mGenre);
        }
    }

    private void bindViewData(Genre genre) {
        if (EmptyUtils.isNotEmpty(genre)) {
            mToolBarApp.setTextLeftToolbar(genre.getType().toUpperCase());
            setUpAdapter(genre);
        }
    }

    private void setUpAdapter(Genre genre) {
        mTrackMoreAdapter = new TrackMoreAdapter(this);
        mTrackMoreAdapter.setData(genre.getTrackModels());
        mRecyclerView.setAdapter(mTrackMoreAdapter);
        mTrackMoreAdapter.setTrackOnItemClick(new OnGetListDataListener<Track>() {
            @Override
            public void onItemClick(List<Track> list, int position) {
                gotoPlayMusicActivity(list, position);
            }
        });
        loadMoreData();
    }

    private void loadMoreData() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mGridLayoutManager.getChildCount();
                totalItems = mGridLayoutManager.getItemCount();
                scrollItems = mGridLayoutManager.findFirstVisibleItemPosition();
                if (!isScrolling && (currentItems + scrollItems == totalItems)) {
                    mTrackMoreAdapter.addItemLoaMore(null);
                    fetDataLoadMore();
                    isScrolling = true;
                }
            }
        });
    }

    private void fetDataLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadMore(mGenre.getType(), Constant.DEFAULT_LIMIT, totalItems);
            }
        }, 2000);
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_track_more;
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
    public void loadMore(List<Track> trackList) {
        mTrackMoreAdapter.removeItemLoadMore(null);
        isScrolling = false;
        if (EmptyUtils.isNotEmpty(trackList)) {
            mTrackMoreAdapter.addItemsAtFront(trackList);
        }
    }

    @Override
    public void getSearchFailure(String messages) {
        Toast.makeText(this, messages, Toast.LENGTH_SHORT).show();
    }
}
