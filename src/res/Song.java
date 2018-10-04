package res;
/*
Team:
Ebosereme Enahoro - eoe7 
Yitzchok Dier - yid2
*/
import java.util.Comparator;
import org.json.JSONObject;

public class Song {
	/************************************************************************************
	 * Fields
	 ************************************************************************************/
	public static final Comparator<Song> BY_TITLE_ARTIST = new ByTitleAndArtist();
	public String title;
	public String artist;
	public String album;
	public int year;
	
	/************************************************************************************
	 * Constructors
	 ************************************************************************************/
	
	//Default constructor
	public Song(){
		this.title = "";
		this.artist = "";
		this.album = "";
		this.year = 0;
	}
	//constructor
	public Song(String title, String artist, String album, int year){
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	/************************************************************************************
	 * Methods
	 ************************************************************************************/
	
	public boolean equals(Object o){
		//Test to see if two songs are the same
		if (o == null || !(o instanceof Song)){
			return false;
		}
		
		Song other = (Song)o;
		
		return this.title.equalsIgnoreCase(other.title) && this.artist.equalsIgnoreCase(other.artist) 
				&& this.album.equalsIgnoreCase(other.album) && this.year == other.year;
		
	}
	
	
	public JSONObject toJson()
	{//converts a Song object to a JSONObject
		
		JSONObject json = new JSONObject();
		json.put("title",this.title);
		json.put("artist", this.artist);
		json.put("album", this.album);
		json.put("year", this.year);
		return json;
	}
	
	public String toString(){
		return this.title+"  -  "+this.artist;
	}
	
/************************************************************************************
 * private class for comparator interface
 ************************************************************************************/	
	
private static class ByTitleAndArtist implements Comparator<Song>{
	//Compares songs by title then by artist
	public int compare(Song s1, Song s2){
		if(s1.title.compareToIgnoreCase(s2.title) < 0){
			return -1;
		}else if(s1.title.compareToIgnoreCase(s2.title) > 0){
			return 1;
		}else{
			
			if(s1.artist.compareToIgnoreCase(s2.artist) < 0){
				return -1;
			}else if(s1.artist.compareToIgnoreCase(s2.artist) > 0){
				return 1;
			}
		}
		return 0;
	}
}
	
}
