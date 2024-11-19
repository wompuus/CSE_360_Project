module CSE360_HW1 {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.graphics;
	requires java.sql;
	 
	opens application to javafx.graphics, javafx.fxml;
}
