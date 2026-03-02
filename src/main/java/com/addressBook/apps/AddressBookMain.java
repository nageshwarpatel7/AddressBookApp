package com.addressBook.apps;

public class AddressBookMain {
	public static void main(String[] args) {
		
		Contact sampleContact = new Contact(
				"Nageshwar",
				"Patel", 
				"Press colony, Bhopal", 
				"Bhopal",
				"Madhya Pradesh",
				"462021",
				"1242345678",
				"nageshwar@example.com"
						);
		System.out.println("Contact created successfully");
		System.out.println(sampleContact);
	}
}
