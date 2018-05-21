package com.mobile.lapdv.mymusic.data.source.remote.network;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.GenreDataSource;
import com.mobile.lapdv.mymusic.data.source.SearchDataSource;
import com.mobile.lapdv.mymusic.utils.ConfigApi;
import com.mobile.lapdv.mymusic.utils.Constant;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lap on 14/05/2018.
 */

public class SearchTrackAsynTask extends AsyncTask<String, Void, String> {

    private SearchDataSource.OnFetchDataListener<Track> mOnFetchDataListener;

    public SearchTrackAsynTask(SearchDataSource.OnFetchDataListener<Track> onCallBackDataListener) {
        mOnFetchDataListener = onCallBackDataListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
        if (mOnFetchDataListener != null && EmptyUtils.isNotEmpty(data)) {
            mOnFetchDataListener.onFetchDataSuccess(parseSongObject(data));
        } else {
            mOnFetchDataListener.onFetchDataFailure(Constant.ERROR_MESSAGE);
        }
    }

    public List<Track> parseSongObject(String data) {
        List<Track> songList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectSong = (JSONObject) jsonArray.get(i);
                Track song = new Track();
                song.setArtworkUrl(Constant.replaceAvartarUrl(objectSong
                        .getString(Track.TrackEntity.ARTWORK_URL)));
                song.setDescription(objectSong.getString(Track.TrackEntity.DESCRIPTION));
                song.setDownloadable(objectSong.getBoolean(Track.TrackEntity.DOWNLOADABLE));
                song.setDownloadUrl(objectSong.getString(Track.TrackEntity.DOWNLOAD_URL));
                song.setDuration(objectSong.getLong(Track.TrackEntity.DURATION));
                song.setId(objectSong.getInt(Track.TrackEntity.ID));
                song.setLikesCount(objectSong.getInt(Track.TrackEntity.LIKES_COUNT));
                song.setPlaybackCount(objectSong.getInt(Track.TrackEntity.PLAYBACK_COUNT));
                song.setTitle(objectSong.getString(Track.TrackEntity.TITLE));
                song.setUri(ConfigApi.getUriStream(objectSong
                        .getString(Track.TrackEntity.URI)));
                JSONObject jsonObjectUser = new JSONObject(objectSong.getString(Track.TrackEntity.USER));
                song.setUsername(jsonObjectUser.getString(Track.TrackEntity.USERNAME));
                song.setAvatarUrl(Constant.replaceAvartarUrl(
                        jsonObjectUser.getString(Track.TrackEntity.AVATAR_URL)));
                songList.add(song);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return songList;
    }

    public interface OnCallBackDataListener<T> {
        void onFetchDataListener(List<T> t);
    }
}
