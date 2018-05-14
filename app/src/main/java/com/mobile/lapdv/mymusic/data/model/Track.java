package com.mobile.lapdv.mymusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lap on 10/05/2018.
 */

public class Track implements Parcelable {
    private String mArtworkUrl;
    private String mDescription;
    private boolean mDownloadable;
    private String mDownloadUrl;
    private long mDuration;
    private int mId;
    private int mLikesCount;
    private String mTitle;
    private String mUri;
    private String mUsername;
    private String mAvatarUrl;
    private int mPlaybackCount;

    public Track() {

    }

    protected Track(Parcel in) {
        mArtworkUrl = in.readString();
        mDescription = in.readString();
        mDownloadable = in.readByte() != 0;
        mDownloadUrl = in.readString();
        mDuration = in.readLong();
        mId = in.readInt();
        mLikesCount = in.readInt();
        mTitle = in.readString();
        mUri = in.readString();
        mUsername = in.readString();
        mAvatarUrl = in.readString();
        mPlaybackCount = in.readInt();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public int getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        mPlaybackCount = playbackCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mArtworkUrl);
        parcel.writeString(mDescription);
        parcel.writeByte((byte) (mDownloadable ? 1 : 0));
        parcel.writeString(mDownloadUrl);
        parcel.writeLong(mDuration);
        parcel.writeInt(mId);
        parcel.writeInt(mLikesCount);
        parcel.writeString(mTitle);
        parcel.writeString(mUri);
        parcel.writeString(mUsername);
        parcel.writeString(mAvatarUrl);
        parcel.writeInt(mPlaybackCount);
    }

    public interface TrackEntity {
        String COLLECTION = "collection";
        String TRACK = "track";
        String ARTWORK_URL = "artwork_url";
        String DESCRIPTION = "description";
        String DOWNLOADABLE = "downloadable";
        String DOWNLOAD_URL = "download_url";
        String DURATION = "duration";
        String ID = "id";
        String PLAYBACK_COUNT = "playback_count";
        String TITLE = "title";
        String URI = "uri";
        String USER = "user";
        String AVATAR_URL = "avatar_url";
        String LIKES_COUNT = "likes_count";
        String USERNAME = "username";
        String LARGE_IMAGE_SIZE = "large";
        String BETTER_IMAGE_SIZE = "original";
    }
}
