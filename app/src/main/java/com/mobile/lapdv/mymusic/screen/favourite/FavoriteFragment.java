package com.mobile.lapdv.mymusic.screen.favourite;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BaseFragment;
import com.mobile.lapdv.mymusic.callback.OnGetListDataListener;
import com.mobile.lapdv.mymusic.data.local.database.MusicDB;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.FavoriteRepository;
import com.mobile.lapdv.mymusic.screen.favourite.adapter.FavoriteAdapter;
import com.mobile.lapdv.mymusic.screen.offline.DownLoadManager;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

import java.util.List;

/**
 * Created by lap on 14/05/2018.
 */

public class FavoriteFragment extends BaseFragment implements FavoriteContract.View
        , OnGetListDataListener<Track> {

    private FavoriteContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private TextView mTextViewNoData;
    private FavoriteAdapter mFavoriteAdapter;

    @Override
    protected void initView(View view) {
        if (null == mPresenter) {
            mPresenter = new FavoritePresenter(FavoriteRepository
                    .getInstance(getBaseActivity()));
            mPresenter.onAttach(this);
            findView(view);
        }
    }

    private void findView(View view) {
        mTextViewNoData = view.findViewById(R.id.text_no_data_favorite);
        mRecyclerView = view.findViewById(R.id.recycler_track);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initData() {
        mFavoriteAdapter = new FavoriteAdapter(getBaseActivity());
        mFavoriteAdapter.setOnFavoriteListener(new FavoriteAdapter.OnFavoriteListener() {
            @Override
            public void onRemoveFavorite(Track track) {
                if (EmptyUtils.isNotEmpty(track)) {
                    mFavoriteAdapter.deleteItem(track);
                    mPresenter.removeFavorite(track);
                    Toast.makeText(getContext(),
                            R.string.string_title_remove_favorite,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDowload(Track track) {
                if (track.isDownloadable()) {
                    mPresenter.addTrackOffline(track);
                    DownLoadManager.getInstance().requestDownload(getBaseActivity(), track);
                } else {
                    Toast.makeText(getBaseActivity(), R.string.string_is_downloadable
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
        mFavoriteAdapter.setTrackOnItemClick(this);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void getSongsSuccess(List<Track> trackList) {
        if (EmptyUtils.isNotEmpty(trackList)) {
            mFavoriteAdapter.setData(trackList);
            mRecyclerView.setAdapter(mFavoriteAdapter);
        }
    }

    @Override
    public void getSongsFailure(String messages) {
        mTextViewNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        DownLoadManager.unregisterReceiver(getBaseActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getSongs();
        DownLoadManager.registerReceiver(getBaseActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DownLoadManager.unregisterReceiver(getBaseActivity());
    }

    @Override
    public void onItemClick(List<Track> list, int position) {
        if (getBaseActivity() != null) {
            getBaseActivity().gotoPlayMusicActivity(list, position);
        }
    }
}
