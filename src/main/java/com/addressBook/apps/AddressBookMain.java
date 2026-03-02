package com.addressBook.apps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

import com.addressBook.apps.model.Contact;

public class AddressBookMain {
	private static  List<Contact> contacts = new ArrayList<>(); //Helps to add multiple person in the Address book App
	
	public static void add(String s) {
		String[] info = s.split(":");
		if(info.length!=8) {
			throw new IllegalArgumentException("Invalid Input");
		}
		contacts.add(new Contact(info[0], info[1], info[2], info[3], info[4], info[5],
				info[6], info[7]));
	}
	
	public static void update(String name, String s) {
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
	
	public static void deleteContact(String name) {
		for(Contact c: contacts) {
			if(name.equalsIgnoreCase(c.getFirstName()+" "+c.getLastName())) {
				System.out.println("Deleted contact: "+c.toString());
				contacts.remove(c);
				return;
			}
		}
		System.out.println("User not found");
	}
	public static void main(String args[]) throws IOException {
		
		add("lucky:pal:berkhera:bhopal:MP:12345:83056144536:pallucky936@gmail.com");
    	add("Himesh:kurmi:baisa:sagar:MP:462022:89564122121:himeshkurmi@gmail.com");
    	add("nageshwar:patel:maiyar:katni:MP:11111:7845129654:nageshwar@gmail.com");
    	for(Contact c : contacts) {
    		System.out.println(c.toString());
    	}
    	
    	update("himesh kurmi","Himesh:kurmi:Anand Nager:Bhopal:MP:462022:89564122121:himeshkurmi@gmail.com ");
    	System.out.println("\n");
    	for(Contact c: contacts) {
    		System.out.println(c);
    	}
    	
    	System.out.println("\n");
    	deleteContact("himesh kurmi");
    	for(Contact c: contacts) {
    		System.out.println(c);
    	}
    	
	}
}
