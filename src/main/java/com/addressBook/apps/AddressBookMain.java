package com.addressBook.apps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

import com.addressBook.apps.model.Contact;

public class AddressBookMain {
	private static  List<Contact> contacts = new ArrayList<>();
	
	public static void add(String s) {
		String[] info = s.split(":");
		if(info.length!=8) {
			throw new IllegalArgumentException("Invalid Input");
		}
		contacts.add(new Contact(info[0], info[1], info[2], info[3], info[4], info[5],
				info[6], info[7]));
	}
	
	public static void main(String args[]) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		
		while((line=br.readLine())!=null) {
			add(line);
		}
		for(Contact c: contacts) {
			System.out.println(c);
		}
	}
}
