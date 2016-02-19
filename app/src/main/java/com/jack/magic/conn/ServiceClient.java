package com.jack.magic.conn;

import com.jack.magic.conn.form.DiscussionForm;
import com.jack.magic.conn.form.LoginForm;
import com.jack.magic.conn.form.UserForm;

/**
 * Created by jacktseng on 2015/10/23.
 */
public interface ServiceClient {

    public void register(UserForm form);

    public void login(LoginForm form);

    public void updateAccount(UserForm form);

    public void postDiscussion(DiscussionForm form);

    public void editDiscussion(DiscussionForm form);

    public void deleteDiscussion(DiscussionForm form);



}
