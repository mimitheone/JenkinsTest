package com.phonebookconsole.tools.xmlHelpers;

import com.phonebookconsole.entities.Contact;
import com.phonebookconsole.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ContactList {

    private List<Contact> list;

    public ContactList() {
        this.list = new ArrayList<Contact>();
    }

    public void add(Contact i){
        this.list.add(i);
    }

    public Contact get(int i) {
        return this.list.get(i);
    }

    public void remove(int i) {
        this.list.remove(i);
    }

    public List<Contact> getList() {
        return list;
    }

    public void setList(List<Contact> list) {
        this.list = list;
    }
}
