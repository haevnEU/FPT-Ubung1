package core;

import interfaces.ISong;
import java.io.Serializable;

/**
 * This class is used as a serialization wrapper for Song objects
 * <p>
 * Created by Nils Milewsi (nimile) on 16.06.17
 */
public class SongWrapper implements Serializable{
	private String title, path, artist, album;
	private Long id;

	public SongWrapper() {
		super();
	}

	public SongWrapper(ISong s){
		this.title = s.getTitle();
		this.path = s.getPath();
		this.artist = s.getInterpret();
		this.album = s.getAlbum();
		this.id = s.getId();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
