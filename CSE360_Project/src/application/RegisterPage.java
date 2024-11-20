package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement; // have some documentation on preparedstatements on the login page

public class RegisterPage extends Application {
	
	@Override
	public void start(Stage primaryStage ) {
		// Need to make all the UI components
		Label title = new Label("Register New User");
		title.setStyle("-fx-font-size: 24px");
		
		TextField asuIdField = new TextField();
		asuIdField.setPromptText("ASU ID");
		
		PasswordField passwordField = new PasswordField(); // Remember, password fields are different than text fields
		passwordField.setPromptText("Password");
		
		ComboBox<String> roleComboBox = new ComboBox<>();
		roleComboBox.getItems().addAll("buyer", "seller", "admin");
		roleComboBox.setPromptText("Select Role(s)");
		
		TextField emailField = new TextField();
		emailField.setPromptText("Email");
		
		
		Button registerButton = new Button("Register");
		Label statusLabel = new Label();
		
		
		
        // Button to go back to the login page
        Button backToLoginButton = new Button("Back to Login");
        // Handle the back to login button click event
        backToLoginButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();  // Create new LoginPage instance
            loginPage.start(primaryStage); // Transition back to the Login page
        });
        
		
		VBox layout = new VBox(10, title, asuIdField, passwordField, roleComboBox, emailField, registerButton, statusLabel, backToLoginButton);
		layout.setStyle("-fx-alignment: center; -fx-padding: 15");
		// ------------------------- END OF UI COMPONENTS--------------------
		
		// --------------------------Event handling --------------------------
		registerButton.setOnAction(e-> {
			String asuId = asuIdField.getText();
			String password = passwordField.getText();
			String role = roleComboBox.getValue();
			String email = emailField.getText();
			
			if (asuId.isEmpty() || password.isEmpty() || role == null || email.isEmpty()) {
				statusLabel.setText("All fields are required.");
				statusLabel.setStyle("-fx-text-fill: red;");
			} else if (registerUser(asuId, password, role, email)) {
				statusLabel.setText("Registration successful!");
				statusLabel.setStyle("-fx-text-fill: green;");
			} else {
				statusLabel.setText("Error: Could not register user.");
				statusLabel.setStyle("-fx-text-fill: red;");
			}
		});
	
		// --------------------------SET THE SCENE ---------------------------
		Scene registerScene = new Scene(layout, 400, 400);
		primaryStage.setTitle("Register");
		primaryStage.setScene(registerScene);
		primaryStage.show();
	}
	
	// ----------------------- Our functions to get this going. Test the DB and see if we can register
	private boolean registerUser(String asuId, String password, String role, String email) {
		String insertQuery = "INSERT INTO Users (asu_id, password, role, email) VALUES (?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
			stmt.setString(1, asuId);
			stmt.setString(2, password);
			stmt.setString(3, role);
			stmt.setString(4, email);
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
//  try (Connection conn = DatabaseConnection.getConnection();
//  Statement stmt = conn.createStatement()) {
// stmt.executeUpdate(insertUsers);
// System.out.println("Test data inserted successfully.");
//} catch (Exception e) {
// e.printStackTrace();
//}
	
	
	
	
	
	private void clearFields(TextField asuIdField, PasswordField passwordField, ComboBox<String> roleComboBox, TextField emailField) {
		asuIdField.clear();
		passwordField.clear();
		roleComboBox.getSelectionModel().clearSelection();
		emailField.clear();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}