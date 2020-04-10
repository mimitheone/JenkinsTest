package views;

import java.io.*;
import java.util.*;

import entities.*;
import repositories.*;
import tools.*;

public class PhonesManagementView {
	
	private Contact parent;
	
	public PhonesManagementView(Contact parent) {
		this.parent = parent;
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
		
		PhonesRepository phonesRepo = new PhonesRepository("phones.txt");
		
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
			
			ArrayList<Phone> phones = phonesRepo.GetAll(parent.getId());
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
		
		PhonesRepository phoneRepo = new PhonesRepository("phones.txt");
		phoneRepo.Add(item);
		
		ConsoleManager.Clear();
		ConsoleManager.WriteLine("Item added successfully");
		ConsoleManager.WriteLine("Press [Enter] to continue");
		ConsoleManager.ReadLine();
	}
	
	private void List() {
		
		ConsoleManager.Clear();
		ConsoleManager.WriteLine("####List Phones####");
		
		PhonesRepository phoneRepo = new PhonesRepository("phones.txt");
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
		
		PhonesRepository phoneRepo = new PhonesRepository("phones.txt");
		ArrayList<Phone> phones = phoneRepo.GetAll();
		
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
		
		PhonesRepository phoneRepo = new PhonesRepository("phones.txt");
		ArrayList<Phone> phones = phoneRepo.GetAll();
		
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
		
		PhonesRepository phoneRepo = new PhonesRepository("phones.txt");
		ArrayList<Phone> phones = phoneRepo.GetAll();
		
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
