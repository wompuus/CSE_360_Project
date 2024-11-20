package application;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SellerPage extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Label title = new Label("Seller Dashboard");
		title.setStyle("-fx-font-size: 24px");
		
		Button listBooksButton = new Button("List Books");
		Button orderHistoryButton = new Button("Order History");
		Button logoutButton = new Button("Logout");
		
		logoutButton.setOnAction(e-> new LoginPage().start(primaryStage));
		
		VBox Layout = new VBox(10, title, listBooksButton, orderHistoryButton, logoutButton);
		Layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
		
		Scene buyerScene = new Scene(Layout, 400, 300);
		primaryStage.setTitle("Seller Dashboard");
		primaryStage.setScene(buyerScene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}