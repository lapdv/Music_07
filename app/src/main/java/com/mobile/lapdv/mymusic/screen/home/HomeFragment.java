package com.mobile.lapdv.mymusic.screen.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BaseFragment;
import com.mobile.lapdv.mymusic.callback.OnRecyclerViewItemClick;
import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.source.remote.GenreReopository;
import com.mobile.lapdv.mymusic.screen.home.adapter.GenresAdapter;

import java.util.List;

/**
 * Created by lap on 04/05/2018.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomePresenter mHomeContrack;
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
        mHomeContrack = new HomePresenter(GenreReopository.getInstance());
        mHomeContrack.onAttach(this);
        mGenresAdapter = new GenresAdapter(getBaseActivity());
        mGenresAdapter.setOnRecyclerViewItemClick(onClickItemRecyclerView());
        mHomeContrack.getSongs();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getSongsSuccess(List<Genre> genresModels) {
        mGenresAdapter.setData(genresModels);
        mGenresRecyclerView.setAdapter(mGenresAdapter);
    }

    @Override
    public void getSongsFailure(String messages) {
        // TODO: 09/05/2018
    }

    private OnRecyclerViewItemClick<Genre> onClickItemRecyclerView() {
        return new OnRecyclerViewItemClick<Genre>() {
            @Override
            public void onItemClick(Genre genresModel, int position) {

            }
        };
    }
}
