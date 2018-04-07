package com.cmp.portal.user.model.res;

import com.cmp.portal.user.model.User;

public class ResUser {

    private User user;

    public ResUser() {
    }

    public ResUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
