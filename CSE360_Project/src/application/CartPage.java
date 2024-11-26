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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class CartPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: red; -fx-padding: 10;");

        Text titleText = new Text("SunDevil Book Buying Website");
        titleText.setFill(Color.WHITE);  // White text color
        titleText.setFont(new Font(50));  // Increased font size for a taller title

        titleBar.getChildren().add(titleText);

        // Step 1: Add in the top navbar with white background
        HBox navBar = new HBox(20);
        navBar.setPadding(new Insets(15, 20, 15, 20));  // Padding around navbar
        navBar.setStyle("-fx-background-color: white; -fx-min-height: 60px;");
        navBar.setAlignment(Pos.CENTER_LEFT);

        Button logOutButton = new Button("Log Out");
        Button myAccountButton = new Button("My Account");
        Button mainPageButton = new Button("Main Page");

        ToggleButton buyerSellerToggle = new ToggleButton("BUYER");
        buyerSellerToggle.setSelected(true);
        buyerSellerToggle.setDisable(true);

        logOutButton.setMinHeight(40);
        buyerSellerToggle.setMinHeight(40);
        myAccountButton.setMinHeight(40);
        mainPageButton.setMinHeight(40);


        logOutButton.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        buyerSellerToggle.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        myAccountButton.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        mainPageButton.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        navBar.getChildren().addAll(logOutButton, buyerSellerToggle, myAccountButton, mainPageButton);

        // Event handler for the "Log Out" button
        logOutButton.setOnAction(e-> new LoginPage().start(primaryStage));

        // Event handler for the "My Account" button
        myAccountButton.setOnAction(e -> {
            //CurrentUser.openCurrentUserPage();  // Open the CurrentUser page
            primaryStage.close();  // Close the current Cart window
        });
        // Event handler for the "Main Page" button
        mainPageButton.setOnAction(e -> new BuyerPage().start(primaryStage));


        // Layout for the book selling form
        VBox whiteBox = new VBox(20);
        whiteBox.setStyle("-fx-background-color: white; -fx-padding: 40px;");
        whiteBox.setVgrow(whiteBox, Priority.ALWAYS);

        VBox rectanglesColumn = new VBox(20);
        rectanglesColumn.setAlignment(Pos.CENTER);
        rectanglesColumn.setPadding(new Insets(20));

        // Rectangles for book info
        VBox rect1Content = new VBox(10);
        rect1Content.setStyle("-fx-background-color: gray; -fx-min-width: 200px; -fx-padding: 15px;");
        rect1Content.setAlignment(Pos.CENTER_LEFT);

        Text rect1Text = new Text("Shopping Cart");
        rect1Text.setFont(new Font(14));
        rect1Text.setFill(Color.WHITE);

        // Call the method to get the book
        int bookID = 1; // should be passed along from when the cart is
        String[] book = getBook(bookID); // sample of getting the book from database


        // GRIDPANE FOR CART LISTINGS: EDIT ONCE DATABASE IS SETUP

        Text itemNumber = new Text("1"); //internal count for books in cart



        Text bookTitle = new Text(book[0]);
        Text author = new Text(book[1]);
        Text date = new Text(book[2]);
        Text condition = new Text(book[3]);
        Text bookType = new Text(book[4]);
        Text price = new Text(book[5]);
        Text uniqueBookID = new Text(book[6]);
        Text sellerID = new Text(book[7]);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setStyle("-fx-background-color: gray; -fx-min-width: 200px; -fx-padding: 15px;");
        grid.setMinSize(100, 100);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(60);


        grid.add(bookTitle, 0, 0);
        grid.add(author, 1, 0);
        grid.add(date, 2, 0);
        grid.add(condition, 3, 0);
        grid.add(bookType, 4, 0);
        grid.add(price, 5, 0);


        GridPane grid1 = new GridPane();
        grid1.setAlignment(Pos.CENTER_LEFT);
        grid1.setStyle("-fx-background-color: lightgray; -fx-min-width: 200px; -fx-padding: 15px;");
        grid1.setMinSize(100, 100);
        grid1.setPadding(new Insets(20, 20, 20, 20));
        grid1.setVgap(10);
        grid1.setHgap(60);


        grid1.add(bookTitle, 0, 0);
        grid1.add(author, 1, 0);
        grid1.add(date, 2, 0);
        grid1.add(condition, 3, 0);
        grid1.add(bookType, 4, 0);
        grid1.add(price, 5, 0);




        // Buy Button
        Button BuyButton = new Button("Buy");
        BuyButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px;");
        BuyButton.setMinWidth(200);

        BuyButton.setOnAction(e -> {
            try {
                // Show success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Book Purchased");
                alert.setContentText("The book has been purchased successfully!");
                alert.showAndWait();


            } catch (Exception ex) { //error message if book is not available for purchased
                // Catch other exceptions (e.g., database issues)
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Unable to buy the book");
                alert.setContentText("Please try again later.");
                alert.showAndWait();
            }
        });

        // Add all UI components to the VBox
        whiteBox.getChildren().add(grid);
        whiteBox.getChildren().add(grid1);
        whiteBox.getChildren().addAll(rectanglesColumn, BuyButton);

        VBox mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(titleBar, navBar, whiteBox);


        // Set up the stage
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("Shopping Cart");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public String[] getBook(int id) {
        // Database logic to insert the book details


        String[] newBook;//  = new String[8];//8 items for: 0-title, 1-author, 2-date,3-condition, 4-book type,
        //5-price, 6-bookId, 7- sellerID


        newBook = new String[]{"Cook Book", "Mary Something", "10/20/2020", "New", "Science", "$100", "1", "1"};
        System.out.println("Book with id: " + id + " has been added to the cart");

        return newBook;
    }

    public static void main(String[] args) {
        launch();
    }

}