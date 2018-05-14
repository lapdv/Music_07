package com.mobile.lapdv.mymusic.screen.home;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BaseFragment;
import com.mobile.lapdv.mymusic.callback.OnRecyclerViewItemClick;
import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.GenreReopository;
import com.mobile.lapdv.mymusic.screen.home.adapter.GenresAdapter;
import com.mobile.lapdv.mymusic.screen.playmusic.PlayMusicActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lap on 04/05/2018.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomeContract.Presenter mHomePresenter;
    private RecyclerView mGenresRecyclerView;
    private GenresAdapter mGenresAdapter;

    @Override
    protected void initView(View view) {
        mGenresRecyclerView = view.findViewById(R.id.recycler_genres);
        mGenresRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        mHomePresenter = new HomePresenter(GenreReopository.getInstance());
        mHomePresenter.onAttach(this);
        mGenresAdapter = new GenresAdapter(getBaseActivity());
        mHomePresenter.getSongs();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getSongsSuccess(final List<Genre> genresModels) {
        mGenresAdapter.setData(genresModels);
        mGenresRecyclerView.setAdapter(mGenresAdapter);
        mGenresAdapter.setTrackOnItemClick(new OnRecyclerViewItemClick<Track>() {
            @Override
            public void onItemClick(Track track, int position) {
                getBaseActivity().openActivity(PlayMusicActivity.class);
            }
        });
        //TODO start service send data
    }

    @Override
    public void getSongsFailure(String messages) {
        // TODO: 09/05/2018
    }
}
