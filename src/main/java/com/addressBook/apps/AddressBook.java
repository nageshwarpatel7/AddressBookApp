package com.addressBook.apps;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.addressBook.apps.model.Contact;

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
}
