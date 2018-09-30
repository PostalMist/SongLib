package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.ListController;


public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("../view/Lib.fxml"));
		System.out.println("passed here");
		AnchorPane root = (AnchorPane)loader.load();

		System.out.println("passed here 2");
		ListController listController = loader.getController();
		listController.start(primaryStage);

		Scene scene = new Scene(root, 600, 320);
	    
		primaryStage.setTitle("Song Library");
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}