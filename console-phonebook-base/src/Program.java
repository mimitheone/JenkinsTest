import java.io.*;

import services.AuthenticationServices;
import tools.ConsoleManager;
import views.*;

public class Program {

	public static void main(String[] args) throws IOException {
		
		AuthenticationView authView = new AuthenticationView();
		authView.Run();
		
		if (AuthenticationServices.getInstance().getLoggedUser() != null) {
			
			if (AuthenticationServices.getInstance().getLoggedUser().getIsAdmin()) {
				AdministrationView view = new AdministrationView();
				view.Run();
			}
			else {
				ContactsManagementView view = new ContactsManagementView();
				view.Run();
			}
			
			ConsoleManager.Clear();
			ConsoleManager.WriteLine("Good bye");
		}
	}
}