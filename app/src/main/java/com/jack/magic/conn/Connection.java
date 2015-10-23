package com.jack.magic.conn;

import java.net.URL;

/**
 * Created by jacktseng on 2015/10/22.
 */
public interface Connection {

    public boolean isEncoding();

    public void enableEncode(boolean enable);

    public void setURL(URL url);

    public URL getURL();

    public void setTimeout(int milliseconds);

    public void setRequestListener(RequestListener listener);

    public void doRequest(Request request);

}
