package com.mobile.lapdv.mymusic.data.source.remote.network;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lap on 08/05/2018.
 */

public class HTTPConnection extends BaseConnection {

    private HttpURLConnection mHttpURLConnection = null;

    public HTTPConnection(String url) {
        super();
        try {
            mHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected HttpURLConnection getURLConnection() {
        return mHttpURLConnection;
    }
}

