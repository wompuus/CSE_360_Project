package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // ** Step 1: Create UI Components **
        // Title Label - Displays the name of the app - website
        Label title = new Label("Sun Devil Book Buying Thingy");
        title.setStyle("-fx-font-size: 24px;"); // Add some style to make it stand out

        // TextField for ASU ID input
        TextField asuIdField = new TextField();
        asuIdField.setPromptText("ASU ID"); // Placeholder text

        // PasswordField for password input
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password"); // Placeholder text

        // Login button
        Button loginButton = new Button("Login");

        // Label to display error messages (e.g., incorrect login)
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;"); // Red text for error messages

        // Hyperlink for the "Forgot Password?" option
        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");

        // ** Step 2: Set up Layout (VBox) **
        // VBox organizes the components vertically with 10px spacing
        VBox layout = new VBox(10, title, asuIdField, passwordField, loginButton, errorLabel, forgotPasswordLink);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;"); // Center-align components and add padding

        // ** Step 3: Add Event Handlers **
        // Handle the login button click event
        loginButton.setOnAction(e -> {
            String asuId = asuIdField.getText();        // Get the ASU ID from the text field
            String password = passwordField.getText();  // Get the password from the password field

            if (validateLogin(asuId, password)) {
                // If login is successful, clear error message
                errorLabel.setText("Login successful!"); // need to set this to null, just easier than reading console
                System.out.println("Login successful!"); // For now, just print a message
            } else {
                // Display error message if login fails
                errorLabel.setText("Incorrect username and/or password");
            }
        });

        // Handle the forgot password link click event
        forgotPasswordLink.setOnAction(e -> {
            ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage();  // Create new ForgotPasswordPage instance
            forgotPasswordPage.start(primaryStage); // Transition to the Forgot Password page
        });

        // ** Step 4: Set up and Show Scene **
        // Create a Scene with the layout (VBox) and set the window size
        Scene loginScene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Login"); // Set the title of the window
        primaryStage.setScene(loginScene); // Set the scene on the primary stage
        primaryStage.show(); // Display the window
    }

    // ** Helper method to validate login credentials **
    // This is where we need to connect to our database, if we do a text document, we should do it in dictionary style? maybe map and key? 
    private boolean validateLogin(String asuId, String password) {
        // Simple hardcoded validation
        return "123456".equals(asuId) && "password".equals(password);
    }
    public static void main(String[] args) {
        launch(args); // This launches the JavaFX application, starting with LoginPage
    }
}


