package com.mobile.lapdv.mymusic.screen.search;

import com.mobile.lapdv.mymusic.base.mvp.BasePresenter;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.SearchDataSource;
import com.mobile.lapdv.mymusic.data.source.SearchRepository;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

import java.util.List;

/**
 * Created by lap on 15/05/2018.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View>
        implements SearchContract.Presenter, SearchDataSource.OnFetchDataListener<Track> {

    private SearchRepository mSearchRepository;

    public SearchPresenter(SearchRepository searchRepository) {
        mSearchRepository = searchRepository;
    }

    @Override
    public void onFetchDataSuccess(List<Track> data) {
        if (EmptyUtils.isNotEmpty(data)) {
            getView().getSearchSuccess(data);
        }
    }

    @Override
    public void onFetchDataFailure(String error) {
        getView().getSearchFailure(error);
    }

    @Override
    public void getSearch(String keyWord) {
        mSearchRepository.getSongRemote(this, keyWord);
    }

    @Override
    public void addFavorite(Track track) {
        if (EmptyUtils.isNotEmpty(track)) {
            mSearchRepository.addFavorite(track);
        }
    }
}
