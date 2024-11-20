package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate(insertUsers);
//            System.out.println("Test data inserted successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
            
//        String insertBooks = """
//                INSERT INTO books (title, author, published_year, category, condition, price, seller_id) VALUES
//        		('Test Book 1', 'John Doe', 2011, 'Natural Science', 'Used Like New', 69.69, 1),
//        		('Test Book 2', 'John Doe', 3014, 'Natural Science', 'Moderately Used', 123.32, 1),
//        		('Test Book 3', 'John Doe', 2009, 'Natural Science', 'Used Like New', 1.00, 3),
//        		('CodingJavaShops', 'Code Master John', 1492, 'Computer', 'Heavily Used', 54.99, 2);
//                """;
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(insertBooks)) {
//            stmt.executeUpdate();
//            System.out.println("Test data inserted successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    	
        LoginPage.main(args); // Starts with the login page 
    }
}
