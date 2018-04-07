package com.cmp.portal.user.model.res;

import com.cmp.portal.user.model.User;

import java.util.List;

public class ResUsers {

    private List<User> users;

    public ResUsers() {
    }

    public ResUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
