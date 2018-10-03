package database_interface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Database
{
	public static boolean save(library.SongLibrary lib)
	{
		File savefile = new File("." + File.separator + "library.json");
		try
		{
			FileWriter write = new FileWriter(savefile);
			String out = lib.toJson().toString();
			//System.out.println(out);
			write.write(out);
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
	public static boolean load(library.SongLibrary lib)
	{
		File savefile = new File("." + File.separator + "library.json");
		if(!savefile.exists())
		{
			return false;
		}
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
					//System.out.println(String.format("%s %s %s %s",song.getString("title"),song.getString("artist"), song.getString("album"),song.getString("year")));
					lib.addSong(song.getString("title"),song.getString("artist"),song.getString("album"),song.getString("year"));
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
		return true;
	}
}
