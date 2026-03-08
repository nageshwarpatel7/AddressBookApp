package com.addressBook.database;
import java.sql.*;

public class ConnectDatabase {
	
	private static String url = "jdbc:mysql://localhost:3306/practice";
	private static String user = "root";
	private static String password = "1234";
	private static Connection con;
	
		
	public static Connection getConnection() throws SQLException{
		
		if(con==null || con.isClosed())
			con = DriverManager.getConnection(url, user, password);
		return con;
	}
	
}
