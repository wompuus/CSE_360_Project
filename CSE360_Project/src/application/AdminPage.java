package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Label for title
        Label title = new Label("Admin Dashboard");
        title.setStyle("-fx-font-size: 24px");

        // Buttons for different actions
        Button viewBooksButton = new Button("View Books");
        Button orderHistoryButton = new Button("Order History");
        Button logoutButton = new Button("Logout");

        // Action for logout button
        logoutButton.setOnAction(e -> new LoginPage().start(primaryStage));

        // Action for View Books button
        viewBooksButton.setOnAction(e -> showBooksPage(primaryStage));

        // Layout for the admin page
        VBox Layout = new VBox(10, title, viewBooksButton, orderHistoryButton, logoutButton);
        Layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Scene for the Admin Dashboard
        Scene adminScene = new Scene(Layout, 400, 300);
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(adminScene);
        primaryStage.show();
    }

    
    
 // Method to show the Books page
    private void showBooksPage(Stage primaryStage) {
        // Create a label for the View Books page
        Label booksTitle = new Label("View Books");
        booksTitle.setStyle("-fx-font-size: 24px");

        // Button to go back to the Admin Dashboard
        Button backButton = new Button("Back to Admin Dashboard");
        backButton.setOnAction(e -> start(primaryStage));  // Go back to the admin page

        // Search Bar
        HBox searchBar = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Search books...");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("All Categories", "Natural Science", "Computer", "Mathematics");
        categoryComboBox.setValue("All Categories");
        Button searchButton = new Button("Search");
        searchBar.getChildren().addAll(searchField, categoryComboBox, searchButton);

        // Book listings
        VBox bookList = new VBox(10);
        bookList.setPadding(new Insets(10));
        ScrollPane bookScrollPane = new ScrollPane(bookList);
        bookScrollPane.setFitToWidth(true);

        // Pagination
        HBox paginationBar = new HBox(10);
        paginationBar.setPadding(new Insets(10));
        paginationBar.setStyle("-fx-alignment: center;");

        for (int i = 1; i <= 5; i++) {
            Button pageButton = new Button(String.valueOf(i));
            paginationBar.getChildren().add(pageButton);
        }

        // Initially populate the books
        populateBooks(bookList, null, "All Categories");

        // Search button action
        searchButton.setOnAction(e -> {
            String searchText = searchField.getText();
            String selectedCategory = categoryComboBox.getValue();
            populateBooks(bookList, searchText, selectedCategory);
            searchField.setText(null);
        });

        // Layout for the View Books page
        VBox layout = new VBox(10, booksTitle, searchBar, bookScrollPane, paginationBar, backButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Scene for the View Books page
        Scene booksScene = new Scene(layout, 600, 400);
        primaryStage.setScene(booksScene);
        primaryStage.show();
    }

    
    
    private void populateBooks(VBox bookList, String searchText, String category) {
		bookList.getChildren().clear();
		String query = "SELECT id, title, author, published_year, condition, price FROM Books Where 1=1";
		
		boolean hasSearchText = searchText != null && !searchText.trim().isEmpty();
		boolean hasCategoryFilter = category != null && !"All Categories".equals(category);
		
		if (hasSearchText) {
			query += " AND title LIKE ?";
		}
		if (hasCategoryFilter) {
			query += " AND category = ?";
		}
		
		
		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			int paramIndex = 1;
			
			if (hasSearchText) {
				stmt.setString(paramIndex++, "%" + searchText.trim() + "%");
			}
			if (hasCategoryFilter) {
				stmt.setString(paramIndex++, category);
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String title = rs.getString("title");
				String author = rs.getString("author");
				int year = rs.getInt("published_year");
				String condition = rs.getString("condition");
				double price = rs.getDouble("price");
				int bookId = rs.getInt("id");
				//Now add them to the book item!
				HBox bookItem = createBookItem(title, author, year, condition, price, bookId);
				bookList.getChildren().add(bookItem);
			}
			if (bookList.getChildren().isEmpty()) {
				Label noBooksLabel = new Label("No books found");
				noBooksLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: grey;");
				bookList.getChildren().add(noBooksLabel);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private HBox createBookItem(String title, String author, int year, String condition, double price, int bookId) {
		Label bookDetails = new Label(String.format("%s\nAuthor: %s | Published: %d\nCondtion: %s",  title, author, year, condition));
		bookDetails.setStyle("-fx-font-size: 14px");
		
		Label bookPrice = new Label(String.format("$%.2f",  price));
		bookPrice.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
		
	

		
		HBox bookItem = new HBox(10, bookDetails, bookPrice);
		bookItem.setPadding(new Insets(10));
		bookItem.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: #f9f9f9;");
		return bookItem;
	}

    public static void main(String[] args) {
        launch(args);
    }
}
