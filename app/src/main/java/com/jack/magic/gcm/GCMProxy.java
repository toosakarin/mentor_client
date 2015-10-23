package com.jack.magic.gcm;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.jack.magic.gcm.api16down.GCMProxy16;

/**
 * Created by jacktseng on 2015/10/16.
 */
public abstract class GCMProxy {

    public static final String TAG = GCMProxy.class.getSimpleName();

    protected static GCMProxy INSTANCE;

    protected GCMReceiveListener  mReceiveLtn;
    protected GCMErrorListener    mErrorLtn;
    protected GCMRegisterListener  mRegisterLtn;

    public static GCMProxy getInstance() {

        if(INSTANCE == null) {
            int apiLv = Build.VERSION.SDK_INT;
            if (apiLv <= 16) {
                INSTANCE = new GCMProxy16();
            }
            Log.v(TAG, "create GCMPPoxy instance => " + INSTANCE.getClass().toString());
        }

        return INSTANCE;
    }

    public abstract boolean check(Context ctx);

    public abstract boolean isRegistered(Context ctx);

    public abstract String getRegisterId(Context ctx);

    public abstract void register(Context ctx, String senderId);

    public abstract void unregister(Context ctx);

    public void setErrorListener(GCMErrorListener listener) {
        mErrorLtn = listener;
    }

    public void setReceiveListener(GCMReceiveListener listener) {
        mReceiveLtn = listener;
    }

    public void setRegisterListener(GCMRegisterListener listener) {
        mRegisterLtn = listener;
    }

    public GCMErrorListener getErrorListener() {
        return mErrorLtn;
    }

    public GCMReceiveListener getReceiveListener() {
        return mReceiveLtn;
    }

    public GCMRegisterListener getRegisterListener() {
        return mRegisterLtn;
    }

}
