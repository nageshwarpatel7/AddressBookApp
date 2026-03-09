package com.addressBook.database;
import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.sql.Date;

import com.addressBook.apps.model.Contact;

public class DatabaseOperation {
	
	private static Connection con;
	
	public static void add(Contact contact, String addressBookName) {
		try {
			con = ConnectDatabase.getConnection();
			
			String query = "insert into contacts(fname, lname, address, city, state, zip, phoneNo, email, addressBook, dateAdded)"
					+ " values(?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setString(1, contact.getFirstName());
			pstm.setString(2, contact.getLastName());
			pstm.setString(3, contact.getAddress());
			pstm.setString(4,  contact.getCity());
			pstm.setString(5, contact.getState());
			pstm.setString(6,  contact.getZip());
			pstm.setString(7, contact.getPhoneNumber());
			pstm.setString(8, contact.getEmail());
			pstm.setString(9, addressBookName);
			pstm.setDate(10, Date.valueOf(contact.getDateAdded()));
			
			int s = pstm.executeUpdate();
			System.out.println(s+" rows updates sucessfully!");
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Contact> getAll(String s) throws SQLException{
		
		con = ConnectDatabase.getConnection();
		String query = "select * from contacts where addressBook=?";
		
		PreparedStatement pstm = con.prepareStatement(query);
		pstm.setString(1, s);
		
		ResultSet rs = pstm.executeQuery();
		
		List<Contact>  ans = new ArrayList<>();
		
		while(rs.next()) {
			Contact c = new Contact();
			c.setFirstName(rs.getString("fname"));
			c.setLastName(rs.getString("lname"));
			c.setAddress(rs.getString("address"));
			c.setCity(rs.getString("city"));
			c.setState(rs.getString("state"));
			c.setZip(rs.getString("zip"));
			c.setPhoneNumber(rs.getString("phoneNo"));
			c.setEmail(rs.getString("email"));
			c.setDate(rs.getDate("dateAdded"));
			ans.add(c);
		}
		return ans;
	}
	public static Contact getPerson(String fname, String lname, String addressBook) {

	    try {

	        con = ConnectDatabase.getConnection();

	        String query =
	        "select * from contacts where fname=? and lname=? and addressBook=?";

	        PreparedStatement pstm = con.prepareStatement(query);

	        pstm.setString(1, fname);
	        pstm.setString(2, lname);
	        pstm.setString(3, addressBook);

	        ResultSet rs = pstm.executeQuery();

	        if(rs.next()) {

	            return new Contact(
	                    rs.getString("fname"),
	                    rs.getString("lname"),
	                    rs.getString("address"),
	                    rs.getString("city"),
	                    rs.getString("state"),
	                    rs.getString("zip"),
	                    rs.getString("phoneNo"),
	                    rs.getString("email")
	            );
	        }

	    }
	    catch(SQLException e){
	        e.printStackTrace();
	    }

	    return null;
	}
	public static void update(Contact contact, String addressBookName) {

	    try {

	        con = ConnectDatabase.getConnection();

	        String query =
	        "update contacts set address=?, city=?, state=?, zip=?, phoneNo=?, email=? "
	        + "where fname=? and lname=? and addressBook=?";

	        PreparedStatement pstm = con.prepareStatement(query);

	        pstm.setString(1, contact.getAddress());
	        pstm.setString(2, contact.getCity());
	        pstm.setString(3, contact.getState());
	        pstm.setString(4, contact.getZip());
	        pstm.setString(5, contact.getPhoneNumber());
	        pstm.setString(6, contact.getEmail());
	        pstm.setString(7, contact.getFirstName());
	        pstm.setString(8, contact.getLastName());
	        pstm.setString(9, addressBookName);

	        int rows = pstm.executeUpdate();

	        if(rows > 0)
	            System.out.println("Contact updated in database");

	    }
	    catch(SQLException e){
	        e.printStackTrace();
	    }
	}
}
