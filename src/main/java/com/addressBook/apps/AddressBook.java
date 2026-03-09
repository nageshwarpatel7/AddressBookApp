package com.addressBook.apps;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.SQLException;

import com.addressBook.apps.model.Contact;
import com.addressBook.database.*;

public class AddressBook {
	private List<Contact> contacts = new ArrayList<>(); //Helps to add multiple person in the Address book App
	
	public void addContact(Contact c) {
		boolean exists = contacts.stream().anyMatch(contact	-> contact.getFirstName().equalsIgnoreCase(c.getFirstName())
				&& contact.getLastName().equalsIgnoreCase(c.getLastName()));
		
		if(exists) {
			System.out.println("Duplicate Contact! Person already exists.");
			return;
		}
		contacts.add(c);
		System.out.println("Contact added successfully");
	}
	public void displayContact() {
		if(contacts.isEmpty()) {
			System.out.println("No contacts available");
			return;
		}
		for(Contact c: contacts) {
			System.out.println(c);
		}
	}
	public void update(String name, String data, String addressBookName) {

	    String info[] = data.split(":");

	    if(info.length != 8)
	        throw new IllegalArgumentException("Invalid Input");

	    for(Contact c : contacts) {

	        if(name.equalsIgnoreCase(c.getFirstName()+" "+c.getLastName())) {

	            c.setFirstName(info[0]);
	            c.setLastName(info[1]);
	            c.setAddress(info[2]);
	            c.setCity(info[3]);
	            c.setState(info[4]);
	            c.setZip(info[5]);
	            c.setPhoneNumber(info[6]);
	            c.setEmail(info[7]);

	            DatabaseOperation.update(c, addressBookName);

	            syncWithDatabase(c.getFirstName(), c.getLastName(), addressBookName);

	            return;
	        }
	    }

	    System.out.println("User not found");
	}
	
	public  void deleteContact(String name) {
		boolean removed = contacts.removeIf(
		        c -> name.equalsIgnoreCase(c.getFirstName()+" "+c.getLastName())
		    );

		    if(!removed)
		        System.out.println("User not found");
	}
	public List<Contact> searchContactByCity(String city){
		List<Contact> ans = contacts.stream().filter(c->c.getCity().equalsIgnoreCase(city)).toList();
		return ans;
	}
	public List<Contact> searchContactByState(String state){
		return contacts.stream().filter(c->c.getState().equalsIgnoreCase(state)).toList();
	}
		
	public Map<String, List<Contact>> viewContactByCity(){
		return contacts.stream().collect(Collectors.groupingBy(c->c.getCity(), Collectors.toList()));
	}
	public Map<String, List<Contact>> viewContactByState(){
		return contacts.stream().collect(Collectors.groupingBy(c->c.getState(), Collectors.toList()));
	}
	public Map<String, Long> getContactsByCity(){
		return contacts.stream().collect(Collectors.groupingBy(c->c.getCity(), Collectors.counting()));
	}
	public Map<String, Long> getContactsByState(){
		return contacts.stream().collect(Collectors.groupingBy(c->c.getState(), Collectors.counting()));
	}
	public List<Contact> getContactSortedByName(){
		return contacts.stream().sorted(Comparator.comparing(Contact::getFirstName).thenComparing(Contact::getLastName)).toList();
	}
	public List<Contact> getContactSortedByCity(){
		return contacts.stream().sorted(Comparator.comparing(Contact::getCity)).toList();
	}
	public List<Contact> getContactSortedByState(){
		return contacts.stream().sorted(Comparator.comparing(Contact::getState)).toList();
	}
	public List<Contact> getContactSortedByZip(){
		return contacts.stream().sorted(Comparator.comparing(Contact::getZip)).toList();
	}
	
	public void writeContactToFile(String filePath) {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
			for(Contact c: contacts) {
				String data = c.getFirstName()+":"+c.getLastName()+":"+c.getAddress()
				+":"+c.getCity()+":"+c.getState()+":"+c.getZip()+":"+c.getPhoneNumber()+":"+c.getEmail();
				
				bw.write(data);
				bw.newLine();
			}
			System.out.println("Contacts saved to file");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void readContactFile(String filePath) {
		contacts.clear();
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
			String line;
			while((line = br.readLine())!=null) {
				String info[] = line.split(":");
				if(info.length!=8)
					continue;
				Contact c= new Contact(info[0], info[1], info[2], info[3], info[4],
						info[5], info[6], info[7]);
				contacts.add(c);
			}
			System.out.println("Contacts loaded from file");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void writeContactsToCSVFile(String filePath) {
		try(CSVWriter writer = new CSVWriter(new FileWriter(filePath))){
			String[] header = {"FirstName", "LastName", "Address", "City",
					"State", "Zip", "PhoneNumber", "Email"
			};
			writer.writeNext(header);
			
			for(Contact c:contacts) {
				String[] data = {
						c.getFirstName(), c.getLastName(),
						c.getAddress(), c.getCity(),
						c.getState(), c.getZip(),
						c.getPhoneNumber(), c.getEmail()
				};
				
				writer.writeNext(data);
			}
			System.out.println("Contacts saved to csv file");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void readContactsToCSVFile(String filePath) {
		contacts.clear();
		try(CSVReader reader = new CSVReader(new FileReader(filePath))){
			String[]  line;
			reader.readNext();
			while((line=reader.readNext())!=null) {
				if(line.length!=8) continue;
				Contact c= new Contact(line[0], line[1],
						line[2], line[3], line[4], line[5], line[6], line[7]);
				contacts.add(c);
			}
			System.out.println("Contacts loaded from CSV");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(CsvValidationException e) {
			e.printStackTrace();
		}
	}
	public void writeContactsToJSONFile(String filePath) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try(FileWriter writer = new FileWriter(filePath)){
			gson.toJson(contacts, writer);
			System.out.println("Contacts saved to json");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void readContactsFromJSONFile(String filePath) {
		contacts.clear();
		Gson gson = new Gson();
		try(FileReader reader = new FileReader(filePath)){
			Type contactListType = new TypeToken<List<Contact>>() {}.getType();
			
			contacts = gson.fromJson(reader, contactListType);
			
			if(contacts ==null)
				contacts = new ArrayList<>();
			System.out.println("Contacts loaded from JSON");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void writeContactsToDatabase(String addresBookName) {
		for(Contact c: contacts) {
			DatabaseOperation.add(c, addresBookName);
		}
		System.out.println("Contacts successfully added to database");
	}
	public void readFromDatabase(String addressBookName) {
		try {
			contacts.clear();
			contacts.addAll(DatabaseOperation.getAll(addressBookName));
			
			System.out.println("Contacs loaded from database");
			
			for(Contact c: contacts) {
				System.out.println(c);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void syncWithDatabase(String fname, String lname, String addressBook) {

	    Contact dbContact =
	    DatabaseOperation.getPerson(fname, lname, addressBook);
	    if(dbContact ==null) {
	    	System.out.println("Contact not found in database");
	    	return;
	    }

	    for(Contact c : contacts){

	        if(c.getFirstName().equalsIgnoreCase(fname) &&
	           c.getLastName().equalsIgnoreCase(lname)){

	            if(c.equals(dbContact))
	                System.out.println("Memory and Database are in Sync");
	            else
	                System.out.println("Memory and Database are NOT in Sync");
	        }
	    }
	}
}
