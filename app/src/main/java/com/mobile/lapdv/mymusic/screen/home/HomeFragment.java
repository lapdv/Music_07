package com.mobile.lapdv.mymusic.screen.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BaseFragment;
import com.mobile.lapdv.mymusic.callback.OnGetListDataListener;
import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.GenreRepository;
import com.mobile.lapdv.mymusic.screen.home.adapter.GenresAdapter;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

import java.util.List;

/**
 * Created by lap on 04/05/2018.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomeContract.Presenter mHomePresenter;
    private RecyclerView mGenresRecyclerView;
    private ProgressBar mProgressBar;
    private GenresAdapter mGenresAdapter;

    @Override
    protected void initView(View view) {
        mProgressBar = view.findViewById(R.id.progress_loadding);
        mGenresRecyclerView = view.findViewById(R.id.recycler_genres);
        mGenresRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        mHomePresenter = new HomePresenter(GenreRepository.getInstance());
        mHomePresenter.onAttach(this);
        mGenresAdapter = new GenresAdapter(getBaseActivity());
        mProgressBar.setVisibility(View.VISIBLE);
        mHomePresenter.getSongs();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getSongsSuccess(final List<Genre> genresModels) {
        if (EmptyUtils.isNotEmpty(genresModels)) {
            mGenresAdapter.setData(genresModels);
            mGenresRecyclerView.setAdapter(mGenresAdapter);
            mProgressBar.setVisibility(View.GONE);
            mGenresAdapter.setTrackOnItemClick(new OnGetListDataListener<Track>() {
                @Override
                public void onItemClick(List<Track> list, int position) {
                    if (getBaseActivity() != null) {
                        getBaseActivity().gotoPlayMusicActivity(list, position);
                    }
                }
            });
        }
    }

    @Override
    public void getSongsFailure(String messages) {
        mProgressBar.setVisibility(View.GONE);
    }
}
