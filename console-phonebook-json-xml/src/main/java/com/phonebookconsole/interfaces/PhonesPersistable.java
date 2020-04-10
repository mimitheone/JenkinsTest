package com.phonebookconsole.interfaces;

import com.phonebookconsole.entities.Phone;
import java.util.ArrayList;

public interface PhonesPersistable {

    ArrayList<Phone> GetAll();
    Phone GetById(int id);
    void Add(Phone item);
    void Edit(Phone item);
    void Delete(Phone item);

    ArrayList<Phone> GetAll(int parentId);
}
