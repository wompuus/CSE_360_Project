package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ForgotPasswordPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Createing UI Components **
        // Title Label - shows that this is the forgot password page
        Label title = new Label("Forgot Password"); // make the label and give it text
        title.setStyle("-fx-font-size: 24px;"); // Add some style to make it stand out

        // TextField for entering the user name
        TextField asuIdField = new TextField();
        asuIdField.setPromptText("Enter ASU ID"); // Placeholder text to guide the user 

        // Submit button
        Button submitButton = new Button("Submit");

        // Label to display error or success messages
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;"); // Red text for error messages

        // Button to go back to the login page
        Button backToLoginButton = new Button("Back to Login");

        // Set up Layout (VBox) **
        // VBox organizes the components vertically with 10px spacing
        VBox layout = new VBox(10, title, asuIdField, submitButton, errorLabel, backToLoginButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;"); // Center-align components and add padding

        // Add Event Handlers **
        // Handle the submit button click event
        submitButton.setOnAction(e -> {
            String asuId = asuIdField.getText();  // Get the ASU ID from the text field

            if (validateAsuId(asuId)) {
                // If valid ASU ID, show success message
                errorLabel.setText("Password recovery email sent!");
            } else {
                // Display error message for invalid ASU ID
                errorLabel.setText("Invalid ASU ID");
            }
        });

        // Handle the back to login button click event
        backToLoginButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();  // Create new LoginPage instance
            loginPage.start(primaryStage); // Transition back to the Login page
        });
        
        // All of the UI creation is done here!!!!! This is what is running to create the scene. 
        
        // Set up and Show Scene **
        // Create a Scene with the layout (VBox) and set the window size
        Scene forgotPasswordScene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Forgot Password"); // Set the title of the window
        primaryStage.setScene(forgotPasswordScene); // Set the scene on the primary stage
        primaryStage.show(); // Display the window
    }

    // ** Helper method to validate the ASU ID **
    // This will need to contain some way to validate that the username is a valid ASU email address. probably will use CFG logic from cse355
    private boolean validateAsuId(String asuId) {
        // Simple hardcoded logic, something to make sure the function plays
        return "123456".equals(asuId);
    }
    public static void main(String[] args) {
        launch(args); // This launches the JavaFX application, starting with LoginPage
    }
}


