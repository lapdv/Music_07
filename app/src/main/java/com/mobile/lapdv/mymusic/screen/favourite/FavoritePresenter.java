package com.mobile.lapdv.mymusic.screen.favourite;

import com.mobile.lapdv.mymusic.base.mvp.BasePresenter;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.FavoriteDataSource;
import com.mobile.lapdv.mymusic.data.source.FavoriteRepository;
import com.mobile.lapdv.mymusic.utils.ConfigApi;
import com.mobile.lapdv.mymusic.utils.Constant;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

import java.util.List;

/**
 * Created by lap on 14/05/2018.
 */

public class FavoritePresenter extends BasePresenter<FavoriteContract.View>
        implements FavoriteContract.Presenter, FavoriteDataSource.OnFetchDataListener<Track> {

    private FavoriteRepository mFavoriteRepository;

    public FavoritePresenter(FavoriteRepository reopository) {
        mFavoriteRepository = reopository;
    }

    @Override
    public void getSongs() {
        mFavoriteRepository.getSongList(this);
    }

    @Override
    public void removeFavorite(Track track) {
        if (EmptyUtils.isNotEmpty(track)) {
            mFavoriteRepository.removeFavorite(track);
        }
    }

    @Override
    public void onFetchDataSuccess(List<Track> data) {
        if (EmptyUtils.isNotEmpty(data)) {
            getView().getSongsSuccess(data);
        } else {
            getView().getSongsFailure(Constant.ERROR_MESSAGE);
        }
    }

    @Override
    public void onFetchDataFailure(String error) {
        getView().getSongsFailure(error);
    }
}
