package com.phonebookconsole.views;

import com.phonebookconsole.entities.Contact;
import com.phonebookconsole.entities.Phone;
import com.phonebookconsole.interfaces.PhonesPersistable;
import com.phonebookconsole.repositories.json.PhonesRepositoryJson;
import com.phonebookconsole.tools.ConsoleManager;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class PhonesManagementView {

    private PhonesPersistable phoneRepo;
    private Contact parent;

    public PhonesManagementView(Contact contact) {
        phoneRepo = new PhonesRepositoryJson();
        parent = contact;
    }

    public void Run() {

        while(true) {
            MenuEnumeration choice = RenderMenu();

            switch(choice){

                case List: {
                    List();
                    break;
                }
                case Add: {
                    Add();
                    break;
                }
                case Edit: {
                    Edit();
                    break;
                }
                case Delete: {
                    Delete();
                    break;
                }
                case View: {
                    View();
                    break;
                }
                case Exit: {
                    return;
                }
            }
        }
    }

    private MenuEnumeration RenderMenu() {

        while(true){

            ConsoleManager.Clear();

            ConsoleManager.WriteLine("Contact Details:");
            ConsoleManager.Write("First Name: ");
            ConsoleManager.WriteLine(parent.getFirstName());
            ConsoleManager.Write("Last Name: ");
            ConsoleManager.WriteLine(parent.getLastName());
            ConsoleManager.Write("Email: ");
            ConsoleManager.WriteLine(parent.getEmail());
            ConsoleManager.WriteLine();

            ArrayList<Phone> phones = phoneRepo.GetAll(parent.getId());
            for(int i=0;i<phones.size();i++) {

                Phone phone = phones.get(i);
                ConsoleManager.WriteLine(phone.getPhone());
            }

            ConsoleManager.WriteLine("\n###############################\n");

            ConsoleManager.WriteLine("[L]ist Phones");
            ConsoleManager.WriteLine("[A]dd Phone");
            ConsoleManager.WriteLine("[E]dit Phone");
            ConsoleManager.WriteLine("[D]elete Phone");
            ConsoleManager.WriteLine("[V]iew Phone");
            ConsoleManager.WriteLine("E[x]it");

            ConsoleManager.Write(">");
            String choice = ConsoleManager.ReadLine();

            switch(choice.toUpperCase()){
                case "L":{
                    return MenuEnumeration.List;
                }
                case "A":{
                    return MenuEnumeration.Add;
                }
                case "E":{
                    return MenuEnumeration.Edit;
                }
                case "D":{
                    return MenuEnumeration.Delete;
                }
                case "V":{
                    return MenuEnumeration.View;
                }
                case "X":{
                    return MenuEnumeration.Exit;
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

    private void Add() {

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("####Add Phone####");

        Phone item = new Phone();
        item.setContactId(parent.getId());

        ConsoleManager.Write("Phone: ");
        item.setPhone(ConsoleManager.ReadLine());

        phoneRepo.Add(item);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item added successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void List() {

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("####List Phones####");

        ArrayList<Phone> phones = phoneRepo.GetAll(parent.getId());

        for(int i=0;i<phones.size();i++) {

            Phone phone = phones.get(i);

            ConsoleManager.Write("ID: ");
            ConsoleManager.WriteLine(phone.getId());
            ConsoleManager.Write("Phone: ");
            ConsoleManager.WriteLine(phone.getPhone());
            ConsoleManager.WriteLine("---------------------------------");
        }

        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void Edit() {

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("####Edit Phone####");

        ArrayList<Phone> phones = phoneRepo.GetAll(this.parent.getId());

        for(int i=0;i<phones.size();i++) {

            Phone phone = phones.get(i);

            ConsoleManager.Write(phone.getPhone() + " ( " + phone.getId() + " )\t");

            if (i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of phone: ");
        int id = Integer.parseInt(ConsoleManager.ReadLine());

        Phone phone = phoneRepo.GetById(id);

        ConsoleManager.Write("Phone: (" + phone.getPhone() + " ): ");
        phone.setPhone(ConsoleManager.ReadLine());

        phoneRepo.Edit(phone);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item updated successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void Delete() {

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("####Delete Phone####");

        ArrayList<Phone> phones = phoneRepo.GetAll(this.parent.getId());

        for(int i=0;i<phones.size();i++) {

            Phone phone = phones.get(i);

            ConsoleManager.Write(phone.getPhone() + " ( " + phone.getId() + " )\t");

            if (i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of phone: ");
        int id = Integer.parseInt(ConsoleManager.ReadLine());

        Phone phone = phoneRepo.GetById(id);
        phoneRepo.Delete(phone);

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("Item deleted successfully");
        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }

    private void View() {

        ConsoleManager.Clear();
        ConsoleManager.WriteLine("####View Phone####");

        ArrayList<Phone> phones = phoneRepo.GetAll(this.parent.getId());

        for(int i=0;i<phones.size();i++) {

            Phone phone = phones.get(i);

            ConsoleManager.Write(phone.getPhone() + " ( " + phone.getId() + " )\t");

            if (i > 0 && i % 5 == 0)
                ConsoleManager.WriteLine();
        }

        ConsoleManager.WriteLine();
        ConsoleManager.Write("Enter ID of phone: ");
        int id = Integer.parseInt(ConsoleManager.ReadLine());

        Phone phone = phoneRepo.GetById(id);

        ConsoleManager.Clear();
        ConsoleManager.Write("ID: ");
        ConsoleManager.WriteLine(phone.getId());
        ConsoleManager.Write("Phone: ");
        ConsoleManager.WriteLine(phone.getPhone());

        ConsoleManager.WriteLine("Press [Enter] to continue");
        ConsoleManager.ReadLine();
    }
}

