package com.mobile.lapdv.mymusic.screen.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.adapter.BaseRecyclerAdapter;
import com.mobile.lapdv.mymusic.base.adapter.BaseViewHolder;
import com.mobile.lapdv.mymusic.callback.OnGetListDataListener;
import com.mobile.lapdv.mymusic.callback.OnRecyclerViewItemClick;
import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.screen.track.TrackMoreActivity;
import com.mobile.lapdv.mymusic.utils.ConfigApi;
import com.mobile.lapdv.mymusic.utils.Constant;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.widget.CustomTrackRecyclerView;

import java.util.List;

/**
 * Created by lap on 07/05/2018.
 */

public class GenresAdapter extends BaseRecyclerAdapter<Genre, GenresAdapter.GenresHolder> {

    public GenresAdapter(Context context) {
        super(context);
    }

    private OnGetListDataListener<Track> mTrackOnRecyclerViewItemClick;

    public void setTrackOnItemClick(OnGetListDataListener<Track> trackOnRecyclerViewItemClick) {
        mTrackOnRecyclerViewItemClick = trackOnRecyclerViewItemClick;
    }

    @Override
    public GenresHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        return new GenresHolder(getInflater()
                .inflate(R.layout.item_genres, parent, false),
                mTrackOnRecyclerViewItemClick);
    }

    public static class GenresHolder extends BaseViewHolder<Genre>
            implements OnGetListDataListener<Track>, View.OnClickListener {

        private ImageView mImageMore;
        private TextView mTextGenres;
        private CustomTrackRecyclerView mCustomTrackRecyclerView;
        private OnGetListDataListener<Track> mTrackOnGetListDataListener;
        private Genre mGenre;

        public GenresHolder(@NonNull View itemView, OnGetListDataListener<Track> listener) {
            super(itemView);
            mCustomTrackRecyclerView = itemView.findViewById(R.id.recyclerview_track);
            mTextGenres = itemView.findViewById(R.id.text_genres);
            mImageMore = itemView.findViewById(R.id.image_more);
            mTrackOnGetListDataListener = listener;
            mCustomTrackRecyclerView.setOnClickItemRecyclerView(this);
            mImageMore.setOnClickListener(this);
        }

        @Override
        public void binData(Genre genresModel, int position) {
            if (EmptyUtils.isNotEmpty(genresModel)) {
                mGenre = genresModel;
                mGenre.setPosition(position);
                mGenre.setType(ConfigApi.LIST_TRACK_GENRES.get(position));
                mTextGenres.setText(ConfigApi.LIST_TRACK_GENRES.get(position));
                mCustomTrackRecyclerView.setAdapter(genresModel.getTrackModels());
            }
        }

        @Override
        public void onItemClick(List<Track> trackList, int position) {
            if (mTrackOnGetListDataListener != null) {
                mTrackOnGetListDataListener.onItemClick(trackList, position);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_more:
                    Intent intent = new Intent(view.getContext(), TrackMoreActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.BUNDLE_GENRE, mGenre);
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);
                    break;
            }
        }
    }
}
