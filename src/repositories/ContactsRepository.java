package repositories;

import java.io.*;
import java.util.*;

import entities.*;
import exceptions.DataAccessException;
import tools.*;

public class ContactsRepository {
	
	private String fileName;
	
	public ContactsRepository(String fileName) {
		
		this.fileName = fileName;

		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
		}
		catch(Exception ex) {
			throw new DataAccessException(ex);
		}
	}
	
	private int GetNextId() {
		
		int nextId = 0;
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
		
			String value = "";
			while((value = bufferedReader.readLine()) != null) {
				
				Contact item = new Contact();
				item.setId(Integer.parseInt(value));
				
				item.setUserId(Integer.parseInt(bufferedReader.readLine()));
				item.setFirstName(bufferedReader.readLine());
				item.setLastName(bufferedReader.readLine());
				item.setEmail(bufferedReader.readLine());
				
				if (nextId < item.getId())
					nextId = item.getId();
			}
		}
		catch(Exception ex) {
			throw new DataAccessException(ex);
		}
		
		return nextId + 1;
	}
	
	public Contact GetById(int id) {
		
		Contact result = null;
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
		
			String value = "";
			while((value = bufferedReader.readLine()) != null) {
				
				Contact item = new Contact();
				item.setId(Integer.parseInt(value));
				
				item.setUserId(Integer.parseInt(bufferedReader.readLine()));
				item.setFirstName(bufferedReader.readLine());
				item.setLastName(bufferedReader.readLine());
				item.setEmail(bufferedReader.readLine());
				
				if (item.getId() == id) {
					result = item;
					break;
				}
			}		
		}
		catch(Exception ex) {
			throw new DataAccessException(ex);
		}
		
		return result;
	}
	
	public ArrayList<Contact> GetAll() {
		
		ArrayList<Contact> result = new ArrayList<Contact>();
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
			
			String value = "";
			while((value = bufferedReader.readLine()) != null) {
				
				Contact item = new Contact();
				item.setId(Integer.parseInt(value));
				
				item.setUserId(Integer.parseInt(bufferedReader.readLine()));
				item.setFirstName(bufferedReader.readLine());
				item.setLastName(bufferedReader.readLine());
				item.setEmail(bufferedReader.readLine());
				
				result.add(item);
			}		
		}
		catch(Exception ex) {
			throw new DataAccessException(ex);
		}
		
		return result;
	}
	
	public void Add(Contact item) {
		
		item.setId(GetNextId());
		
		try (PrintWriter printWriter  = new PrintWriter(new FileWriter(fileName, true))) {
			
			printWriter.println(item.getId());
			
			printWriter.println(item.getUserId());
			printWriter.println(item.getFirstName());
			printWriter.println(item.getLastName());
			printWriter.println(item.getEmail());
		}
		catch(Exception ex) {
			throw new DataAccessException(ex);
		}
	}
	
	public void Edit(Contact item) {
		
		String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
				 PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))) {
			
			String value = "";
			while((value = bufferedReader.readLine()) != null) {
				
				Contact tempEntity = new Contact();
				tempEntity.setId(Integer.parseInt(value));
				
				tempEntity.setUserId(Integer.parseInt(bufferedReader.readLine()));
				tempEntity.setFirstName(bufferedReader.readLine());
				tempEntity.setLastName(bufferedReader.readLine());
				tempEntity.setEmail(bufferedReader.readLine());
				
				if (tempEntity.getId() != item.getId()) {
				
					printWriter.println(tempEntity.getId());
					
					printWriter.println(tempEntity.getUserId());
					printWriter.println(tempEntity.getFirstName());
					printWriter.println(tempEntity.getLastName());
					printWriter.println(tempEntity.getEmail());
				}
				else {
					
					printWriter.println(item.getId());
					
					printWriter.println(item.getUserId());
					printWriter.println(item.getFirstName());
					printWriter.println(item.getLastName());
					printWriter.println(item.getEmail());
				}
			}
		}
		catch(Exception ex) {
			throw new DataAccessException(ex);
		}
		
		File original = new File(fileName);
		File tempFile = new File(tempFileName);
		
		original.delete();
		tempFile.renameTo(original);
		tempFile.delete();
	}
	
	public void Delete(Contact item) {

		String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
				 PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))) {
			
			String value = "";
			while((value = bufferedReader.readLine()) != null) {
				
				Contact tempEntity = new Contact();
				tempEntity.setId(Integer.parseInt(value));
				
				tempEntity.setUserId(Integer.parseInt(bufferedReader.readLine()));
				tempEntity.setFirstName(bufferedReader.readLine());
				tempEntity.setLastName(bufferedReader.readLine());
				tempEntity.setEmail(bufferedReader.readLine());
				
				if (tempEntity.getId() != item.getId()) {
				
					printWriter.println(tempEntity.getId());
					printWriter.println(tempEntity.getUserId());
					printWriter.println(tempEntity.getFirstName());
					printWriter.println(tempEntity.getLastName());
					printWriter.println(tempEntity.getEmail());
				}
			}
		}
		catch(Exception ex) {
			throw new DataAccessException(ex);
		}
		
		File original = new File(fileName);
		File tempFile = new File(tempFileName);
		
		original.delete();
		tempFile.renameTo(original);
		tempFile.delete();
	}
	
	public ArrayList<Contact> GetAll(int parentId) {
		
		ArrayList<Contact> result = new ArrayList<Contact>();
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
			
			String value = "";
			while((value = bufferedReader.readLine()) != null) {
				
				Contact contact = new Contact();
				contact.setId(Integer.parseInt(value));
				contact.setUserId(Integer.parseInt(bufferedReader.readLine()));
				contact.setFirstName(bufferedReader.readLine());
				contact.setLastName(bufferedReader.readLine());
				contact.setEmail(bufferedReader.readLine());
				
				if (contact.getUserId() == parentId)
					result.add(contact);
			}		
		}
		catch(Exception ex) {
			throw new DataAccessException(ex);
		}
		
		return result;
	}
}
