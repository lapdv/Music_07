package com.mobile.lapdv.mymusic.data.model;

import java.util.List;

/**
 * Created by lap on 09/05/2018.
 */

public class Genre {
    private int position;
    private List<Track> mTrackModels;

    public Genre(List<Track> trackModels) {
        mTrackModels = trackModels;
    }

    public List<Track> getTrackModels() {
        return mTrackModels;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
