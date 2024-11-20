package application;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AdminPage extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Label title = new Label("Admin Dashboard");
		title.setStyle("-fx-font-size: 24px");
		
		Button viewBooksButton = new Button("View Books");
		Button orderHistoryButton = new Button("Order History"); // Need to update. Just a copy paste of seller at the moment
		Button logoutButton = new Button("Logout");// Need to update. Just a copy paste of seller at the moment
		
		logoutButton.setOnAction(e-> new LoginPage().start(primaryStage));
		
		VBox Layout = new VBox(10, title, viewBooksButton, orderHistoryButton, logoutButton);// Need to update. Just a copy paste of seller at the moment
		Layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
		
		Scene buyerScene = new Scene(Layout, 400, 300);
		primaryStage.setTitle("Admin Dashboard");
		primaryStage.setScene(buyerScene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}