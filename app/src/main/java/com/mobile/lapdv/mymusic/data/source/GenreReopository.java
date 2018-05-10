package com.mobile.lapdv.mymusic.data.source;

import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.source.remote.GenreRemoteDataSource;

/**
 * Created by lap on 09/05/2018.
 */

public class GenreReopository implements GenreDataSource.RemoteDataSource {
    private static GenreReopository sGenreReopository;

    private GenreDataSource.RemoteDataSource mRemoteDataSource;

    private GenreReopository(GenreDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public static GenreReopository getInstance() {
        if (sGenreReopository == null) {
            sGenreReopository = new GenreReopository(GenreRemoteDataSource.getInstance());
        }
        return sGenreReopository;
    }

    @Override
    public void getSongsRemote(GenreDataSource.OnFetchDataListener<Genre> listener) {
        mRemoteDataSource.getSongsRemote(listener);
    }
}
