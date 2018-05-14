package com.mobile.lapdv.mymusic.screen.favourite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.adapter.BaseRecyclerAdapter;
import com.mobile.lapdv.mymusic.base.adapter.BaseViewHolder;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.utils.GlideUtils;
import com.mobile.lapdv.mymusic.widget.SquareImageView;

/**
 * Created by lap on 14/05/2018.
 */

public class FavoriteAdapter extends BaseRecyclerAdapter<Track, FavoriteAdapter.FavoriteHolder> {

    public FavoriteAdapter(Context context) {
        super(context);
    }

    @Override
    public FavoriteHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.item_favorite, parent, false);
        return new FavoriteHolder(view);
    }

    public static class FavoriteHolder extends BaseViewHolder<Track> {
        private ImageView mImageTrack, mImageMore;
        private TextView mTitleTrack, mUserTrack;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            mImageTrack = itemView.findViewById(R.id.image_track);
            mImageMore = itemView.findViewById(R.id.image_more);
            mTitleTrack = itemView.findViewById(R.id.text_track_title);
            mUserTrack = itemView.findViewById(R.id.text_track_user);
        }

        @Override
        public void binData(Track track, int position) {
            if (EmptyUtils.isNotEmpty(track)) {
                GlideUtils
                        .loadImage(track.getAvatarUrl(),
                                mImageTrack);
                mTitleTrack.setText(track.getTitle());
                mUserTrack.setText(track.getUsername());
            }
        }
    }
}
