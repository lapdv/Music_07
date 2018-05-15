package com.mobile.lapdv.mymusic.screen.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BaseActivity;
import com.mobile.lapdv.mymusic.callback.OnRecyclerViewItemClick;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.SearchRepository;
import com.mobile.lapdv.mymusic.screen.playmusic.PlayMusicActivity;
import com.mobile.lapdv.mymusic.screen.search.adapter.SearchAdapter;
import com.mobile.lapdv.mymusic.utils.EditTextUtil;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.widget.ToolBarApp;

import java.util.List;

/**
 * Created by lap on 14/05/2018.
 */

public class SearchActivity extends BaseActivity implements
        SearchContract.View, ToolBarApp.OnCallBackSearch, SearchAdapter.OnSearchListener {

    private SearchContract.Presenter mPresenter;
    private boolean mIsShowingKeyboard;
    private ToolBarApp mToolBarApp;
    private RecyclerView mRecyclerViewTrack;
    private SearchAdapter mSearchAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void initView() {
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
        mSearchAdapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClick<Track>() {
            @Override
            public void onItemClick(Track track, int position) {
                openActivity(PlayMusicActivity.class);
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
        //TODO dowload task
    }
}
