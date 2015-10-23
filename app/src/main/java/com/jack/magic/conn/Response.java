package com.jack.magic.conn;

/**
 * Created by jacktseng on 2015/9/24.
 */
public interface Response<T> {

    public Request getRequest();

    public int getStatusCode();

    public T getExtraData();

    public T getData(String name);

//    public void onReceive(Map<K,T> data);

}
