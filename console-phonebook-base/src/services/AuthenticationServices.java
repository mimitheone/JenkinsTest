package services;

import java.io.FileNotFoundException;
import java.io.IOException;

import entities.*;
import repositories.*;

public class AuthenticationServices {
	
	private static AuthenticationServices instance = null;

	public static AuthenticationServices getInstance() {

		if (AuthenticationServices.instance == null)
			AuthenticationServices.instance = new AuthenticationServices();

		return AuthenticationServices.instance;
	}

	private AuthenticationServices() {

	}

	private User authenticatedUser = null;
	
	public User getLoggedUser() {
		return authenticatedUser;
	}
	
	public void AuthenticateUser(String username, String password) {
		
		UsersRepository usersRepo = new UsersRepository("users.txt");
		this.authenticatedUser = usersRepo.GetByUsernameAndPassword(username, password);
	}
}
