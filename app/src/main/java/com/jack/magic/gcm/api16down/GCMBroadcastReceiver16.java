package com.jack.magic.gcm.api16down;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

import java.lang.reflect.Method;

/**
 * Created by jacktseng on 2015/10/19.
 */
public class GCMBroadcastReceiver16 extends BroadcastReceiver {

    private static final String TAG = GCMBroadcastReceiver16.class.getSimpleName();
    private static boolean mReceiverSet = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "onReceive: " + intent.getAction());

        if (!mReceiverSet) {
            mReceiverSet = true;
            String myClass = getClass().getName();
            if (!myClass.equals(GCMBroadcastReceiver16.class.getName())) {
                try {
                    String mName = "setRetryReceiverClassName";
                    Method method = GCMRegistrar.class.getDeclaredMethod(mName, String.class);
                    method.setAccessible(true);
                    method.invoke(null, myClass);
                    Log.d(TAG, "reflection to GCMRegister." + mName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String className = getGCMIntentServiceClassName(context);
        Log.v(TAG, "GCM IntentService class: " + className);

        try {
            String mName = "runIntentInService";
            Method method =
                    GCMBaseIntentService.class.getDeclaredMethod(mName,
                            Context.class, Intent.class, String.class);
            method.setAccessible(true);
            method.invoke(null, context, intent, className);
            Log.d(TAG, "reflection to GCMBaseIntentService." + mName);
        } catch (Exception e ) {
            e.printStackTrace();
        }

        setResult(-1, null, null);
    }


    protected String getGCMIntentServiceClassName(Context context)
    {
        return getDefaultIntentServiceClassName(context);
    }

    static final String getDefaultIntentServiceClassName(Context context) {
        String className = GCMBroadcastReceiver16.class.getPackage().getName() + ".GCMIntentService";

        return className;
    }
}
