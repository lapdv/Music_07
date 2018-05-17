package com.mobile.lapdv.mymusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lap on 09/05/2018.
 */

public class Genre implements Parcelable {
    private int position;
    private List<Track> mTrackModels;
    private String mType;

    protected Genre(Parcel in) {
        position = in.readInt();
        mTrackModels = in.createTypedArrayList(Track.CREATOR);
        mType = in.readString();
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(position);
        parcel.writeTypedList(mTrackModels);
        parcel.writeString(mType);
    }
}
