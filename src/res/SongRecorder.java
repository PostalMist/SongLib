package res;
/*
Team:
Ebosereme Enahoro - eoe7 
Yitzchok Dier - yid2
*/
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SongRecorder implements EventHandler<WindowEvent>{
//This class is in charge of storing and retrieving the list of songs from a database
	/************************************************************************************
	 * Fields
	 ************************************************************************************/
	public  File savefile;
	private ObservableList<Song> songList;
	
	/************************************************************************************
	 * Constructor
	 ************************************************************************************/
	
	public SongRecorder(){
		this.savefile = new File("." + File.separator + "library.json");
		try{
			this.savefile.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
		};
		
		
	}
	
	/************************************************************************************
	 * Methods
	 ************************************************************************************/
	@Override
	public void handle(WindowEvent we){
		//on close save the song library to the JSON database
		  if(we.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST)
				save(this.toJson());
			  
	  }
	
	public void getList(ObservableList<Song> songList){
		//get method to get the list of songs
		this.songList = songList;
	}

	public boolean save(JSONObject json)
	{//This method saves the list of songs to the JSON database
		
		File savefile = new File("." + File.separator + "library.json");
		try
		{
			
			FileWriter write = new FileWriter(savefile);
			write.write(json.toString());
			write.close();
			return true;
		}
		catch(IOException e)
		{
			System.err.println("Cannot write save file.");
			e.printStackTrace();
		}
		return false;
	}

	
	public JSONObject toJson()
	{//converts the SongRecorder object to a JSON object
		JSONObject json = new JSONObject();
		int i = 0;
		for(Song s : this.songList){
			json.put(i + "",s.toJson());
			i++;
		}
		return json;
	}
	
	
	public ObservableList<Song> load()
	{//retrieves a list of songs from the database
		
		ObservableList<Song> obsList = FXCollections.observableArrayList();
		try
		{
			
			FileReader reader = new FileReader(savefile);
			JSONTokener tokener = new JSONTokener(reader);
			JSONObject json = new JSONObject(tokener);
			Iterator<String> keys = json.keys();
			while(keys.hasNext())
			{
				String k = keys.next();
				if(json.get(k) instanceof JSONObject)
				{
					JSONObject song = json.getJSONObject(k);
					obsList.add(new Song(song.getString("title"),song.getString("artist"),song.getString("album"),song.getInt("year")));
				}
			}
		}
		catch(FileNotFoundException | JSONException e)
		{
			if(e instanceof JSONException)
			{
				System.err.println("Save file corrupted. Deleting corrupted file and loading new library.");
				savefile.delete();
			}
			if(e instanceof FileNotFoundException)
			{
				System.err.println("Cannot read save file.");
				e.printStackTrace();
			}
		}
		return obsList;
	}

}


