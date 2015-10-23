package com.jack.magic.conn.form;

import org.json.JSONObject;

/**
 * Created by jacktseng on 2015/10/23.
 */
public abstract class Form {

    public abstract Object add();

    public abstract Object update();

    public abstract  Object delete();

    public static class JSONBuilder {

        private JSONObject json = new JSONObject();

        public JSONBuilder put(String tag, Object value) {
            try {
                json.put(tag, value);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return this;
        }

        public JSONObject create() {
            return json;
        }

    }

}
