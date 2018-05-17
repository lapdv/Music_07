package com.mobile.lapdv.mymusic.callback;

import java.util.List;

/**
 * Created by lap on 15/05/2018.
 */

public interface OnGetListDataListener<T> {
    void onItemClick(List<T> list, int position);
}
