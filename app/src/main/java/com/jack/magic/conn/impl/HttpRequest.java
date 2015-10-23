package com.jack.magic.conn.impl;

import com.google.gson.Gson;
import com.jack.magic.conn.Request;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by jacktseng on 2015/10/23.
 */
public class HttpRequest implements Request {

    private Object mObj;

    public HttpRequest(Object obj) {
        mObj = obj;
    }


    @Override
    public String toStr() {
        String rtn = null;
        if(mObj instanceof Map) {
            rtn = new JSONObject((Map) mObj).toString();
        }
        else if(mObj instanceof JSONObject) {
            rtn = mObj.toString();
        }
        else {
            rtn = new Gson().toJson(mObj);
        }

        return rtn;
    }

    @Override
    public byte[] toBytes() {
        return toStr().getBytes();
    }
}
