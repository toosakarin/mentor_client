package com.jack.magic.gcm;

import android.content.Context;
import android.content.Intent;

/**
 * Created by jacktseng on 2015/10/16.
 */
public interface GCMReceiveListener {

    public void onMessage(Context ctx, Intent intent);


}
