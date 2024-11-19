package application;

import java.sql.Connection;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
    	
    	Connection connection = DatabaseConnection.getConnection();
    	if (connection != null) {
    		System.out.println("Database connection successful");    		
    	} else {
    		System.out.println("Failed to connect to the database");
    	}
    	
    	// adding test data into the db to see if login works
//        String insertUsers = """
//                INSERT INTO Users (asu_id, password, role, email) VALUES
//                ('123456', 'password', 'buyer', 'buyer@example.com'),
//                ('654321', 'password', 'seller', 'seller@example.com'),
//                ('admin01', 'adminpass', 'admin', 'admin@example.com');
//                """;

//        try (Connection conn = DatabaseConnection.getConnection();
//             Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate(insertUsers);
//            System.out.println("Test data inserted successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    	
        LoginPage.main(args); // Starts with the login page 
    }
}