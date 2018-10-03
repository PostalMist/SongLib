
package library;

import org.json.JSONArray;
import org.json.JSONObject;

public class Song implements Comparable<Song>
{
	/*Song metadata*/
	private int title = 0;
	private int artist = 1;
	private int album = 2;
	private int year = 3;
	private String metadataNames[] = {"title","artist","album","year"};
	private String metadata[] = new String[4];
	/*Pointer to next song in library*/
	private Song nextSong;
	private Song previousSong;
	
	/**
	 * @param title Title of song
	 * @param artist Song's artist name
	 * @param album Song's album name
	 * @param year Year of song
	 */
	public Song(String title, String artist, String album, String year)
	{
		metadata[this.title] = title;
		metadata[this.artist] = artist;
		metadata[this.album] = album;
		metadata[this.year] = year;
		this.nextSong = null;
		
	}
	/*Set methods*/
	public void editTitle(String title){metadata[this.title] = title;}
	public void editArtist(String artist){metadata[this.artist] = artist;}
	public void editAlbum(String album){metadata[this.title] = album;}
	public void editYear(String year){metadata[this.title] = year;}
	public void editNext(Song nextSong){this.nextSong = nextSong;}
	public void editPrevious(Song previousSong){this.previousSong = previousSong;}
	/*Get methods*/
	public String getTitle(){return metadata[title];}
	public String getArtist(){return metadata[artist];}
	public String getAlbum(){return metadata[album];}
	public String getYear(){return metadata[year];}
	public Song getNext(){return nextSong;}
	public Song getPrevious(){return previousSong;}
	public int compareTo(Song a)
	{
		if(a == null) {return 1;}
		int o = this.toString().compareTo(a.toString());
		return o;
	}
	public JSONObject toJson()
	{
		JSONArray data = new JSONArray();
		JSONObject json = new JSONObject();
		for(int i = 0; i < metadata.length; i++)
		{
			json.put(metadataNames[i],metadata[i]);
		}
		return json;
	}
	public String toString()
	{
		return metadata[title];
	}
}