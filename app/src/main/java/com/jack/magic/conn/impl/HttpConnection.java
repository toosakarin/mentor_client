package com.jack.magic.conn.impl;

import android.util.Base64;
import android.util.Log;

import com.jack.magic.conn.Connection;
import com.jack.magic.conn.Request;
import com.jack.magic.conn.RequestListener;
import com.jack.magic.conn.Response;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jacktseng on 2015/10/22.
 */
public class HttpConnection implements Connection {

    public static final String TAG = HttpConnection.class.getSimpleName();

    private boolean mEnableEncode;

    private URL mURL;

    private String mHttpMethod = "POST";

    private int mTimeout = 5000;

    private RequestListener mRequestListener;

    private String mCookie;

    public HttpConnection() {

    }

    public HttpConnection(URL url) {
        mURL = url;
    }


    public void setHttpMethod(String method) {
        if(!method.equals("GET") && !method.equals("POST"))
            return;
        mHttpMethod = method;
    }

    public void setCookie(String cookie) {
        mCookie = cookie;
    }

    @Override
    public boolean isEncoding() {
        return mEnableEncode;
    }

    @Override
    public void enableEncode(boolean enable) {
        mEnableEncode = enable;
    }

    @Override
    public void setURL(URL url) {
        mURL = url;
    }

    @Override
    public URL getURL() {
        return mURL;
    }

    @Override
    public void setTimeout(int milliseconds) {
        if(mTimeout < 0 ) return;
        mTimeout = milliseconds;
    }

    @Override
    public void setRequestListener(RequestListener listener) {
        mRequestListener = listener;
    }

    @Override
    public void doRequest(Request request) {
        if(mURL == null) {
            Log.e(TAG, "not found any url can be visited");
            return;
        }

        String requestData = request.toStr();
        if(isEncoding()) {
            requestData = Base64.encodeToString(requestData.getBytes(), Base64.DEFAULT);
        }
        Log.d(TAG, "json=" + requestData);

        try {
            HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
            conn.setConnectTimeout(mTimeout);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod(mHttpMethod);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            /**
             * If has cookie, set it to the http header
             */
            if(mCookie != null)
                conn.setRequestProperty("Cookie", mCookie);

            OutputStream out = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(requestData);
            writer.flush();
            writer.close();
            out.close();


            /**
             * Handles the response of server for requirement
             */
            int responseCode = conn.getResponseCode();
            Log.i(TAG, "responseCode=" + responseCode);

            HttpResponse response = new HttpResponse();
            response.statusCode = responseCode;

            if(responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String responseData = null;
                while ((responseData = in.readLine()) != null) {
                    sb.append(responseData);
                    Log.v(TAG, "readLine=" + responseData);
                }
                in.close();
                Log.e(TAG, "response=" + sb.toString());

                /**
                 * Tries to get the cookie from server
                 */
                String cookie = conn.getHeaderField("Set-Cookie");
                Log.e(TAG, "cookie=" + cookie);

                response.response = new JSONObject(sb.toString());
                response.cookie = cookie;
            }

            /**
             * Callbacks to listener
             */
            if(mRequestListener != null)
                mRequestListener.onResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class HttpResponse implements Response<String> {

        private int statusCode;
        private String cookie;
        private Request request;
        private JSONObject response;

        @Override
        public Request getRequest() {
            return request;
        }

        @Override
        public int getStatusCode() {
            return statusCode;
        }

        @Override
        public String getExtraData() {
            if(response == null) return null;
            return response.toString();
        }

        @Override
        public String getData(String name) {
            if(name == null || name.isEmpty())
                return null;

            String rtn = null;
            if(name.equals("cookie")) {
                rtn = cookie;
            }
            else {
                try {
                    rtn = response.getString(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return rtn;
        }
    }
}
