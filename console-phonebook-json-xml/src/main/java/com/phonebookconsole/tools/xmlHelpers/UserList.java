package com.phonebookconsole.tools.xmlHelpers;

import com.phonebookconsole.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    private List<User> list;

    public UserList() {

        list = new ArrayList<User>();
    }

    public void add(User i) {

        list.add(i);
    }

    public User get(int i) {
        return this.list.get(i);
    }

    public void remove(int i) {
        this.list.remove(i);
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
