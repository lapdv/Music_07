package com.mobile.lapdv.mymusic.screen.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.adapter.BaseRecyclerAdapter;
import com.mobile.lapdv.mymusic.base.adapter.BaseViewHolder;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.utils.GlideUtils;
import com.mobile.lapdv.mymusic.widget.SquareImageView;

/**
 * Created by lap on 07/05/2018.
 */

public class TrackAdapter extends BaseRecyclerAdapter<Track, TrackAdapter.TrackHolder> {

    public TrackAdapter(Context context) {
        super(context);
    }

    @Override
    public TrackHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.item_track, parent, false);
        return new TrackHolder(view);
    }

    public static class TrackHolder extends BaseViewHolder<Track> {

        private SquareImageView mImageTrack;
        private TextView mTitleTrack, mUserTrack;

        public TrackHolder(@NonNull View itemView) {
            super(itemView);
            mImageTrack = itemView.findViewById(R.id.image_track);
            mTitleTrack = itemView.findViewById(R.id.text_track_title);
            mUserTrack = itemView.findViewById(R.id.text_track_user);
        }

        @Override
        public void binData(Track trackModel, int position) {
            if (EmptyUtils.isNotEmpty(trackModel)) {
                GlideUtils
                        .loadImage(trackModel.getAvatarUrl(),
                                mImageTrack);
                mTitleTrack.setText(trackModel.getTitle());
                mUserTrack.setText(trackModel.getUsername());
            }
        }
    }
}
