package application;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
    	
    	Connection connection = DatabaseConnection.getConnection();
    	if (connection != null) {
    		System.out.println("Database connection successful");    		
    	} else {
    		System.out.println("Failed to connect to the database");
    	}
    	
    	
        LoginPage.main(args); // Starts with the login page 
    }
}