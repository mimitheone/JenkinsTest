package com.phonebookconsole;

import com.phonebookconsole.services.AuthenticationServices;
import com.phonebookconsole.tools.ConsoleManager;
import com.phonebookconsole.views.AdministrationView;
import com.phonebookconsole.views.AuthenticationView;
import com.phonebookconsole.views.ContactsManagementView;

public class Application {

    public static void main(String args[]) {

        AuthenticationView authView = new AuthenticationView();
        authView.Run();

        if (AuthenticationServices.getInstance().getLoggedUser() != null) {

            if (AuthenticationServices.getInstance().getLoggedUser().getIsAdmin()) {
                AdministrationView adminView = new AdministrationView();
                adminView.Run();
            } else {
                ContactsManagementView view = new ContactsManagementView();
                view.Run();
            }

            ConsoleManager.Clear();
            ConsoleManager.WriteLine("Good bye");
        }
    }
}
