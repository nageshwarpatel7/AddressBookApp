package com.addressBook.apps.model;

import java.util.Objects;
import java.sql.Date;
import java.time.LocalDate;

public class Contact {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phoneNumber;
	private String email;
	private LocalDate dateAdded;
	public Contact() {
		
	}
	public Contact(String firstName, String lastName, String address, String city, String state, String zip,
			String phoneNumber, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.dateAdded =LocalDate.now();
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public LocalDate getDateAdded() {
		return dateAdded;
	}
	@Override
	public String toString() {
		return "Contact [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city=" + city
				+ ", state=" + state + ", zip=" + zip + ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", dateAdded=" + dateAdded + "]";
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDate(Date date) {
		this.dateAdded = LocalDate.parse(date.toString());
	}	
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj==null || getClass()!=obj.getClass()) return false;
		
		Contact c = (Contact) obj;
		
		return firstName.equalsIgnoreCase(c.firstName)
	            && lastName.equalsIgnoreCase(c.lastName)
	            && Objects.equals(address, c.address)
	            && Objects.equals(city, c.city)
	            && Objects.equals(state, c.state)
	            && Objects.equals(zip, c.zip)
	            && Objects.equals(phoneNumber, c.phoneNumber)
	            && Objects.equals(email, c.email);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase());
	}
	
}
