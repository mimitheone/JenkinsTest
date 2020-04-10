package com.phonebookconsole.views;

import com.phonebookconsole.tools.ConsoleManager;

public class AdministrationView {

    private UsersManagementView usersView;
    private ContactsManagementView contactsView;

    public AdministrationView() {
        usersView = new UsersManagementView();
        contactsView = new ContactsManagementView();
    }

    public void Run() {

        while (true) {
            ConsoleManager.Clear();

            ConsoleManager.WriteLine("[U]ser Management");
            ConsoleManager.WriteLine("[C]ontacts Management");
            ConsoleManager.WriteLine("E[x]it");

            ConsoleManager.Write(">");
            String choice = ConsoleManager.ReadLine();

            switch (choice.toUpperCase()) {
                case "U": {
                    UsersManagement();
                    break;
                }
                case "C": {
                    ContactsManagement();
                    break;
                }
                case "X": {
                    return;
                }
                default: {
                    ConsoleManager.Clear();
                    ConsoleManager.WriteLine("Invalid choice!");
                    ConsoleManager.WriteLine("Press [Enter] to continue");
                    ConsoleManager.ReadLine();
                    break;
                }
            }
        }
    }

    private void UsersManagement() {

        usersView.Run();
    }

    private void ContactsManagement() {

        contactsView.Run();
    }
}
