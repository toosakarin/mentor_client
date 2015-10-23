package com.jack.magic.gcm.api16down;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.jack.magic.gcm.GCMProxy;
import com.jack.magic.gcm.GCMService;

/**
 * Created by jacktseng on 2015/10/15.
 */
public class GCMIntentService extends GCMBaseIntentService implements GCMService {

    public static final String TAG = GCMIntentService.class.getPackage().getName() + "/"
            + GCMIntentService.class.getSimpleName();

    public static final String SENDER_ID = "107339576763";

    private GCMProxy mProxy;

    public GCMIntentService() {
        super(SENDER_ID);
        mProxy = GCMProxy.getInstance();
    }

    @Override
    public void setProxy(GCMProxy proxy) {
        mProxy = proxy;
    }

    @Override
    public GCMProxy getProxy() {
        return mProxy;
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        String author = intent.getStringExtra("author");
        String title = intent.getStringExtra("content");

        Log.d(TAG, "onMessage()->message from author=" + author + " title=" + title);
        if(mProxy != null && mProxy.getReceiveListener() != null)
            mProxy.getReceiveListener().onMessage(context, intent);

    }

    @Override
    protected void onError(Context context, String s) {
        Log.e(TAG, "onError()");
        if(mProxy != null && mProxy.getErrorListener() != null)
            mProxy.getErrorListener().onError(context, s);
    }

    @Override
    protected void onRegistered(Context context, String s) {
        Log.d(TAG, "onRegistered()->s=" + s);
        if(mProxy != null && mProxy.getRegisterListener() != null)
            mProxy.getRegisterListener().onRegistered(context, s);
    }

    @Override
    protected void onUnregistered(Context context, String s) {
        Log.d(TAG, "onUnregistered()");
        if(mProxy != null && mProxy.getRegisterListener() != null)
            mProxy.getRegisterListener().onUnregistered(context, s);
    }
}
