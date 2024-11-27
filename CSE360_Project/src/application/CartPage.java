package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CartPage extends Application {

    @Override
    public void start(Stage primaryStage) {
    	HBox titleBar = new HBox();
    	titleBar.setStyle("-fx-background-color: red; -fx-padding: 10;");

    	Text titleText = new Text("SunDevil Book Buying Website");
    	titleText.setFill(Color.WHITE);
    	titleText.setFont(new Font(50));
    	titleBar.getChildren().add(titleText);

    		// Top Navbar
    	HBox navBar = new HBox(20);
    	navBar.setPadding(new Insets(15, 20, 15, 20));
    	navBar.setStyle("-fx-background-color: white; -fx-min-height: 60px;");
    	navBar.setAlignment(Pos.CENTER_LEFT);

    	Button logOutButton = new Button("Log Out");
    	Button myAccountButton = new Button("My Account");
    	Button mainPageButton = new Button("Main Page");

    	ToggleButton buyerSellerToggle = new ToggleButton("BUYER");
    	buyerSellerToggle.setSelected(true);
    	buyerSellerToggle.setDisable(true);

    	navBar.getChildren().addAll(logOutButton, buyerSellerToggle, myAccountButton, mainPageButton);

    	// Event Handlers for Navbar Buttons
    	logOutButton.setOnAction(e -> new LoginPage().start(primaryStage));
    	mainPageButton.setOnAction(e -> new BuyerPage().start(primaryStage));
    	myAccountButton.setOnAction(e -> {
    		// Open My Account (not implemented yet)
    		primaryStage.close();
    	});

    	// Book Listings
    	VBox bookList = new VBox(10);
    	bookList.setPadding(new Insets(10));
    	ScrollPane bookScrollPane = new ScrollPane(bookList);
    	bookScrollPane.setFitToWidth(true);

    	populateCart(bookList);
    	
    	Button BuyButton = new Button("Buy");
        BuyButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px;");
        BuyButton.setMinWidth(200);
        
        BuyButton.setOnAction(e -> {
            if (purchaseBooks()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Books Purchased");
                alert.setContentText("Your purchase was successful!");
                alert.showAndWait();
                populateCart(bookList); // Refresh the cart after purchase
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Purchase Failed");
                alert.setContentText("Unable to complete your purchase. Please try again.");
                alert.showAndWait();
            }
        });

    	// Main Layout
    	VBox mainLayout = new VBox(20);
    	mainLayout.getChildren().addAll(titleBar, navBar, bookScrollPane, BuyButton);

    	Scene scene = new Scene(mainLayout, 450,450);
    	primaryStage.setTitle("Shopping Cart");
    	primaryStage.setScene(scene);
    	primaryStage.show();
    	}

    private void populateCart(VBox bookList) {
    	bookList.getChildren().clear();
    		// SQL query to fetch cart items for the active user
    	String query = """
    			SELECT b.title, b.author, b.published_year, b.condition, b.price, b.id
    			FROM Cart c
    			JOIN Books b ON c.book_id = b.id
    			WHERE c.buyer_id = ?
    			""";
    	try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
    		// need to use the active user's ID
    		stmt.setInt(1, CurrentUser.getId());
    		ResultSet rs = stmt.executeQuery();

    		while (rs.next()) {
    			String title = rs.getString("title");
    			String author = rs.getString("author");
    			int year = rs.getInt("published_year");
    			String condition = rs.getString("condition");
    			double price = rs.getDouble("price");
    			int bookId = rs.getInt("id");

// ----------------- Now to add the item to the book list
    			HBox bookItem = createBookItem(title, author, year, condition, price, bookId);
    			bookList.getChildren().add(bookItem);
    			}

// --------------- If no items are in the cart, display a message
    		if (bookList.getChildren().isEmpty()) {
    			Label noBooksLabel = new Label("Your cart is empty.");
    			noBooksLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: grey;");
    			bookList.getChildren().add(noBooksLabel);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    private HBox createBookItem(String title, String author, int year, String condition, double price, int bookId) {
    	Label bookDetails = new Label(String.format("%s\nAuthor: %s | Published: %d\nCondition: %s", title, author, year, condition));
    	bookDetails.setStyle("-fx-font-size: 14px");
    	Label bookPrice = new Label(String.format("$%.2f", price));
    	bookPrice.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

    	Button removeButton = new Button("Remove");
    	removeButton.setOnAction(e -> {
    	
    	if (removeFromCart(bookId)) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		alert.setTitle("Success");
    		alert.setHeaderText("Book Removed");
    		alert.setContentText("The book has been removed from your cart.");
    		alert.showAndWait();
    		populateCart((VBox) bookDetails.getParent().getParent());
    		} else {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText("Removal Failed");
    			alert.setContentText("Unable to remove the book. Please try again.");
                alert.showAndWait();
                }
    	});

    	HBox bookItem = new HBox(10, bookDetails, bookPrice, removeButton);
    	bookItem.setPadding(new Insets(10));
    	bookItem.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: #f9f9f9;");
    	return bookItem;
    	}

    private boolean removeFromCart(int bookId) {
    	String query = "DELETE FROM Cart WHERE book_id = ? AND buyer_id = ?";

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

    private boolean purchaseBooks() {
    	String removeBooksQuery = "DELETE FROM Books WHERE id IN (SELECT book_id FROM Cart WHERE buyer_id = ?)";
    	String clearCartQuery = "DELETE FROM Cart WHERE buyer_id = ?";
    	try (Connection conn = DatabaseConnection.getConnection()) {
    		conn.setAutoCommit(false); 

    		try (PreparedStatement removeBooksStmt = conn.prepareStatement(removeBooksQuery); PreparedStatement clearCartStmt = conn.prepareStatement(clearCartQuery)) {
    			removeBooksStmt.setInt(1, CurrentUser.getId());
    			clearCartStmt.setInt(1, CurrentUser.getId());
    			removeBooksStmt.executeUpdate();
    			clearCartStmt.executeUpdate();
    			conn.commit(); // Commit transaction
    			return true;
    		} catch (Exception e) {
    			conn.rollback(); // Rollback transaction on failure
    			e.printStackTrace();
    			return false;
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    			return false;
    		}
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}