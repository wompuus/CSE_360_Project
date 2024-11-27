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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BuyerPage extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Label title = new Label("Buyer Dashboard");
		title.setStyle("-fx-font-size: 24px");
		
		//Style to make it look like the PDF
		// Step 1: Add in the top nava bar!
		HBox navBar = new HBox(10); 
		navBar.setPadding(new Insets(10));
		Button homeButton = new Button("Home");
		Button myAccountButton = new Button("My Account");
		Button orderHistoryButton = new Button("Order History");
		ToggleButton buyerSellerToggle = new ToggleButton("BUYER");
		buyerSellerToggle.setSelected(true);
		buyerSellerToggle.setDisable(true); // For now sellers won't toggle here
		Button cartButton = new Button("Cart");
		navBar.getChildren().addAll(homeButton, myAccountButton, orderHistoryButton, buyerSellerToggle, cartButton);
		
		
		cartButton.setOnAction(e-> {
			new CartPage().start(primaryStage);
		});
		
		// Search Bar!
		HBox searchBar = new HBox(10);
		TextField searchField = new TextField();
		searchField.setPromptText("Search books..");
		ComboBox<String> categoryComboBox = new ComboBox<>();
		categoryComboBox.getItems().addAll("All Categories", "Natural Science", "Computer", "Mathmatics");
		categoryComboBox.setValue("All categories");
		Button searchButton = new Button("Search");
		searchBar.getChildren().addAll(searchField, categoryComboBox, searchButton);
		
		
		//Book listings
		VBox bookList = new VBox(10);
		bookList.setPadding(new Insets(10));
		ScrollPane bookScrollPane = new ScrollPane(bookList);
		bookScrollPane.setFitToWidth(true);

		
		// pagination
		HBox paginationBar = new HBox(10);
		paginationBar.setPadding(new Insets(10));
		paginationBar.setStyle("-fx-alignment: center;");
		
		for (int i = 1; i <= 5; i++) {
			Button pageButton = new Button(String.valueOf(i));
			paginationBar.getChildren().add(pageButton);
		}
		
		
		populateBooks(bookList, null, "All Categories"); 
		
		searchButton.setOnAction(e-> {
			String searchText = searchField.getText();
			String selectedCategory = categoryComboBox.getValue();
			populateBooks(bookList, searchText, selectedCategory);
			searchField.setText(null);
		});
		
		
		
		
		
		
		Button browseBooksButton = new Button("Browse Books");

		Button logoutButton = new Button("Logout");
		
		logoutButton.setOnAction(e-> new LoginPage().start(primaryStage));
		
		VBox Layout = new VBox(10, navBar, searchBar, bookScrollPane, paginationBar);
		Layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
		
		Scene buyerScene = new Scene(Layout, 600, 400);
		primaryStage.setTitle("Buyer Dashboard");
		primaryStage.setScene(buyerScene);
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
		
		Button addToCartButton = new Button("Add to cart");
		addToCartButton.setOnAction(e-> {
			//what happens when you try and add it to the cart
			if(addToCart(bookId)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book added to cart!", ButtonType.OK);
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Failed to add book to cart.", ButtonType.OK);
				alert.showAndWait();
			}
		});

		
		HBox bookItem = new HBox(10, bookDetails, bookPrice, addToCartButton);
		bookItem.setPadding(new Insets(10));
		bookItem.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: #f9f9f9;");
		return bookItem;
	}
	
	private boolean addToCart(int bookId) {
		String query = "INSERT INTO Cart (book_id, buyer_id) VALUES (?, ?)";
		
		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, bookId);
			stmt.setInt(2, CurrentUser.getId());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
			
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}