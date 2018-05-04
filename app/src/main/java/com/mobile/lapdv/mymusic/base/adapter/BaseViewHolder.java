package com.mobile.lapdv.mymusic.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lap on 02/05/2018.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected T mItem;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView.getRootView());
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public abstract void binData(T t, int position);
}
