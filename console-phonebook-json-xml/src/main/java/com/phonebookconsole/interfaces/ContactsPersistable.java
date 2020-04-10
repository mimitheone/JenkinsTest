package com.phonebookconsole.interfaces;

import com.phonebookconsole.entities.Contact;
import java.util.ArrayList;

public interface ContactsPersistable {

    ArrayList<Contact> GetAll();
    Contact GetById(int id);
    void Add(Contact item);
    void Edit(Contact item);
    void Delete(Contact item);

    ArrayList<Contact> GetAll(int parentId);
}
