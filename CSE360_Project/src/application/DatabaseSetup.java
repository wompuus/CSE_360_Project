package application;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static void setupDatabase() {
        String createUsersTable = """
                CREATE TABLE IF NOT EXISTS Users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    asu_id TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,
                    role TEXT NOT NULL CHECK(role IN ('buyer', 'seller', 'admin')),
                    email TEXT NOT NULL
                );
                """;

        String createBooksTable = """
                CREATE TABLE IF NOT EXISTS Books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    author TEXT NOT NULL,
                    published_year INTEGER NOT NULL,
                    category TEXT NOT NULL,
                    condition TEXT NOT NULL CHECK(condition IN ('Used Like New', 'Moderately Used', 'Heavily Used')),
                    price REAL NOT NULL,
                    seller_id INTEGER NOT NULL,
                    FOREIGN KEY (seller_id) REFERENCES Users(id) ON DELETE CASCADE
                );
                """;

        String createTransactionsTable = """
                CREATE TABLE IF NOT EXISTS Transactions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    book_id INTEGER NOT NULL,
                    buyer_id INTEGER NOT NULL,
                    seller_id INTEGER NOT NULL,
                    price REAL NOT NULL,
                    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (book_id) REFERENCES Books(id) ON DELETE CASCADE,
                    FOREIGN KEY (buyer_id) REFERENCES Users(id) ON DELETE CASCADE,
                    FOREIGN KEY (seller_id) REFERENCES Users(id) ON DELETE CASCADE
                );
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createBooksTable);
            stmt.execute(createTransactionsTable);
            System.out.println("Database setup complete.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        setupDatabase();
    }
}