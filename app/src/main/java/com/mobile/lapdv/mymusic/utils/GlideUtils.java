package com.mobile.lapdv.mymusic.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mobile.lapdv.mymusic.R;

/**
 * Created by lap on 07/05/2018.
 */

public class GlideUtils {

    public static void loadImage(String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().error(R.drawable.ic_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }

    public static void loadImage(String url, ImageView imageView, boolean checkError) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (checkError) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(imageView);
        } else {
            loadImage(url, imageView);
        }
    }

    public static void loadResource(int resource, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(resource)
                .into(imageView);
    }
}
