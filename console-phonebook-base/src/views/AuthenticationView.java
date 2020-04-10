package views;

import java.io.*;

import services.*;
import tools.*;

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
