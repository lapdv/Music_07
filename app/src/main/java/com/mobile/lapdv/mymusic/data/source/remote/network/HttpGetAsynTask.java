package com.mobile.lapdv.mymusic.data.source.remote.network;

import android.os.AsyncTask;
import android.os.Build;

import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.GenreDataSource;
import com.mobile.lapdv.mymusic.utils.Constant;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lap on 08/05/2018.
 */

public class HttpGetAsynTask extends AsyncTask<String, Void, String> {

    private int mPosition;
    private List<Genre> mGenresModels;
    private GenreDataSource.OnFetchDataListener<Genre> mOnFetchDataListener;

    public HttpGetAsynTask(int position, GenreDataSource.OnFetchDataListener<Genre> listener) {
        mPosition = position;
        mOnFetchDataListener = listener;
        mGenresModels = new ArrayList<>();
    }

    public void executeTask(String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        } else {
            this.execute(url);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        BaseConnection connection = new HTTPConnection(strings[0]);
        connection.setURLConnectionCommonParam();
        return connection.doGetRequest();
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        if (EmptyUtils.isNotEmpty(data)) {
            Genre genresModel = new Genre(parseTrackJSONObject(data));
            genresModel.setPosition(mPosition);
            mGenresModels.add(genresModel);
            if (mOnFetchDataListener != null) {
                mOnFetchDataListener.onFetchDataSuccess(mGenresModels);
            }
        }
    }

    private List<Track> parseTrackJSONObject(String data) {
        ArrayList<Track> trackModelArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            String collection = jsonObject.getString(Track.TrackEntity.COLLECTION);
            JSONArray arrayCollection = new JSONArray(collection);
            for (int i = 0; i < arrayCollection.length(); i++) {
                String track = arrayCollection.getJSONObject(i).getString(Track.TrackEntity.TRACK);
                JSONObject jsonObjectTrack = new JSONObject(track);
                trackModelArrayList.add(setDataTrack(jsonObjectTrack));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trackModelArrayList;
    }

    private Track setDataTrack(JSONObject jsonObjectTrack) throws JSONException {
        Track trackModel = new Track();
        try {
            trackModel.setArtworkUrl(jsonObjectTrack
                    .getString(Track.TrackEntity.ARTWORK_URL));
            trackModel.setDescription(jsonObjectTrack
                    .getString(Track.TrackEntity.DESCRIPTION));
            trackModel.setDownloadable(jsonObjectTrack
                    .getBoolean(Track.TrackEntity.DOWNLOADABLE));
            trackModel.setDownloadUrl(jsonObjectTrack
                    .getString(Track.TrackEntity.DOWNLOAD_URL));
            trackModel.setDuration(jsonObjectTrack
                    .getLong(Track.TrackEntity.DURATION));
            trackModel.setId(jsonObjectTrack
                    .getInt(Track.TrackEntity.ID));
            trackModel.setLikesCount(jsonObjectTrack
                    .getInt(Track.TrackEntity.LIKES_COUNT));
            trackModel.setPlaybackCount(jsonObjectTrack
                    .getInt(Track.TrackEntity.PLAYBACK_COUNT));
            trackModel.setTitle(jsonObjectTrack
                    .getString(Track.TrackEntity.TITLE));
            trackModel.setUri(jsonObjectTrack
                    .getString(Track.TrackEntity.URI));
            String user = jsonObjectTrack.getString(Track.TrackEntity.USER);
            JSONObject jsonObjectUser = new JSONObject(user);
            trackModel.setUsername(jsonObjectUser
                    .getString(Track.TrackEntity.USERNAME));
            trackModel.setAvatarUrl(Constant.replaceAvartarUrl(jsonObjectUser
                    .getString(Track.TrackEntity.AVATAR_URL)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trackModel;
    }
}
