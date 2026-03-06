package com.addressBook.apps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

import com.addressBook.apps.model.Contact;

public class AddressBookMain {
	private static Map<String, AddressBook> addressBookSystem = new HashMap<>();
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome to Address Book System");
		
		while(true) {
			System.out.println("\n1. Add New Address Book");
			System.out.println("2. Access Existing Addess Book");
			System.out.println("3. Exit");
			int choice = sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
			case 1:
				System.out.print("Enter unique name for new Address Book: ");
				String name = sc.nextLine();
				if(addressBookSystem.containsKey(name)) {
					System.out.println("Address Book with this name already exits");
				}
				else {
					addressBookSystem.put(name, new AddressBook());
					System.out.println("Address Book "+name+" "+" created.");
				}
				break;
			case 2:
				System.out.println("Enter Address Book name to access: ");
				String bookName = sc.nextLine();
				AddressBook currentBook = addressBookSystem.get(bookName);
				if(currentBook!=null) {
					System.out.println("Accessing "+bookName);
					manageAddressBook(currentBook, bookName);
				}	
				else {
					System.out.println("Address Book not found");
				}
				break;
			case 3:
				System.exit(0);
			}
		}
	}
	public static void manageAddressBook(AddressBook book, String bookName) {
		while(true) {
			System.out.println("\n--- Managing Address Book: "+bookName+" ---");
			System.out.println("1.Add Contact\n2. Edit Contact\n3. Delete Contact\n4. View All\n5. Search By City"
					+ "\n6. Search By State\n7. Back");
			int choice = sc.nextInt();
			sc.nextLine();
			
			if(choice==7) 
				break;
			
			switch(choice) {
			case 1:
				book.addContact(getContactFromConsole());
				break;
			case 2:
				System.out.println("Enter full name to edit: ");
				String name = sc.nextLine();
				
				System.out.println("Enter updated details separated by ':'");
				System.out.println("Format: firstName:lastName:address:city:state:zip:phone:email");
				String data = sc.nextLine();
				book.update(name, data);
				break;
			case 3:
				System.out.print("Enter full name to delete: ");
                book.deleteContact(sc.nextLine()); 
                break;
            case 4:
                book.displayContact();
                break;
            case 5:
            	System.out.println("Enter city: ");
            	String city = sc.nextLine();
            	
            	List<Contact> cityResult = book.searchContactByCity(city);
            	if(cityResult.isEmpty()) System.out.println("No contact found in this city");
            	else
            		cityResult.forEach(System.out::println);
            	break;
            case 6:
            	System.out.println("Enter state");
            	String state = sc.nextLine();
            	
            	List<Contact> stateResult = book.searchContactByState(state);
            	if(stateResult.isEmpty())
            		System.out.println("No contact found in this state");
            	else
            		stateResult.forEach(System.out::println);
            	break;
			}
		}
	}
	public static Contact getContactFromConsole() {
		System.out.print("First Name: "); String fn = sc.nextLine();
        System.out.print("Last Name: "); String ln = sc.nextLine();
        System.out.print("Address: "); String addr = sc.nextLine();
        System.out.print("City: "); String city = sc.nextLine();
        System.out.print("State: "); String state = sc.nextLine();
        System.out.print("Zip: "); String zip = sc.nextLine();
        System.out.print("Phone: "); String ph = sc.nextLine();
        System.out.print("Email: "); String em = sc.nextLine();
        return new Contact(fn, ln, addr, city, state, zip, ph, em);
	}
}
