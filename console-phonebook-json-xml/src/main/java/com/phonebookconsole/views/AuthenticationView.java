package com.phonebookconsole.views;

import com.phonebookconsole.services.AuthenticationServices;
import com.phonebookconsole.tools.ConsoleManager;

public class AuthenticationView {

    public void Run() {

        while(AuthenticationServices.getInstance().getLoggedUser() == null) {

            ConsoleManager.Clear();

            ConsoleManager.Write("Username: ");
            String username = ConsoleManager.ReadLine();

            ConsoleManager.Write("Password: ");
            String password = ConsoleManager.ReadLine();

            AuthenticationServices.getInstance().AuthenticateUser(username, password);
        }
    }
}
