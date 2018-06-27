package com.mobile.lapdv.mymusic.screen.track;

import com.mobile.lapdv.mymusic.base.mvp.BasePresenter;
import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.GenreDataSource;
import com.mobile.lapdv.mymusic.data.source.GenreRepository;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

import java.util.List;

/**
 * Created by lap on 16/05/2018.
 */

public class TrackMorePresenter extends BasePresenter<TrackMoreContract.View>
        implements TrackMoreContract.Presenter, GenreDataSource.OnFetchDataListener<Genre> {
    private GenreRepository mGenreRepository;

    public TrackMorePresenter(GenreRepository repository) {
        mGenreRepository = repository;
    }

    @Override
    public void onFetchDataSuccess(List<Genre> data) {
        if (EmptyUtils.isNotEmpty(data)) {
            getView().loadMore(data.get(0).getTrackModels());
        }
    }

    @Override
    public void onFetchDataFailure(String error) {

    }

    @Override
    public void loadMore(String type, int limit, int offset) {
        if (mGenreRepository != null) {
            mGenreRepository.getSongsMoreRemote(type, limit, offset, this);
        }
    }
}
