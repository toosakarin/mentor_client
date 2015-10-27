package com.jack.magic.conn.impl;

import com.jack.magic.conn.Request;
import com.jack.magic.conn.RequestListener;
import com.jack.magic.conn.Response;
import com.jack.magic.conn.ServiceClient;
import com.jack.magic.conn.form.DiscussionForm;
import com.jack.magic.conn.form.LoginForm;
import com.jack.magic.conn.form.UserForm;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jacktseng on 2015/10/23.
 */
public class ServiceClientImpl implements ServiceClient {

    public static final String TAG = ServiceClientImpl.class.getSimpleName();

    private URL mURL;
    private String mCookie;


    private HttpConnection openConnection() {
        if(mURL == null) return null;

        HttpConnection conn = null;
        try {
            conn = new HttpConnection();
            conn.setURL(mURL);
            conn.enableEncode(true);
            conn.setRequestListener(new RequestListener<String>() {
                @Override
                public void onResponse(Response<String> response) {
                    if (response != null)
                        mCookie = response.getData("cookie");
                }
            });
        } catch (Exception e ) {
            e.printStackTrace();
        }

        return conn;
    }

    private void doRequest(Object requestBody) {
        if(requestBody == null) return;

        Request request  = new HttpRequest(requestBody);
        HttpConnection conn = openConnection();
        conn.doRequest(request);
    }

    @Override
    public void setURL(String url) {
        if(url == null || url.isEmpty()) return;

        try {
            mURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setURL(URL url) {
        mURL = url;
    }

    @Override
    public String getURL() {
        return mURL.toString();
    }

    @Override
    public void register(UserForm form) {

    }

    @Override
    public void login(LoginForm form) {
        if(form == null) return;
        doRequest(form.add());
    }

    @Override
    public void updateAccount(UserForm form) {

    }

    @Override
    public void postDiscussion(DiscussionForm form) {

    }

    @Override
    public void editDiscussion(DiscussionForm form) {

    }

    @Override
    public void deleteDiscussion(DiscussionForm form) {

    }
}
