package com.phonebookconsole.services;

import com.phonebookconsole.interfaces.UsersPersistable;
import com.phonebookconsole.entities.User;
import com.phonebookconsole.repositories.json.UsersRepositoryJson;

public class AuthenticationServices {

    private static AuthenticationServices instance = null;
    public static AuthenticationServices getInstance() {

        if (instance == null)
            instance = new AuthenticationServices();

        return instance;
    }

    private User authenticatedUser = null;
    private UsersPersistable repository;

    private AuthenticationServices() {
        repository = new UsersRepositoryJson();
    }

    public User getLoggedUser() {
        return authenticatedUser;
    }
    public void AuthenticateUser(String username, String password) {

        this.authenticatedUser = repository.GetByUsernameAndPassword(username, password);
    }
}
