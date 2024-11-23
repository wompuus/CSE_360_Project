package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Random;

public class SellerPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title Bar with red background and white text
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

        ToggleButton buyerSellerToggle = new ToggleButton("SELLER");
        buyerSellerToggle.setSelected(true);
        buyerSellerToggle.setDisable(true);

        logOutButton.setMinHeight(40);
        buyerSellerToggle.setMinHeight(40);
        myAccountButton.setMinHeight(40);

        logOutButton.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        buyerSellerToggle.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        myAccountButton.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        navBar.getChildren().addAll(logOutButton, buyerSellerToggle, myAccountButton);

        // Event handler for the "Log Out" button
        logOutButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();  // Launch LoginPage
            loginPage.start(primaryStage);  // Close the current SellerPage
            primaryStage.close();
        });

        // Event handler for the "My Account" button
        myAccountButton.setOnAction(e -> {
            CurrentUser.openCurrentUserPage();  // Open the CurrentUser page
            primaryStage.close();  // Close the current SellerPage window
        });

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

        Text rect1Text = new Text("Tell us about the book you are selling");
        rect1Text.setFont(new Font(14));
        rect1Text.setFill(Color.WHITE);

        HBox titleAuthorBox = new HBox(10);
        titleAuthorBox.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("Title");
        TextField titleField = new TextField();

        Label authorLabel = new Label("Author");
        TextField authorField = new TextField();

        Label dateLabel = new Label("Date Published");
        TextField datePicker = new TextField(); // Replaced DatePicker with TextField

        titleAuthorBox.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, dateLabel, datePicker);
        rect1Content.getChildren().addAll(rect1Text, titleAuthorBox);
        rectanglesColumn.getChildren().add(rect1Content);

        // Radio Buttons for book category
        VBox rect2Content = new VBox(10);
        rect2Content.setStyle("-fx-background-color: gray; -fx-min-width: 200px; -fx-padding: 15px;");
        rect2Content.setAlignment(Pos.CENTER_LEFT);

        Text rect2Text = new Text("What kind of Book are you selling?");
        rect2Text.setFont(new Font(14));
        rect2Text.setFill(Color.WHITE);

        ToggleGroup bookTypeGroup = new ToggleGroup();

        RadioButton fictionButton = new RadioButton("Fiction");
        fictionButton.setToggleGroup(bookTypeGroup);
        RadioButton nonFictionButton = new RadioButton("Non-Fiction");
        nonFictionButton.setToggleGroup(bookTypeGroup);
        RadioButton mathButton = new RadioButton("Math");
        mathButton.setToggleGroup(bookTypeGroup);
        RadioButton scienceButton = new RadioButton("Science");
        scienceButton.setToggleGroup(bookTypeGroup);
        RadioButton fantasyButton = new RadioButton("Fantasy");
        fantasyButton.setToggleGroup(bookTypeGroup);

        rect2Content.getChildren().addAll(rect2Text, fictionButton, nonFictionButton, mathButton, scienceButton, fantasyButton);
        rectanglesColumn.getChildren().add(rect2Content);

        // ComboBox for book condition
        VBox rect3Content = new VBox(10);
        rect3Content.setStyle("-fx-background-color: gray; -fx-min-width: 200px; -fx-padding: 15px;");
        rect3Content.setAlignment(Pos.CENTER_LEFT);

        Text rect3Text = new Text("What condition is your book in?");
        rect3Text.setFont(new Font(14));
        rect3Text.setFill(Color.WHITE);

        ComboBox<String> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll("Used Like New", "Moderately Used", "Heavily Used");

        HBox rect3HBox = new HBox(10);
        rect3HBox.setAlignment(Pos.CENTER_LEFT);
        rect3HBox.getChildren().addAll(rect3Text, conditionComboBox);

        rect3Content.getChildren().add(rect3HBox);
        rectanglesColumn.getChildren().add(rect3Content);

        // Listing price section
        VBox rect4Content = new VBox(10);
        rect4Content.setStyle("-fx-background-color: gray; -fx-min-width: 200px; -fx-padding: 15px;");
        rect4Content.setAlignment(Pos.CENTER_LEFT);

        Text rect4Text = new Text("Set your listing price");
        rect4Text.setFont(new Font(14));
        rect4Text.setFill(Color.WHITE);

        Label listingPriceLabel = new Label("Price ($)");
        TextField listingPriceField = new TextField();

        HBox rect4HBox = new HBox(10);
        rect4HBox.setAlignment(Pos.CENTER_LEFT);
        rect4HBox.getChildren().addAll(listingPriceLabel, listingPriceField);

        rect4Content.getChildren().add(rect4HBox);
        rectanglesColumn.getChildren().add(rect4Content);

        // List It Button
        Button listItButton = new Button("List It");
        listItButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px;");
        listItButton.setMinWidth(200);

        listItButton.setOnAction(e -> {
            try {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String datePublished = datePicker.getText().trim();
                String condition = conditionComboBox.getValue();
                String bookType = "";

                if (fictionButton.isSelected()) bookType = "Fiction";
                else if (nonFictionButton.isSelected()) bookType = "Non-Fiction";
                else if (mathButton.isSelected()) bookType = "Math";
                else if (scienceButton.isSelected()) bookType = "Science";
                else if (fantasyButton.isSelected()) bookType = "Fantasy";

                String price = listingPriceField.getText().trim();

                // Input validation
                if (title.isEmpty() || author.isEmpty() || datePublished.isEmpty() || condition == null || bookType.isEmpty() || price.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled out.");
                }

                // Try parsing price to ensure it's a valid number
                double parsedPrice;
                try {
                    parsedPrice = Double.parseDouble(price);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Please enter a valid price.");
                }

                // Random book ID (simulated)
                Random random = new Random();
                int bookId = random.nextInt(1000) + 1;

                // Call the method to list the book
                listBook(bookId, title, author, datePublished, bookType, condition, price, 1); // Assume sellerId = 1 for now

                // Show success alert
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Book Listed");
                alert.setContentText("The book has been listed successfully!");
                alert.showAndWait();

                // Clear fields after listing
                titleField.clear();
                authorField.clear();
                datePicker.clear();
                conditionComboBox.getSelectionModel().clearSelection();
                fictionButton.setSelected(false);
                nonFictionButton.setSelected(false);
                mathButton.setSelected(false);
                scienceButton.setSelected(false);
                fantasyButton.setSelected(false);
                listingPriceField.clear();

            } catch (IllegalArgumentException ex) {
                // Show error message for invalid input
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                // Catch other exceptions (e.g., database issues)
                ex.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Unable to list the book");
                alert.setContentText("Please try again later.");
                alert.showAndWait();
            }
        });

        // Add all UI components to the VBox
        whiteBox.getChildren().addAll(rectanglesColumn, listItButton);
        VBox mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(titleBar, navBar, whiteBox);

        // Set up the stage
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("Seller Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to list the book in the database (dummy method for now)
    public void listBook(int id, String title, String author, String datePublished, String bookType, String condition, String price, int sellerId) {
        // Database logic to insert the book details
        System.out.println("Book listed: " + title + " by " + author);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
