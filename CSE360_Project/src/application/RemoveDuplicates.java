package application;

import java.sql.Connection;
import java.sql.Statement;

public class RemoveDuplicates {

    public static void main(String[] args) {
        String removeDuplicatesQuery = """
                DELETE FROM Books
                WHERE ROWID NOT IN (
                    SELECT MIN(ROWID)
                    FROM Books
                    GROUP BY title, author, published_year
                );
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(removeDuplicatesQuery);
            System.out.println("Duplicates removed: " + rowsAffected);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}