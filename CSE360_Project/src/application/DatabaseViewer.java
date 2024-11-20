package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseViewer {

    public static void main(String[] args) {
        String query = "SELECT * FROM Cart";

        
        // Use this to view what is in the database
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Books Table:");
            System.out.println("ID | Title | Author | Year | Category | Condition | Price | Seller ID");
            System.out.println("------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%d | %s | %s | %d | %s | %s | %.2f | %d%n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("published_year"),
                        rs.getString("category"),
                        rs.getString("condition"),
                        rs.getDouble("price"),
                        rs.getInt("seller_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}