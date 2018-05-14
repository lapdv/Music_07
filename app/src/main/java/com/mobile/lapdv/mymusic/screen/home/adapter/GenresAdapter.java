package com.mobile.lapdv.mymusic.screen.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.adapter.BaseRecyclerAdapter;
import com.mobile.lapdv.mymusic.base.adapter.BaseViewHolder;
import com.mobile.lapdv.mymusic.callback.OnRecyclerViewItemClick;
import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.utils.ConfigApi;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.widget.CustomTrackRecyclerView;

/**
 * Created by lap on 07/05/2018.
 */

public class GenresAdapter extends BaseRecyclerAdapter<Genre, GenresAdapter.GenresHolder> {

    public GenresAdapter(Context context) {
        super(context);
    }

    private OnRecyclerViewItemClick<Track> mTrackOnRecyclerViewItemClick;

    public void setTrackOnItemClick(OnRecyclerViewItemClick<Track> trackOnRecyclerViewItemClick) {
        mTrackOnRecyclerViewItemClick = trackOnRecyclerViewItemClick;
    }

    @Override
    public GenresHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        return new GenresHolder(getInflater()
                .inflate(R.layout.item_genres, parent, false),
                mTrackOnRecyclerViewItemClick);
    }

    public static class GenresHolder extends BaseViewHolder<Genre>
            implements OnRecyclerViewItemClick<Track> {

        private TextView mTextGenres;
        private CustomTrackRecyclerView mCustomTrackRecyclerView;
        private OnRecyclerViewItemClick<Track> mTrackOnRecyclerViewItemClick;

        public GenresHolder(@NonNull View itemView, OnRecyclerViewItemClick<Track> listener) {
            super(itemView);
            mCustomTrackRecyclerView =
                    itemView.findViewById(R.id.recyclerview_track);
            mTextGenres = itemView.findViewById(R.id.text_genres);
            mTrackOnRecyclerViewItemClick = listener;
            mCustomTrackRecyclerView.setOnClickItemRecyclerView(this);
        }

        @Override
        public void binData(Genre genresModel, int position) {
            if (EmptyUtils.isNotEmpty(genresModel)) {
                mTextGenres.setText(ConfigApi.LIST_TRACK_GENRES.get(position));
                mCustomTrackRecyclerView.setAdapter(genresModel.getTrackModels());
            }
        }

        @Override
        public void onItemClick(Track track, int position) {
            if (mTrackOnRecyclerViewItemClick != null) {
                mTrackOnRecyclerViewItemClick.onItemClick(track, position);
            }
        }
    }
}
