package com.mobile.lapdv.mymusic.data.source;

import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.remote.GenreRemoteDataSource;

/**
 * Created by lap on 09/05/2018.
 */

public class GenreRepository implements GenreDataSource.RemoteDataSource {
    private static GenreRepository sGenreReopository;

    private GenreDataSource.RemoteDataSource mRemoteDataSource;

    private GenreRepository(GenreDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public static GenreRepository getInstance() {
        if (sGenreReopository == null) {
            sGenreReopository = new GenreRepository(GenreRemoteDataSource.getInstance());
        }
        return sGenreReopository;
    }

    @Override
    public void getSongsRemote(GenreDataSource.OnFetchDataListener<Genre> listener) {
        mRemoteDataSource.getSongsRemote(listener);
    }
}
