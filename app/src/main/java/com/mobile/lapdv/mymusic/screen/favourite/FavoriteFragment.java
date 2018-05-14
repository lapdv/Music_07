package com.mobile.lapdv.mymusic.screen.favourite;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BaseFragment;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.FavoriteRepository;
import com.mobile.lapdv.mymusic.screen.favourite.adapter.FavoriteAdapter;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

import java.util.List;

/**
 * Created by lap on 14/05/2018.
 */

public class FavoriteFragment extends BaseFragment implements FavoriteContract.View {

    private FavoriteContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
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
        mPresenter.getSongs();
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
    }
}
