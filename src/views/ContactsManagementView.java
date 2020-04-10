package views;

import java.io.*;
import java.util.*;

import entities.*;
import repositories.*;
import services.*;
import tools.*;

public class ContactsManagementView {
	
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
			
			ConsoleManager.WriteLine("[L]ist Contacts");
			ConsoleManager.WriteLine("[A]dd Contact");
			ConsoleManager.WriteLine("[E]dit Contact");
			ConsoleManager.WriteLine("[D]elete Contact");
			ConsoleManager.WriteLine("[V]iew Contact");
			ConsoleManager.WriteLine("E[x]it");
			
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
		ConsoleManager.WriteLine("####Add Contact####");
				
		Contact item = new Contact();
		
		item.setUserId(AuthenticationServices.getInstance().getLoggedUser().getId());
		
		ConsoleManager.Write("First Name: ");
		item.setFirstName(ConsoleManager.ReadLine());
		
		ConsoleManager.Write("Last Name: ");
		item.setLastName(ConsoleManager.ReadLine());
		
		ConsoleManager.Write("Email: ");
		item.setEmail(ConsoleManager.ReadLine());
		
		ContactsRepository ContactRepo = new ContactsRepository("contacts.txt");
		ContactRepo.Add(item);
		
		ConsoleManager.Clear();
		ConsoleManager.WriteLine("Item added successfully");
		ConsoleManager.WriteLine("Press [Enter] to continue");
		ConsoleManager.ReadLine();
	}
	
	private void List() {
		
		ConsoleManager.Clear();
		ConsoleManager.WriteLine("####List Contacts####");
		
		ContactsRepository contactRepo = new ContactsRepository("contacts.txt");
		ArrayList<Contact> contacts = contactRepo.GetAll(AuthenticationServices.getInstance().getLoggedUser().getId());
		
		for(int i=0;i<contacts.size();i++) {
			
			Contact contact = contacts.get(i);
			
			ConsoleManager.Write("ID: ");
			ConsoleManager.WriteLine(contact.getId());
			ConsoleManager.Write("First Name: ");
			ConsoleManager.WriteLine(contact.getFirstName());
			ConsoleManager.Write("Last Name: ");
			ConsoleManager.WriteLine(contact.getLastName());
			ConsoleManager.Write("Email: ");
			ConsoleManager.WriteLine(contact.getEmail());
			ConsoleManager.WriteLine("---------------------------------");
		}
		
		ConsoleManager.WriteLine("Press [Enter] to continue");
		ConsoleManager.ReadLine();
	}
	
	private void Edit() {
		
		ConsoleManager.Clear();
		ConsoleManager.WriteLine("####Edit Contact####");
		
		ContactsRepository contactRepo = new ContactsRepository("contacts.txt");
		ArrayList<Contact> contacts = contactRepo.GetAll();
		
		for(int i=0;i<contacts.size();i++) {
			
			Contact contact = contacts.get(i);
			
			ConsoleManager.Write(contact.getFirstName() + " " + contact.getLastName() + " ( " + contact.getId() + " )\t");
			
			if (i > 0 && i % 5 == 0)
				ConsoleManager.WriteLine();
		}
		
		ConsoleManager.WriteLine();
		ConsoleManager.Write("Enter ID of Contact: ");
		int id = Integer.parseInt(ConsoleManager.ReadLine());
		
		Contact contact = contactRepo.GetById(id);
		
		ConsoleManager.Write("First Name ( " + contact.getFirstName() + " ): ");
		contact.setFirstName(ConsoleManager.ReadLine());
		
		ConsoleManager.Write("Last Name: ( " + contact.getLastName() + " ): ");
		contact.setLastName(ConsoleManager.ReadLine());
		
		ConsoleManager.Write("Email: ( " + contact.getEmail() + " ): ");
		contact.setEmail(ConsoleManager.ReadLine());
		
		contactRepo.Edit(contact);
		
		ConsoleManager.Clear();
		ConsoleManager.WriteLine("Item updated successfully");
		ConsoleManager.WriteLine("Press [Enter] to continue");
		ConsoleManager.ReadLine();
	}
	
	private void Delete() {

		ConsoleManager.Clear();
		ConsoleManager.WriteLine("####Delete Contact####");
		
		ContactsRepository contactRepo = new ContactsRepository("contacts.txt");
		ArrayList<Contact> contacts = contactRepo.GetAll();
		
		for(int i=0;i<contacts.size();i++) {
			
			Contact contact = contacts.get(i);
			
			ConsoleManager.Write(contact.getFirstName() + " " + contact.getLastName() + " ( " + contact.getId() + " )\t");
			
			if (i > 0 && i % 5 == 0)
				ConsoleManager.WriteLine();
		}
		
		ConsoleManager.WriteLine();
		ConsoleManager.Write("Enter ID of Contact: ");
		int id = Integer.parseInt(ConsoleManager.ReadLine());
		
		Contact contact = contactRepo.GetById(id);
		contactRepo.Delete(contact);
		
		ConsoleManager.Clear();
		ConsoleManager.WriteLine("Item deleted successfully");
		ConsoleManager.WriteLine("Press [Enter] to continue");
		ConsoleManager.ReadLine();
	}
	
	private void View() {

		ConsoleManager.Clear();
		ConsoleManager.WriteLine("####View Contact####");
		
		ContactsRepository contactRepo = new ContactsRepository("contacts.txt");
		ArrayList<Contact> contacts = contactRepo.GetAll();
		
		for(int i=0;i<contacts.size();i++) {
			
			Contact contact = contacts.get(i);
			
			ConsoleManager.Write(contact.getFirstName() + " " + contact.getLastName() + " ( " + contact.getId() + " )\t");
			
			if (i > 0 && i % 5 == 0)
				ConsoleManager.WriteLine();
		}
		
		ConsoleManager.WriteLine();
		ConsoleManager.Write("Enter ID of user: ");
		int id = Integer.parseInt(ConsoleManager.ReadLine());
		
		Contact contact = contactRepo.GetById(id);
		
		ConsoleManager.Clear();
		
		PhonesManagementView phonesView = new PhonesManagementView(contact);
		phonesView.Run();
	}
}
