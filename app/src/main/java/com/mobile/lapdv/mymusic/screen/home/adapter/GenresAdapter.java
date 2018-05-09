package com.mobile.lapdv.mymusic.screen.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.adapter.BaseRecyclerAdapter;
import com.mobile.lapdv.mymusic.base.adapter.BaseViewHolder;
import com.mobile.lapdv.mymusic.data.model.Genre;
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

    @Override
    public GenresHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        return new GenresHolder(getInflater()
                .inflate(R.layout.item_genres, parent, false));
    }

    public static class GenresHolder extends BaseViewHolder<Genre> {

        private TextView mTextGenres;
        private CustomTrackRecyclerView mCustomTrackRecyclerView;

        public GenresHolder(@NonNull View itemView) {
            super(itemView);
            mCustomTrackRecyclerView =
                    itemView.findViewById(R.id.recyclerview_track);
            mTextGenres = itemView.findViewById(R.id.text_genres);
        }

        @Override
        public void binData(Genre genresModel, int position) {
            if (EmptyUtils.isNotEmpty(genresModel)) {
                mTextGenres.setText(ConfigApi.LIST_TRACK_GENRES.get(position));
                //TODO mCustomTrackRecyclerView set list adapter by position
                mCustomTrackRecyclerView.setAdapter(genresModel.getTrackModels());
            }
        }
    }
}
