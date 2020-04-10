package views;

import java.io.*;
import tools.*;

public class AdministrationView {
	
	public void Run() {
		
		while(true){
			ConsoleManager.Clear();
			
			ConsoleManager.WriteLine("[U]ser Management");
			ConsoleManager.WriteLine("[C]ontacts Management");
			ConsoleManager.WriteLine("E[x]it");
			
			ConsoleManager.Write(">");
			String choice = ConsoleManager.ReadLine();
			
			switch(choice.toUpperCase()){
				case "U":{
					UsersManagement();
					break;
				}
				case "C":{
					ContactsManagement();
					break;
				}
				case "X":{
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
		
		UsersManagementView view = new UsersManagementView();
		view.Run();
	}
	
	private void ContactsManagement() {
		
		ContactsManagementView view = new ContactsManagementView();
		view.Run();
	}
}
