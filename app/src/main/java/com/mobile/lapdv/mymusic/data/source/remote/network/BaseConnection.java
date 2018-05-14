package com.mobile.lapdv.mymusic.data.source.remote.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import javax.net.ssl.SSLHandshakeException;

/**
 * Created by lap on 08/05/2018.
 */

public abstract class BaseConnection {

    protected static final int CONNECT_TIMEOUT = 5 * 1000;
    protected static final int DEFAULT_READ_TIMEOUT = 10 * 1000;
    protected static final String HTTP_REQ_PROPERTY_CHARSET = "Accept-Charset";
    protected static final String HTTP_REQ_VALUE_CHARSET = "UTF-8";
    protected static final String HTTP_REQ_PROPERTY_CONTENT_TYPE = "Content-Type";
    protected static final String HTTP_REQ_VALUE_CONTENT_TYPE = "application/x-www-form-urlencoded";
    protected static final String HTTP_REQ_METHOD_GET = "GET";

    protected abstract HttpURLConnection getURLConnection();

    protected void setURLConnectionCommonParam() {
        HttpURLConnection connection = getURLConnection();
        if (null == connection) {
            return;
        }
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(DEFAULT_READ_TIMEOUT);
        connection.setRequestProperty(HTTP_REQ_PROPERTY_CHARSET, HTTP_REQ_VALUE_CHARSET);
        connection.setRequestProperty(HTTP_REQ_PROPERTY_CONTENT_TYPE, HTTP_REQ_VALUE_CONTENT_TYPE);
        connection.setUseCaches(false);
    }

    protected String doGetRequest() {
        String result = "";
        InputStream is = null;
        BufferedReader br = null;
        try {
            HttpURLConnection connection = getURLConnection();
            if (null == connection) {
                return "";
            }
            connection.setRequestMethod(HTTP_REQ_METHOD_GET);
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, HTTP_REQ_VALUE_CHARSET));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (SSLHandshakeException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public String getResponseMessage() {
        try {
            HttpURLConnection connection = getURLConnection();
            return null == connection ? "" : getURLConnection().getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public int getResponseCode() {
        try {
            HttpURLConnection connection = getURLConnection();
            return null == connection ? -1 : getURLConnection().getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
