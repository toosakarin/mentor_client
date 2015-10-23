package com.jack.magic.conn.form;

/**
 * Created by jacktseng on 2015/10/23.
 */
public class DiscussionForm extends Form {

    public static final String FD_USER = "user";
    public static final String FD_TITLE = "title";
    public static final String FD_DECKS = "decks";
    public static final String FD_CONTENT = "content";
    public static final String FD_DATE = "date";

    public String user;
    public String title;
    public int[] decks;
    public String content;
    public String date;


    @Override
    public Object add() {
        return new Form.JSONBuilder()
                .put(FD_USER, user)
                .put(FD_TITLE, title)
                .put(FD_DECKS, decks)
                .put(FD_CONTENT, content)
                .create();
    }

    @Override
    public Object update() {
        return new Form.JSONBuilder()
                .put(FD_USER, user)
                .put(FD_TITLE, title)
                .put(FD_DECKS, decks)
                .put(FD_CONTENT, content)
                .create();
    }

    @Override
    public Object delete() {
        return new JSONBuilder()
                .put(FD_USER, user)
                .put(FD_TITLE, title)
                .create();
    }

}
