package com.jack.magic.gcm.api16down;

import android.content.Context;

import com.google.android.gcm.GCMRegistrar;
import com.jack.magic.gcm.GCMProxy;

/**
 * Created by jacktseng on 2015/10/16.
 */
public class GCMProxy16 extends GCMProxy {
    public static final String TAG = GCMProxy16.class.getSimpleName();

    @Override
    public boolean check(Context ctx) {
        try {
            GCMRegistrar.checkDevice(ctx);
            GCMRegistrar.checkManifest(ctx);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean isRegistered(Context ctx) {
        return GCMRegistrar.isRegistered(ctx);
    }

    @Override
    public String getRegisterId(Context ctx) {
        return GCMRegistrar.getRegistrationId(ctx);
    }

    @Override
    public void register(Context ctx, String senderId) {
        if(ctx == null || senderId == null)
            return;
        GCMRegistrar.register(ctx, senderId);
    }

    @Override
    public void unregister(Context ctx) {
        GCMRegistrar.unregister(ctx);
    }


}
