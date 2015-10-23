package com.jack.magic.gcm;

import android.content.Context;

/**
 * Created by jacktseng on 2015/10/16.
 */
public interface GCMRegisterListener {

    public void onRegistered(Context ctx, String s);

    public void onUnregistered(Context ctx, String s);

}
