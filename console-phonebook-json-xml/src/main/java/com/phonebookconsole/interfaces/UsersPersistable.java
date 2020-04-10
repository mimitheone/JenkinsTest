package com.phonebookconsole.interfaces;

import com.phonebookconsole.entities.User;
import java.util.ArrayList;

public interface UsersPersistable {

    ArrayList<User> GetAll();
    User GetById(int id);
    void Add(User item);
    void Edit(User item);
    void Delete(User item);

    User GetByUsernameAndPassword(String username, String password);
}
