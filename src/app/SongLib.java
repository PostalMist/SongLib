package app;

import java.io.IOException;

import database_interface.Database;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import library.SongLibrary;
import view.ListController;

public class SongLib extends Application {
	private static SongLibrary lib;
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
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			
			@Override
			public void handle(WindowEvent event)
			{
				Database.save(lib);
			}
		});
	}
	public static void main(String[] args) {
		lib = new SongLibrary();
		launch(args);		
	}

}
