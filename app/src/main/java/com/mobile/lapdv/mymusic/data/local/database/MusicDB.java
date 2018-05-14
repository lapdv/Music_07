package com.mobile.lapdv.mymusic.data.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobile.lapdv.mymusic.data.model.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lap on 14/05/2018.
 */

public class MusicDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "MusicDB.db";
    public static final String TABLE_FAVORITE_NAME = "tblFavorite";
    public static final String TABLE_TRACK_OFFLINE_NAME = "tblTrackOffline";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URI = "uri";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_GENRES = "genres";
    public static final String COLUMN_AVATAR_URL = "avatarurl";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_DOWLOADABLE = "downloadable";
    public static final String COLUMN_DOWNLOAD_URL = "downloadurl";
    public static final String COLUMN_DURATION = "duration";
    private static MusicDB sMusicDB;

    public static MusicDB getInstance(Context context) {
        if (sMusicDB == null) {
            sMusicDB = new MusicDB(context);
        }
        return sMusicDB;
    }

    private MusicDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_LIST_TRACK_FAVORITE = "CREATE TABLE  IF NOT EXISTS "
            + TABLE_FAVORITE_NAME
            + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_URI + " TEXT, "
            + COLUMN_TITLE + " TEXT, "
            + COLUMN_AVATAR_URL + " TEXT, "
            + COLUMN_USERNAME + " TEXT, "
            + COLUMN_DURATION + " TEXT "
            + " )";

    private static final String CREATE_LIST_TRACK_OFFLINE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_TRACK_OFFLINE_NAME
            + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_URI + " TEXT, "
            + COLUMN_TITLE + " TEXT, "
            + COLUMN_AVATAR_URL + " TEXT, "
            + COLUMN_DOWNLOAD_URL + " TEXT, "
            + COLUMN_DOWLOADABLE + " INTEGER, "
            + COLUMN_USERNAME + " TEXT, "
            + COLUMN_DURATION + " TEXT "
            + " )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIST_TRACK_FAVORITE);
        db.execSQL(CREATE_LIST_TRACK_OFFLINE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void insertTrackFavorite(Track track) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, track.getId());
        values.put(COLUMN_URI, track.getUri());
        values.put(COLUMN_TITLE, track.getTitle());
        values.put(COLUMN_AVATAR_URL, track.getAvatarUrl());
        values.put(COLUMN_USERNAME, track.getUsername());
        values.put(COLUMN_DURATION, track.getDuration());
        database.insert(TABLE_FAVORITE_NAME, null, values);
        database.close();
    }

    public void insertTrackOffline(Track track) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, track.getId());
        values.put(COLUMN_URI, track.getUri());
        values.put(COLUMN_TITLE, track.getTitle());
        values.put(COLUMN_AVATAR_URL, track.getAvatarUrl());
        values.put(COLUMN_DOWNLOAD_URL, track.getDownloadUrl());
        values.put(COLUMN_DOWLOADABLE, track.isDownloadable() ? 1 : 0);
        values.put(COLUMN_USERNAME, track.getUsername());
        values.put(COLUMN_DURATION, track.getDuration());
        database.insert(TABLE_TRACK_OFFLINE_NAME, null, values);
        database.close();
    }

    public List<Track> getFavoriteList() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_FAVORITE_NAME, null,
                null, null, null, null, null);
        List<Track> listFavorite = new ArrayList<>();
        while (cursor.moveToNext()) {
            Track favorite = new Track();
            favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            favorite.setUri(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URI)));
            favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
            favorite.setAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR_URL)));
            favorite.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)));
            favorite.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
            listFavorite.add(favorite);
        }
        cursor.close();
        database.close();
        return listFavorite;
    }
}
