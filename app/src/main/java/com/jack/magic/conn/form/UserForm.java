package com.jack.magic.conn.form;

/**
 * Created by jacktseng on 2015/10/23.
 */
public class UserForm extends Form {

    public static final String FD_USERNAME = "username";
    public static final String FD_PASSWORD = "password";
    public static final String FD_DEVICE_ID = "device_id";

    public String username;
    public String password;
    public String device_id;

    @Override
    public Object add() {
        return new JSONBuilder()
                .put(FD_USERNAME, username)
                .put(FD_PASSWORD, password)
                .put(FD_DEVICE_ID, device_id)
                .create();
    }

    @Override
    public Object update() {
        return new JSONBuilder()
                .put(FD_USERNAME, username)
                .put(FD_PASSWORD, password)
                .put(FD_DEVICE_ID, device_id)
                .create();
    }

    /**
     * @deprecated Cause user account can't delete itself
     * @return
     */
    @Deprecated
    @Override
    public Object delete() {
        return null;
    }
}
