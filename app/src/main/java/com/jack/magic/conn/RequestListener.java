package com.jack.magic.conn;

/**
 * Created by jacktseng on 2015/10/22.
 */
public interface RequestListener<T> {

    public void onResponse(Response<T> response);

}
