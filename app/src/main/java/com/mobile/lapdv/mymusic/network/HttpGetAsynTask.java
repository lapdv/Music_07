package com.mobile.lapdv.mymusic.network;

import android.os.AsyncTask;
import android.os.Build;

import com.mobile.lapdv.mymusic.screen.home.model.GenresModel;
import com.mobile.lapdv.mymusic.screen.home.model.TrackModel;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lap on 08/05/2018.
 */

public class HttpGetAsynTask extends AsyncTask<String, Void, String> {

    private int mPosition;
    private OnFetchDataListener mOnFetchDataListener;
    private ArrayList<GenresModel> mGenresModels = new ArrayList<>();

    public HttpGetAsynTask(OnFetchDataListener onFetchDataListener, int position) {
        mOnFetchDataListener = onFetchDataListener;
        this.mPosition = position;
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
            GenresModel genresModel = new GenresModel(parseTrackJSONObject(data));
            genresModel.setPosition(mPosition);
            mGenresModels.add(genresModel);
            if (mOnFetchDataListener != null) {
                mOnFetchDataListener.onSuccess(mGenresModels, mPosition);
            }
        }
    }

    private ArrayList<TrackModel> parseTrackJSONObject(String data) {
        ArrayList<TrackModel> trackModelArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            String collection = jsonObject.getString(TrackModel.TrackEntity.COLLECTION);
            JSONArray arrayCollection = new JSONArray(collection);
            for (int i = 0; i < arrayCollection.length(); i++) {
                String track = arrayCollection.getJSONObject(i).getString(TrackModel.TrackEntity.TRACK);
                JSONObject jsonObjectTrack = new JSONObject(track);
                trackModelArrayList.add(setDataTrack(jsonObjectTrack));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trackModelArrayList;
    }

    private TrackModel setDataTrack(JSONObject jsonObjectTrack) throws JSONException {
        TrackModel trackModel = new TrackModel();
        trackModel.setArtworkUrl(jsonObjectTrack
                .getString(TrackModel.TrackEntity.ARTWORK_URL));
        trackModel.setDescription(jsonObjectTrack
                .getString(TrackModel.TrackEntity.DESCRIPTION));
        trackModel.setDownloadable(jsonObjectTrack
                .getBoolean(TrackModel.TrackEntity.DOWNLOADABLE));
        trackModel.setDownloadUrl(jsonObjectTrack
                .getString(TrackModel.TrackEntity.DOWNLOAD_URL));
        trackModel.setDuration(jsonObjectTrack
                .getLong(TrackModel.TrackEntity.DURATION));
        trackModel.setId(jsonObjectTrack
                .getInt(TrackModel.TrackEntity.ID));
        trackModel.setLikesCount(jsonObjectTrack
                .getInt(TrackModel.TrackEntity.LIKES_COUNT));
        trackModel.setPlaybackCount(jsonObjectTrack
                .getInt(TrackModel.TrackEntity.PLAYBACK_COUNT));
        trackModel.setTitle(jsonObjectTrack
                .getString(TrackModel.TrackEntity.TITLE));
        trackModel.setUri(jsonObjectTrack
                .getString(TrackModel.TrackEntity.URI));
        String user = jsonObjectTrack.getString(TrackModel.TrackEntity.USER);
        JSONObject jsonObjectUser = new JSONObject(user);
        trackModel.setUsername(jsonObjectUser
                .getString(TrackModel.TrackEntity.USERNAME));
        trackModel.setAvatarUrl(jsonObjectUser
                .getString(TrackModel.TrackEntity.AVATAR_URL));
        return trackModel;
    }

    public interface OnFetchDataListener {
        void onSuccess(ArrayList<GenresModel> data, int position);
    }
}
