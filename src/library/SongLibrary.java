package library;

import java.util.Hashtable;

import org.json.JSONObject;

import database_interface.Database;

public class SongLibrary
{
	private Song library;
	private Hashtable<String, Song> mapper; 
	private int size;
	
	public SongLibrary()
	{
		size = 0;
		mapper = new Hashtable<String,Song>(); 
		Database.load(this);
	}
	
	/**
	 * Adds a new song to the library
	 * @param title Title of song
	 * @param artist Song's artist name
	 * @param album Song's album name
	 * @param year Year of song
	 * @throws NullPointerException For null title
	 */
	public void addSong(String title,String artist,String album,String year) throws NullPointerException
	{
		Song newSong = new Song(title, artist, album, year);
		mapper.put(title, newSong);
		addInOrder(newSong);
		size++;
	}
	/**
	 * Edit song data. Any parameter left null will not be edited
	 * @param currentTitle Current title of song
	 * @param newTitle New title for song
	 * @param newArtist New artist for song
	 * @param newAlbum New album for song
	 * @param newYear New year for song
	 * @return true if completed successfully, or false is song does not exist in library.
	 */
	public boolean editSong(String currentTitle,String newTitle, String newArtist,String newAlbum,String newYear)
	{
		Song song = mapper.get(currentTitle);
		if(song == null)
		{
			return false;
		}
		if(newTitle != null){song.editTitle(newTitle);}
		if(newArtist != null){song.editArtist(newArtist);}
		if(newAlbum != null){song.editAlbum(newAlbum);}
		if(newYear != null){song.editYear(newYear);}
		Song temp = song.getPrevious();
		if(song.getPrevious() != null)
		{		
			temp.editNext(song.getNext());
		}
		if(song.getNext() != null)
		{
			song.getNext().editPrevious(temp);
		}
		song.editNext(null);
		song.editPrevious(null);
		addInOrder(song);
		return true;
	}
	private void addInOrder(Song song)
	{
		if(size == 0 || library == null)
		{
			library = song;
			return;
		}
		Song itter = library;
		Song prev = null;
		while(itter != null)
		{
			if(itter.compareTo(song) >= 0 && song.compareTo(prev) >= 0)
			{
				song.editNext(itter);
				song.editPrevious(prev);
				if(prev != null)
				{
					prev.editNext(song);
				}
				else
				{
					library = song;
				}
				return;
			}
			prev = itter;
			itter = itter.getNext();
		}
		prev.editNext(song);
		song.editPrevious(prev);
		 
	}
	
	public int getSize(){return size;}
	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		Song current = library;
		int i = 0;
		while(current != null)
		{
			json.put(i + "",current.toJson());
			current = current.getNext();
			i++;
		}
		return json;
	}
	public String toString()
	{
		String libToString = "";
		Song song = library;
		while(song != null)
		{
			libToString += song.toString() + "\n";
			song = song.getNext();
		}
		return libToString;
	}
}
