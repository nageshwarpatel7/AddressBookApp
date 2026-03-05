package com.addressBook.apps;
import java.io.IOException;
import java.util.*;

import com.addressBook.apps.model.Contact;

public class AddressBook {
	private List<Contact> contacts = new ArrayList<>(); //Helps to add multiple person in the Address book App
	
	public void addContact(Contact c) {
		contacts.add(c);
	}
	public void displayContact() {
		for(Contact c: contacts) {
			System.out.println(c);
		}
	}
	public void update(String name, String s) {
		String info[] = s.split(":");
		if(info.length!=8) {
			throw new IllegalArgumentException("Invalid Input");
		}
		for(Contact c:contacts) {
			if(name.equalsIgnoreCase(c.getFirstName()+" "+c.getLastName())) {
				c.setFirstName(info[0]);
				c.setLastName(info[1]);
				c.setAddress(info[2]);
				c.setCity(info[3]);
				c.setState(info[4]);
				c.setZip(info[5]);
				c.setPhoneNumber(info[6]);
				c.setEmail(info[7]);
				return;
			}
		}
		System.out.println("User not found");		
	}
	
	public  void deleteContact(String name) {
		for(Contact c: contacts) {
			if(name.equalsIgnoreCase(c.getFirstName()+" "+c.getLastName())) {
				System.out.println("Deleted contact: "+c.toString());
				contacts.remove(c);
				return;
			}
		}
		System.out.println("User not found");
	}
		
}
