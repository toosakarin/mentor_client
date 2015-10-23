package com.jack.magic.test;

import android.content.Context;

/**
 * Created by jacktseng on 2015/10/8.
 */
public interface Test {

    public String SERV_IP = "192.168.30.91";
    public String SERV_HOST = "http://" + SERV_IP + "/service_magic/";

    public void test(Context ctx);

}
