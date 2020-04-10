package com.phonebookconsole.tools.xmlHelpers;

import com.phonebookconsole.entities.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneList {

    private List<Phone> list;

    public PhoneList() {
        this.list = new ArrayList<Phone>();
    }

    public void add(Phone i) {
        this.list.add(i);
    }

    public Phone get(int i) {
        return this.list.get(i);
    }

    public void remove(int i) {
        this.list.remove(i);
    }

    public List<Phone> getList() {
        return list;
    }

    public void setList(List<Phone> list) {
        this.list = list;
    }
}
