package view;


import java.io.FileNotFoundException;
import java.util.Optional;

//import java.util.Optional;

//import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.stage.WindowEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;

//import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import res.Song;
import res.SongRecorder;

public class ListController {

   //@FXML ListView<String> listView;    
	@FXML ListView<Song> listView;
   @FXML TextField titleText;
   @FXML TextField artistText;
   @FXML TextField albumText;
   @FXML TextField yearText;
   @FXML Button addButton;
   @FXML Button editButton;
   @FXML Button deleteButton;
   
   
	//private ObservableList<String> obsList;   
	private ObservableList<Song> songlist;
	
	SongRecorder sr = new SongRecorder();

	public void start(Stage mainStage) {                
		// create an ObservableList 
		// from an ArrayList  
	/*	obsList = FXCollections.observableArrayList(                               
				"Giants",                               
				"Patriots",
				"49ers",
				"Rams",
				"Packers",
				"Colts",
				"Cowboys",
				"Broncos",
				"Vikings",
				"Dolphins",
				"Titans",
				"Seahawks",
				"Steelers",
				"Jaguars"); */
      //  Song s = new Song("In my Feelings","Drake","Scorpion",2018);
      //  Song s1 = new Song("Ye","Burna Boy","Outside",2018);
        
        //songlist = FXCollections.observableArrayList(s,s1);
        try{
        songlist = sr.read();
        }catch(FileNotFoundException e){
        	e.printStackTrace();
        }
        songlist.sort(Song.BY_TITLE_ARTIST);
        
		//listView.setItems(obsList); 
        listView.setItems(songlist);
        listView.setEditable(true);
		
		// select the first item
	      listView.getSelectionModel().select(0);
	      //titleText.setText(listView.getSelectionModel().getSelectedItem().title);
	      showSong(mainStage);

	      // set listener for the items
	      listView
	        .getSelectionModel()
	        .selectedIndexProperty()
	        .addListener(
	           (obs, oldVal, newVal) -> 
	               showSong(mainStage));
	     /* listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>()
	    
	    	        {
	    	            public void changed(ObservableValue<? extends Song> ov,
	    	                    final Song oldvalue, final Song newvalue){
	    	                songChanged(ov, oldvalue, newvalue);
	    	                }});*/
	  /*  EventHandler<WindowEvent> we =  new EventHandler<WindowEvent>(){
    		  public void handle(WindowEvent we){
    			  if(we.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST)
    			  System.out.println("It is closed " + we.getEventType());
    			  
    		  }
    		  
      };*/
	      
	      sr.getList(songlist);
	      mainStage.setOnCloseRequest(sr);


	}
	
	/*private void showItem(Stage mainStage) {                
	      Alert alert = 
	         new Alert(AlertType.INFORMATION);
	      //alert.initModality(Modality.NONE);
	      alert.initOwner(mainStage);
	      alert.setTitle("List Item");
	      alert.setHeaderText(
	           "Selected list item properties");

	      String content = "Index: " + 
	          listView.getSelectionModel()
	                   .getSelectedIndex() + 
	          "\nValue: " + 
	          listView.getSelectionModel()
	                   .getSelectedItem();

	          alert.setContentText(content);
	          alert.showAndWait();
	          //System.out.println("not blocking");
	   }*/
	
	private void showSong(Stage mainStage) {                
	     printToTextfield();
	      //TextInputDialog dialog = new TextInputDialog(item);
	   //   dialog.initOwner(mainStage); dialog.setTitle("List Item");
	     // dialog.setHeaderText("Selected Item (Index: " + index + ")");
	    //  dialog.setContentText("Enter name: ");

	     // Optional<String> result = dialog.showAndWait();
	     // if (result.isPresent()) { obsList.set(index, result.get()); }
	      
	      
	   }
	
	public void transaction(ActionEvent e){
		//method adds a song to the library by adding it to the observable list
		
		Button b = (Button) e.getSource();
		if(b == addButton){
		/******************************************************************************
		 * Adding new songs to the library
		 * 	
		 ******************************************************************************/
			
			//populate the data
			
		    String title = titleText.getText();
			String artist = artistText.getText();
			String album = albumText.getText();
			String year = yearText.getText();
			
			if(isValid(year)){
				Song s = new Song();
				if(year == null || year.equals("")){
					s.year = -1;
				}else{
				s.year = Integer.parseInt(year);
				}
				s.title = title;
				s.artist = artist;
				s.album = album;
				
				if(songlist.contains(s)){
					//check if already in the observable list before inserting
					// put up an alert
					
					Alert invalidEntryAlert = new Alert(AlertType.ERROR);
					 invalidEntryAlert.setTitle("ERROR");
					 invalidEntryAlert.setHeaderText("Invalid entry");
					 invalidEntryAlert.setContentText("This Song already exists");
					 invalidEntryAlert.showAndWait();
				
				}else{
				songlist.add(s);
				songlist.sort(Song.BY_TITLE_ARTIST);
				//Collections.sort(songlist, Song.BY_TITLE_ARTIST);
				//listView.setItems(null);
			   // listView.setItems(songlist);
				//System.out.println(songlist);
				}
				
				int index = songlist.indexOf(s);
				listView.getSelectionModel().select(index);
			}else{
				
				Alert invalidInputAlert = new Alert(AlertType.ERROR);
				invalidInputAlert.setTitle("ERROR");
				invalidInputAlert.setHeaderText("Invalid datatype");
				invalidInputAlert.setContentText("The year textbox only accepts positive integers");
				invalidInputAlert.showAndWait();

			}
			
			
			
			
			
		}else if(b == editButton){
			/********************************************************************
			 * 
			 * Editing an existing song in library
			 * 
			 *********************************************************************/
			
			int index = listView.getSelectionModel().getSelectedIndex();
			
			
			
			
			
			String title = titleText.getText();
			String artist = artistText.getText();
			String album = albumText.getText();
			String year = yearText.getText();
			
			if(isValid(year)){
				Song s = new Song();
				
				s.title = title;
				s.artist = artist;
				s.album = album;
				if(year == null || year.equals("")){
					s.year = -1;
				}else{
				s.year = Integer.parseInt(year);
				}
				
				if(songlist.contains(s)){
					//set alert
					Alert invalidEntryAlert = new Alert(AlertType.ERROR);
					 invalidEntryAlert.setTitle("ERROR");
					 invalidEntryAlert.setHeaderText("Invalid entry");
					 invalidEntryAlert.setContentText("This Song already exists");
					 invalidEntryAlert.showAndWait();
				}else{
					/*listView.setOnEditStart(new EventHandler<ListView.EditEvent<Song>>() {
						@Override
						public void handle(ListView.EditEvent<Song> t) {
							listView.getItems().set(index,s);
							System.out.println("setOnEditCommit");
						}
									
					});*/
				songlist.set(index, s);
				songlist.sort(Song.BY_TITLE_ARTIST);
					
					
				}
				listView.getSelectionModel().select(index);
			}else{
				
				//flag error. There is no letter in a year
				Alert invalidInputAlert = new Alert(AlertType.ERROR);
				invalidInputAlert.setTitle("ERROR");
				invalidInputAlert.setHeaderText("Invalid datatype");
				invalidInputAlert.setContentText("The year textbox only accepts positive integers");
				invalidInputAlert.showAndWait();
				
			}
			
			
			
			
		}else if(b == deleteButton){
			/*************************************************
			 * 
			 * Delete an existing Song
			 * 
			 ***************************************************/
			
			int selectedIndex = 0;
			int index = listView.getSelectionModel().getSelectedIndex();
			
			if (index != songlist.size() - 1){
				selectedIndex = index;
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Delete Song?");
			alert.setContentText("Do you want to delete this song?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    // ... user chose OK
				songlist.remove(index);
				listView.getSelectionModel().select(selectedIndex);
				printToTextfield();
				songlist.sort(Song.BY_TITLE_ARTIST);
			} else {
			    // ... user chose CANCEL or closed the dialog
			}
			
		}
		
		
	}
	
	private boolean isValid(String year){
		if(year.equals("") || year == null)
			return true;
		
		for(int i = 0; i < year.length(); i++){
			if(Character.isDigit(year.charAt(i))){
				continue;
				
			}else{
				return false;
			}
		}
		
		return true;
		
	} 
	
	private void printToTextfield(){
		int index = listView.getSelectionModel().getSelectedIndex();
		if(index <= -1){
			titleText.setText("");
	        artistText.setText("");
	        albumText.setText("");
	        yearText.setText("");
		}else{
		 String title = listView.getSelectionModel().getSelectedItem().title;
	      String artist = listView.getSelectionModel().getSelectedItem().artist;
	      String album = listView.getSelectionModel().getSelectedItem().album;
	      int year = listView.getSelectionModel().getSelectedItem().year;
	      
	      
           titleText.setText(title);
           artistText.setText(artist);
           albumText.setText(album);
           if(year == -1){
           	yearText.setText("");
           }else{
           yearText.setText(String.format("%d",year));
           }
	}
	}
   
   }
	




