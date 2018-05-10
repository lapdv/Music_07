package com.mobile.lapdv.mymusic.screen.home;

import com.mobile.lapdv.mymusic.base.mvp.BasePresenter;
import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.source.remote.GenreDataSource;
import com.mobile.lapdv.mymusic.data.source.remote.GenreReopository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lap on 05/05/2018.
 */

public class HomePresenter extends BasePresenter<HomeContract.View>
        implements HomeContract.Presenter, GenreDataSource.OnFetchDataListener<Genre> {

    private GenreReopository mGenreReopository;
    private List<Genre> mGenresModels = new ArrayList<>();

    public HomePresenter(GenreReopository reopository) {
        mGenreReopository = reopository;
    }

    @Override
    public void getSongs() {
        mGenreReopository.getSongsRemote(this);
    }

    @Override
    public void onFetchDataSuccess(List<Genre> data) {
        mGenresModels.addAll(data);
        sortGenresResult(mGenresModels);
        getView().getSongsSuccess(mGenresModels);
    }

    @Override
    public void onFetchDataFailure(String error) {
    }

    private void sortGenresResult(List<Genre> genresModels) {
        Collections.sort(genresModels, new Comparator<Genre>() {
            @Override
            public int compare(Genre genresModel1, Genre genresModel2) {
                return genresModel1.getPosition() > genresModel2.getPosition() ? 1 : -1;
            }
        });
    }
}
